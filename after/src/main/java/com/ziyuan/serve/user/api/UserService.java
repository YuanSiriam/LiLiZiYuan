package com.ziyuan.serve.user.api;

import com.ziyuan.serve.user.entity.Customer;

public interface UserService {

    Customer findUser(int id, String username, String mail);

    int register(String username, String password, String mail);

    Customer findUserById(int id);
}
