package com.ziyuan.serve.post.api;


import java.util.List;
import java.util.Map;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */
public interface CommentService {
    /**
     * 获取评论
     */
    List<Map<String, Object>> selectAllComment(int postId);


    /**
     * 添加评论或回复
     */
    void insertComment(String username, int userId, int toPostId, String content,int commentCount);

    /**
     * 更新评论
     */

    void updateComment(int commentId);
}
