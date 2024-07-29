package com.ziyuan.serve.duplexCommunication.service;


import com.ziyuan.serve.duplexCommunication.entity.Scorlling;

import java.util.Date;
import java.util.List;

public interface ScorllingService {

    List<Scorlling> showAllScorlling();

    void insertScorlling(int userId, String username, String color, String content);

}
