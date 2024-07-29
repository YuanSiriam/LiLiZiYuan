package com.ziyuan.serve.post.service;


import com.ziyuan.serve.post.api.CommentService;
import com.ziyuan.serve.post.entity.Comment;
import com.ziyuan.serve.post.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Map<String, Object>> selectAllComment(int postId) {
        List<Comment> comments = commentMapper.selectAllComment(postId);

        List<Map<String, Object>> listComment = new ArrayList<>();

        for (Comment comment : comments) {
            Map<String, Object> mapComment = new HashMap<>();
            mapComment.put("commentId", comment.getCommentId());
            mapComment.put("username", comment.getUsername());
            mapComment.put("userId", comment.getUserId());
            mapComment.put("postId", comment.getToPostId());
            mapComment.put("content", comment.getContent());
            mapComment.put("commentCount", comment.getCommentCount());
            mapComment.put("createTime", comment.getCreateTime());

            listComment.add(mapComment);
        }
        return listComment;
    }

    @Override
    public void insertComment(String username, int userId, int toPostId, String content, int commentCount) {
        commentMapper.insertComment(username, userId, toPostId, content, commentCount, new Date());
    }

    @Override
    public void updateComment(int commentId) {
        commentMapper.updateComment(commentId);
    }

}
