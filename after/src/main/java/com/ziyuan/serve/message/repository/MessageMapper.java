package com.ziyuan.serve.message.repository;


import com.ziyuan.serve.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * class description goes here
 *
 * @ClassName: PostMapper
 * @Description:
 */

@Mapper
public interface MessageMapper {

    List<Message> findAllMessage(int userId);

    void insertMessage(int fromId, int toId, String content, Date createTime, String type);

}
