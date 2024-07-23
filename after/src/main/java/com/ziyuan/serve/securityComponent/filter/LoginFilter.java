package com.ziyuan.serve.securityComponent.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyuan.serve.securityComponent.PasswordEncoder;
import com.ziyuan.serve.securityComponent.handler.LoginSuccessHandler;
import com.ziyuan.serve.securityComponent.handler.LoginFailureHandler;
import com.ziyuan.serve.util.ServeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/16
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager authenticationManager,
                             LoginSuccessHandler loginSuccessHandler,
                             LoginFailureHandler loginFailureHandler) {
        log.info("拦截登录");

        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(loginSuccessHandler);
        this.setAuthenticationFailureHandler(loginFailureHandler);
        // 设置处理的URL路径
        this.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Map<String, Object> map = ServeUtil.getBodyFromRequest(request);

        String password = (String) map.get("password");
        String username = (String) map.get("username");

        if (!username.isEmpty() && !password.isEmpty()) {

            // 使用用户名密码构建 Token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }


}
