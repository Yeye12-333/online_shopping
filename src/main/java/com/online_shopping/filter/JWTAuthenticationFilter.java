package com.online_shopping.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.online_shopping.dto.JwtUser;
import com.online_shopping.dto.LoginUser;
import com.online_shopping.util.JwtTokenUtils;
import com.online_shopping.util.Result;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证过滤器
 * @author hehaoyang
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setUsernameParameter("username");
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            System.out.println(loginUser.getUsername() + loginUser.getPassword());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 成功验证后调用的方法
     * 如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();

//        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
//        @SuppressWarnings("all")
//        GrantedAuthority[] grantedAuthorities = authorities.toArray(new GrantedAuthority[authorities.size()]);
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), jwtUser.getId(), false, jwtUser.getAttachData());
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(Result.ok().data("token", JwtTokenUtils.TOKEN_PREFIX + token)));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        System.out.println("失败了");
        response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error().data( failed.getMessage())));
    }
}
