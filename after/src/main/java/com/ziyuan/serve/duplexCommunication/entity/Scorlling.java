package com.ziyuan.serve.duplexCommunication.entity;


import java.util.Date;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */
public class Scorlling {

    private int scrollingId;
    private int userId;
    private String username;
    private String color;
    private String content;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getScrollingId() {
        return scrollingId;
    }

    public void setScrollingId(int scrollingId) {
        this.scrollingId = scrollingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
