package com.ziyuan.serve.message.api;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class description goes here
 *
 * @ClassName: PostService
 * @Description:
 */
public interface MessageService {

    List<Map<String, Object>> showAllMessage(int userId);

    void insertMessage(int fromId, int toId, String content, Date createTime, String type);
}
