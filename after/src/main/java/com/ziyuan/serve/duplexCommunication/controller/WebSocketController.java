package com.ziyuan.serve.duplexCommunication.controller;


import com.ziyuan.serve.duplexCommunication.service.WebSocketService;
import com.ziyuan.serve.util.ServeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/25
 */

@RestController
@RequestMapping("/ws")
@Api(tags = "WebSocket")
public class WebSocketController {

    @Autowired
    private WebSocketService webSocket;

    @ApiOperation(value = "发送广播消息")
    @PostMapping("/broadcast/send")
    public String sendBroadcastMessage(@RequestBody Map<String, Object> map) throws IOException {
        webSocket.broadCast((String) map.get("content"));
        return ServeUtil.getJSONString(0);
    }

    @ApiOperation(value = "发送单点消息")
    @PostMapping("/single/send")
    public String sendSingleMessage(@RequestParam String userId, @RequestParam String message) throws IOException {
        webSocket.sendMessage(userId, message);
        return ServeUtil.getJSONString(0);
    }



}
