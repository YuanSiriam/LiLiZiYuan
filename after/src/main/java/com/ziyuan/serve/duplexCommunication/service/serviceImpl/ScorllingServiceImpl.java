package com.ziyuan.serve.duplexCommunication.service.serviceImpl;


import com.ziyuan.serve.duplexCommunication.entity.Scorlling;
import com.ziyuan.serve.duplexCommunication.repository.ScorllingMapper;
import com.ziyuan.serve.duplexCommunication.service.ScorllingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/26
 */

@Service
public class ScorllingServiceImpl implements ScorllingService {

    @Autowired
    private ScorllingMapper scorllingMapper;

    @Override
    public List<Scorlling> showAllScorlling() {
        List<Scorlling> scorllings = null;

        try {
            scorllings = scorllingMapper.selectAllScorlling();
        } catch (Exception e) {
            throw new RuntimeException("查询滚动消息失败");
        }

        return scorllings;
    }

    @Override
    public void insertScorlling(int userId, String username, String color, String content) {
        Date createTime = new Date();

        scorllingMapper.insertScorlling(userId, username, color, content, createTime);

    }
}
