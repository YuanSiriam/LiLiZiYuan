package com.ziyuan.serve.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/15
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg){
        super(msg);
    }
}
