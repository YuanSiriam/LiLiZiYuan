package com.ziyuan.serve.conversation.repository;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * class description goes here
 *
 * @ClassName: PostMapper
 * @Description:
 */

@Mapper
public interface ChatMapper {

    List<Object> showConversations();

}
