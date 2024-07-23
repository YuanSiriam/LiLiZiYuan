package com.ziyuan.serve.util;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * class description goes here
 *
 * @ClassName: Result
 * @Description:
 */

@Slf4j
public class ServeUtil {

    private static final String PARSED_BODY_KEY = "PARSED_BODY";

    /**
     * 获取请求中的所有参数
     */
    public static final Map<String, Object> getBodyFromRequest(HttpServletRequest request) {
        Map<String, Object> map = null;

        if (request != null) {
            // 优先从 request attribute 中读取参数
            map = (Map<String, Object>) request.getAttribute(PARSED_BODY_KEY);

            // 若从 request attribute 没有读取到参数则从 request 中读取，这样做是因为 request 的流只能读取一次
            if (map == null) {
                map = readParamsFromRequest(request);
                // 读取完以后存入 request attribute
                request.setAttribute(PARSED_BODY_KEY, map);
            }
        }

        if (map == null) {
            map = new HashMap<>(0);
        }

        return map;
    }

    /**
     * 获取请求中的指定字符串参数
     */
    public static final String getStringBodyFromRequest(HttpServletRequest request, String key) {
        String result = Strings.EMPTY;
        Map<String, Object> map = getBodyFromRequest(request);

        if (map.get(key) != null) {
            result = map.get(key).toString();
        }

        return result;
    }

    /**
     * 从请求中读取参数，转为 Map
     */
    private static Map<String, Object> readParamsFromRequest(HttpServletRequest request) {
        Map<String, Object> map = null;

        try {
            // 从 request 中读取 Body 数据
            ServletInputStream inputStream = request.getInputStream();
            StringBuilder content = new StringBuilder();
            byte[] b = new byte[1024];
            int lens;
            while ((lens = inputStream.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }

            // 将字符串转换为 Map 集合
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(content.toString(), Map.class);
        } catch (Exception ex) {
            log.error("Error occurred while read params from request");
        }

        return map;
    }

    // 获取当前登录用户信息
    public static void getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
    }

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // md5加密
    // 使用BCrypt来对密码进行加密
    /**
     * public static String md5(String str) {
     *         //用long3来判断是否为空
     *         if (StringUtils.isAllBlank(str)) {
     *             return null;
     *         }
     *         return DigestUtils.md5DigestAsHex(str.getBytes());
     *     }
     */


    // json格式输出
    public static String getJSONString(int code, String msg, Map<String, Object> map) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));

            }
        }

        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

}
