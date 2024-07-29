package com.ziyuan.serve.config;


import com.ziyuan.serve.duplexCommunication.handler.DefaultWebSocketHandler;
import com.ziyuan.serve.duplexCommunication.interceptor.WebSocketInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/25
 */
@EnableWebSocket
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

    /**
     * 自动注册使用了@ServerEndpoint注解的类
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public DefaultWebSocketHandler defaultWebSocketHandler() {
        return new DefaultWebSocketHandler();
    }

    @Bean
    public WebSocketInterceptor webSocketInterceptor() {
        return new WebSocketInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(defaultWebSocketHandler(), "ws/message")
                .addInterceptors(webSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
