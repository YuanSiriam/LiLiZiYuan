package com.ziyuan.serve.message.service;


import com.ziyuan.serve.message.api.MessageService;
import com.ziyuan.serve.message.entity.Message;
import com.ziyuan.serve.message.repository.MessageMapper;
import com.ziyuan.serve.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * class description goes here
 *
 * @ClassName: PostServiceImpl
 * @Description:
 */

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Map<String, Object>> showAllMessage(int userId) {

        List<Message> allMessage = messageMapper.findAllMessage(userId);
        List<Map<String, Object>> listMessage = new ArrayList<>();

        for (Message message : allMessage) {
            Map<String, Object> mapMessage = new HashMap<>();
            mapMessage.put("messageId", message.getMessageId());
            mapMessage.put("fromId", message.getFromId());
            mapMessage.put("toId", message.getToId());
            mapMessage.put("createTime", message.getCreateTime());
            mapMessage.put("type", message.getType());
            mapMessage.put("status", message.getStatus());
            mapMessage.put("content", message.getContent());


            listMessage.add(mapMessage);
        }
        return listMessage;
    }

    @Override
    public void insertMessage(int fromId, int toId, String content, Date createTime, String type) {
        messageMapper.insertMessage(fromId, toId, content, createTime, type);
    }
}
