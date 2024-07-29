package com.ziyuan.serve.post.service;


import com.ziyuan.serve.post.api.ReplyService;
import com.ziyuan.serve.post.entity.Reply;
import com.ziyuan.serve.post.repository.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Map<String, Object>> selectAllReply(int postId) {

        List<Reply> replies = replyMapper.selectAllReply(postId);

        List<Map<String, Object>> listReply = new ArrayList<>();

        for (Reply reply : replies) {
            Map<String, Object> mapReply = new HashMap<>();
            mapReply.put("replyId", reply.getReplyId());
            mapReply.put("username", reply.getUsername());
            mapReply.put("userId", reply.getUserId());
            mapReply.put("postId", reply.getToPostId());
            mapReply.put("toCommentId", reply.getToCommentId());
            mapReply.put("toReplyUsername", reply.getToReplyUsername());
            mapReply.put("content", reply.getContent());
            mapReply.put("createTime", reply.getCreateTime());

            listReply.add(mapReply);
        }
        return listReply;
    }

    @Override
    public void insertReply(String username, int userId, int toPostId,int toCommentId, String toReplyUsername, String content) {

        replyMapper.insertReply(username, userId, toPostId,toCommentId, toReplyUsername, content, new Date());
    }

}
