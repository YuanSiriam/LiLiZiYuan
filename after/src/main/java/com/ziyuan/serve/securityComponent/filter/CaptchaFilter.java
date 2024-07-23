package com.ziyuan.serve.securityComponent.filter;


import com.ziyuan.serve.exception.CaptchaException;
import com.ziyuan.serve.securityComponent.handler.LoginFailureHandler;
import com.ziyuan.serve.util.RedisKeys;
import com.ziyuan.serve.util.RedisUtil;
import com.ziyuan.serve.util.ServeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/15
 */

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if ("/serve/user/login".equals(uri) && request.getMethod().equals("POST")){
            try {
                validate(request);
            }catch (CaptchaException e){
                failureHandler.onAuthenticationFailure(request,response, e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws CaptchaException {

        Map<String, Object> map = ServeUtil.getBodyFromRequest(request);

        String code = (String) map.get("code");
        String key = (String) map.get("key");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }

        if (!code.equals(redisUtil.hGet(RedisKeys.getKaptchaKey(), key))) {
            throw new CaptchaException("验证码错误");
        }


        // 若验证码正确，执行以下语句
        redisUtil.hDelete(RedisKeys.getKaptchaKey(), key);

    }
}
