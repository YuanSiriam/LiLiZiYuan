package com.ziyuan.serve.securityComponent.handler;


import com.ziyuan.serve.securityComponent.JwtUtil;
import com.ziyuan.serve.util.ServeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/16
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Autowired
    JwtUtil jwtUtils;

    @Value("${jwt.tokenHeader}")
    String header;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader(header, "");

        outputStream.write(ServeUtil.getJSONString(0,"SuccessLogout").getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

