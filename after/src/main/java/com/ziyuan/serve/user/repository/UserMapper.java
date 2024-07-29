package com.ziyuan.serve.user.repository;


import com.ziyuan.serve.user.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * class description goes here
 *
 * @ClassName: WaiterMapper
 * @Description:
 */

@Mapper
public interface UserMapper {

    Customer findUserByIUP(int id, String username, String mail);

    void insertUser(Customer customer);

    Customer findUserByMail(String mail);

    Customer findUserById(int userId);

}
