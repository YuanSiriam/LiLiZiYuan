package com.ziyuan.serve.config.SecurityConfig;


import com.ziyuan.serve.securityComponent.JwtAuthenticationEntryPoint;
import com.ziyuan.serve.securityComponent.PasswordEncoder;
import com.ziyuan.serve.securityComponent.filter.CaptchaFilter;
import com.ziyuan.serve.securityComponent.filter.JwtAuthenticationFilter;
import com.ziyuan.serve.securityComponent.filter.LoginFilter;
import com.ziyuan.serve.securityComponent.handler.LoginFailureHandler;
import com.ziyuan.serve.securityComponent.handler.LoginSuccessHandler;
import com.ziyuan.serve.securityComponent.handler.LogoutSuccessHandler;
import com.ziyuan.serve.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/13
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    CaptchaFilter captchaFilter;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JwtAuthenticationFilter(authenticationManager());
    }

    private static final String[] URL_WHITELIST = {
            "/user/login",
            "/logout",
            "/captcha",
            //aip文档接口的放行
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs", "/configuration/ui", "/configuration/security", "/webjars/**"
    };

    @Bean
    PasswordEncoder PasswordEncoder() {
        return new PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启跨域以便前端访问
                .cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()

                // 登录配置
                // 添加自定义的登录过滤器
                .addFilterBefore(new LoginFilter(authenticationManager(), loginSuccessHandler, loginFailureHandler),
                        JwtAuthenticationFilter.class)

                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
//                .anyRequest().authenticated()
                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                // 权限异常处理
                // .accessDeniedHandler(jwtAccessDeniedHandler)

                // 配置自定义的过滤器
                .and()
                .addFilter(jwtAuthenticationFilter())
                // 验证码过滤器放在UsernamePassword过滤器之前
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * 配置胯资源访问
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }

}
