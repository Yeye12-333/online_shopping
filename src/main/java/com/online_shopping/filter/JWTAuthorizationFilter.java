package com.online_shopping.filter;



import com.online_shopping.exception.PermissionException;
import com.online_shopping.util.JwtTokenUtils;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 授权过滤器
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {


        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (StringUtils.isEmpty(tokenHeader) || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }


        if (!JwtTokenUtils.isExpiration(tokenHeader)){
            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Integer userId = JwtTokenUtils.getUserId(tokenHeader);
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public String[] getParameterValues(String name) {
                    if (name.equals("loginUserId")) {
                        return new String[] { userId .toString() };
                    }
                    return super.getParameterValues(name);
                }
                @Override
                public Enumeration<String> getParameterNames() {
                    Set<String> paramNames = new LinkedHashSet<>();
                    paramNames.add("loginUserId");
                    Enumeration<String> names =  super.getParameterNames();
                    while(names.hasMoreElements()) {
                        paramNames.add(names.nextElement());
                    }
                    return Collections.enumeration(paramNames);
                }
            };
            chain.doFilter(requestWrapper, httpResponse);

//            super.doFilterInternal(request, response, chain);
        }else {
            throw new PermissionException("token已过期");
        }
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     * @param tokenHeader 请求头
     * @return 身份令牌
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String username = JwtTokenUtils.getUsername(tokenHeader);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null,Collections.singleton(new SimpleGrantedAuthority("user")) );
        }
        return null;
    }
}
