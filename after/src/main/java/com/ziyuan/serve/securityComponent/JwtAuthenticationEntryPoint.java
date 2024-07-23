package com.ziyuan.serve.securityComponent;


import com.ziyuan.serve.util.ServeUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(response.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(ServeUtil.getJSONString(response.SC_UNAUTHORIZED,"请先登录").getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
