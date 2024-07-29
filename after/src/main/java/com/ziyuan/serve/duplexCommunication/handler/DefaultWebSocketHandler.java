package com.ziyuan.serve.duplexCommunication.handler;


import com.ziyuan.serve.duplexCommunication.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;


/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/25
 */
public class DefaultWebSocketHandler implements WebSocketHandler {

    @Autowired
    private WebSocketService webSocket;

    /**
     * 建立连接
     * @param webSocketSession 连接信息
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        webSocket.handleOpen(webSocketSession);
    }

    /**
     * 接收消息
     * @param webSocketSession
     * @param webSocketMessage 接收的信息
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage){
            TextMessage textMessage = (TextMessage) webSocketMessage;
            webSocket.handleMessage(webSocketSession, textMessage.getPayload());
        }
    }

    /**
     * 发生错误
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        webSocket.handleError(webSocketSession, throwable);
    }

    /**
     * 关闭连接
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        webSocket.handleClose(webSocketSession);
    }

    /**
     * 是否支持发送部分消息
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
