package com.ziyuan.serve.securityComponent.filter;


import cn.hutool.core.util.StrUtil;
import com.ziyuan.serve.securityComponent.JwtUtil;
import com.ziyuan.serve.user.dto.CustomerUserDetails;
import com.ziyuan.serve.user.service.UserServiceImpl;
import com.ziyuan.serve.util.ServeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/15
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.tokenHeader}")
    private String header;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServiceImpl userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(header);

        // 删除前缀Bearer
        if (jwt != null && jwt.startsWith("Bearer")){
            jwt = jwt.substring(7);
        }

        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.getClaimsFromToken(jwt);

        if (claims == null) {
            throw new JwtException("token 异常");
        }

        String mail = claims.getSubject();
        // 获取用户的权限等信息

        CustomerUserDetails customerUserDetails = (CustomerUserDetails) userService.loadUserByUsername(mail);


        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mail, null, customerUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);

    }
}

