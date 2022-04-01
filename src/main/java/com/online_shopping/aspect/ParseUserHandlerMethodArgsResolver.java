package com.online_shopping.aspect;

import com.online_shopping.annotation.ParseUser;
import com.online_shopping.exception.PermissionException;
import com.online_shopping.exception.SugoException;
import com.online_shopping.pojo.User;
import com.online_shopping.service.UserService;
import com.online_shopping.util.JwtTokenUtils;
import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 注入当前用户参数
 * @author yeye
 */
@Slf4j
public class ParseUserHandlerMethodArgsResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public ParseUserHandlerMethodArgsResolver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParseUser.class);
    }

    @SneakyThrows
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ParseUser parseUser = methodParameter.getParameterAnnotation(ParseUser.class);
        assert parseUser != null;
        try {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            assert request != null;
            String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
            if (StringUtils.isEmpty(token) && !parseUser.required()){
                return null;
            }
            Class<?> type = methodParameter.getParameter().getType();
                if (type == User.class){
                        int userId = JwtTokenUtils.getUserId(token);
                        return userService.getUserInfo(userId);
                }else if (type == Integer.class){
                    return JwtTokenUtils.getAttachData(token, "id");
                }
        }catch (JwtException | NullPointerException | IllegalArgumentException e){
            if (parseUser.required()){
                throw new PermissionException(e.getMessage());
            }else {
                return null;
            }
        }
        throw new SugoException("没有匹配的参数类型");
    }
}
