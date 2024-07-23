package com.ziyuan.serve.util;


/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/15
 */
public class RedisKeys {

    private static final String SPLIT = ":";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_USER = "user";
    private static final String PREFIX_UNREAD = "unreadCount"; //未读消息数量

    // 登录验证码
    public static String getKaptchaKey(){

        return PREFIX_KAPTCHA;
    }

    // 登录的用户
    public static String getUserKey(int userId){
        return PREFIX_USER + SPLIT + userId;
    }

    // 未读消息数量
    public static String getUnreadKey(int userId){
        return PREFIX_UNREAD + SPLIT + userId;
    }

}
