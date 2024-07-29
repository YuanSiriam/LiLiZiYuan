package com.ziyuan.serve.duplexCommunication.repository;


import com.ziyuan.serve.duplexCommunication.entity.Scorlling;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */

@Mapper
public interface ScorllingMapper {

    List<Scorlling> selectAllScorlling();

    void insertScorlling(int userId, String username, String color, String content, Date createTime);
}
