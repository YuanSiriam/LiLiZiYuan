package com.ziyuan.serve.post.api;


import java.util.List;
import java.util.Map;

public interface ReplyService {

    /**
     * 获取回复
     */
    List<Map<String, Object>> selectAllReply(int postId);

    /**
     * 添加回复
     */
    void insertReply(String username, int userId, int toPostId, int toCommentId, String toReplyUsername, String content);
}
