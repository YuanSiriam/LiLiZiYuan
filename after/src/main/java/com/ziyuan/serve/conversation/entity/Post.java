package com.ziyuan.serve.conversation.entity;


import lombok.Getter;

import java.util.Date;

/**
 * class description goes here
 *
 * @ClassName: Post
 * @Description:
 */

@Getter
public class Post {
    private int postId;
    private int userId;
    private String title;
    private String content;
    private Date createTime;
    private int heat;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }
}
