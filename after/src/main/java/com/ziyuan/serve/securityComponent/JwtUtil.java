package com.ziyuan.serve.securityComponent;


import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/13
 */

@Data
@Component
@Slf4j
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final String CLAIM_KEY_CREATED = "created";
    private static final int REFRESH_LEAST_TIME = 1800;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    /**
     * 生成Jwt
     */
    public String generateToken(String mail) {
        return Jwts.builder()
                .setSubject(mail)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证jwt
     */
    public Claims getClaimsFromToken(String token) {
        log.info("验证jwt");

        Claims claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.info("jwt格式验证失败：{}",token);
        }

        return claims;
    }

    /**
     * 设置过期时间
     */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 对比username和expired
     * 验证token是否还有效
     */
    public boolean validateToken(String token){
        Claims body = null;
        try{
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (body != null && !isTokenExpired(token));
    }

    /**
     * 获取过期时间
     */
    private Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否过期
     */
    public boolean isTokenExpired(String token) {
        return getExpiredDateFromToken(token).before(new Date());
    }

    /**
     * 根据用户信息生成token
     */
    /*public String generateToken(ServeUserExtend user){
        // 设置标题为用户唯一标识符mail
        String mail = user.getCustomer().getMail();
        Claims claims = Jwts.claims().setSubject(mail);

        claims.put(CLAIM_KEY_CREATED, new Date());

        // 用户的详情
        claims.put("group",user.getCustomer().getGroup());
        claims.put("username",user.getCustomer().getUsername());
        claims.put("message",user.getCustomer().getMessage());
        return generateToken(claims);
    }*/

    /**
     * 刷新token
     */
    public String refreshHeadToken(String oldToken){
        String token = oldToken.substring(tokenHead.length());

        // 校验是否过期
        if (isTokenExpired(token)){
            return null;
        }
        if (tokenRefreshJustBefore(token)){
            return token;
        }else {
            String mail = SecurityContextHolder.getContext().getAuthentication().getName();
            return generateToken(mail);
        }
    }


    /**
     * 判断token是否刚刷新过
     */
    private boolean tokenRefreshJustBefore(String token) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, REFRESH_LEAST_TIME))){
            return true;
        }
        return false;
    }

    /**
     * 获取jwt中的authentication对象
     * 以便于在任何地方调用SpringContext来获取用户的各个信息
     */
    /*public Authentication getAuthenticationFromToken(String token){
        // 解析jwt
        Claims claims = getClaimsFromToken(token);

        // 从claims中获取邮箱
        String mail = claims.getSubject();

        Customer customer = userMapper.findUserByMail(mail);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (claims.containsKey("group")){
            String group = claims.get("group", String.class);

            authorities.add(new SimpleGrantedAuthority(group));
        }

        ServeUserExtend principal = new ServeUserExtend(customer, authorities);

        // 返回一个JwtAuthenticationToken对象
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    }*/

}
