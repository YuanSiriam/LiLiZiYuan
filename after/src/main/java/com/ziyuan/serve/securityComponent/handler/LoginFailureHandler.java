package com.ziyuan.serve.securityComponent.handler;

import com.ziyuan.serve.exception.CaptchaException;
import com.ziyuan.serve.util.ServeConstant;
import com.ziyuan.serve.util.ServeUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
 * @Description 2024/7/15
 */

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler, ServeConstant {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        try{
            String errorMsg = "用户名或密码错误";
            response.setStatus(response.SC_UNAUTHORIZED);
            if (e instanceof CaptchaException) {
                errorMsg = "验证码错误";

                outputStream.write(ServeUtil.getJSONString(response.SC_UNAUTHORIZED,errorMsg).getBytes(StandardCharsets.UTF_8));
            } else {
                outputStream.write(ServeUtil.getJSONString(response.SC_UNAUTHORIZED,errorMsg).getBytes(StandardCharsets.UTF_8));
            }
        }finally {
            outputStream.flush();
            outputStream.close();
        }
    }

}
