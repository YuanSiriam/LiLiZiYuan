package com.ziyuan.serve.post.repository;


import com.ziyuan.serve.post.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;


@Mapper
public interface CommentMapper {
    /**
     * 获取评论或回复
     */
    List<Comment> selectAllComment(int postId);

    /**
     * 添加评论或回复
     */
    void insertComment(String username, int userId, int toPostId, String content,int commentCount, Date createTime);

    /**
     * 更新评论
     */
    void updateComment(int commentId);
}
