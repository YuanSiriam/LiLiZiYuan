package com.ziyuan.serve.post.entity;


import java.util.Date;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */
public class Reply {
    private int replyId;
    private String username;
    private int userId;
    private int toPostId;
    private int toCommentId;
    private String toReplyUsername;

    public int getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(int toCommentId) {
        this.toCommentId = toCommentId;
    }

    private String content;
    private Date createTime;

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToPostId() {
        return toPostId;
    }

    public void setToPostId(int toPostId) {
        this.toPostId = toPostId;
    }

    public String getToReplyUsername() {
        return toReplyUsername;
    }

    public void setToReplyUsername(String toReplyUsername) {
        this.toReplyUsername = toReplyUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
