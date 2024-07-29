package com.ziyuan.serve.duplexCommunication.interceptor;


import com.ziyuan.serve.user.entity.Customer;
import com.ziyuan.serve.util.ServeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/25
 */
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            // 模拟用户（通常利用JWT令牌解析用户信息）
            try {
                int userId = ServeUtil.getUserId();
                map.put("userId", userId);
            }catch (Exception e){
                log.error("获取用户信息失败");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
