package com.ziyuan.serve.post.repository;


import com.ziyuan.serve.post.entity.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */
@Mapper
public interface ReplyMapper {
    /**
     * 获取评论或回复
     */
    List<Reply> selectAllReply(int postId);

    /**
     * 添加评论或回复
     */
    void insertReply(String username, int userId, int toPostId, int toCommentId, String toReplyUsername, String content, Date createTime);
}
