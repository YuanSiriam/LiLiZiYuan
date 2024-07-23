package com.ziyuan.serve.securityComponent.handler;


import com.ziyuan.serve.securityComponent.JwtUtil;
import com.ziyuan.serve.user.dto.CustomerUserDetails;
import com.ziyuan.serve.util.ServeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/15
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        String username = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomerUserDetails){
            CustomerUserDetails customer = (CustomerUserDetails) principal;

            username = customer.getUsername();
        }

        // 生成Jwt并放在请求头中
        String jwt = jwtUtil.generateToken(username);
        response.setHeader(tokenHeader, jwt);

        ServeUtil.getJSONString(200, "SuccessLogin");
        PrintWriter out = response.getWriter();


        // 输出JSON响应体
        String jsonResult = ServeUtil.getJSONString(200, "SuccessLogin");
        out.print(jsonResult);
        out.flush();
        out.close();
    }
}
