package com.ziyuan.serve.conversation.service;


import com.ziyuan.serve.conversation.api.MessageService;
import com.ziyuan.serve.conversation.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * class description goes here
 *
 * @ClassName: PostServiceImpl
 * @Description:
 */


@Service
public class ChatServiceImpl implements MessageService {


    @Override
    public List<Object> showConversations() {
        return null;
    }

}
