package com.ziyuan.serve.post.entity;


import java.util.Date;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */
public class Comment {
    private int commentId;
    private String username;
    private int userId;
    private int toPostId;
    private String content;
    private int commentCount;
    private Date createTime;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
