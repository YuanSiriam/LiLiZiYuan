package com.ziyuan.serve.securityComponent;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/13
 */

@Slf4j
@NoArgsConstructor
public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    //BCrypt加密
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence charSequence) {
        return passwordEncoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {

        return passwordEncoder.matches(charSequence, s);
    }
}
