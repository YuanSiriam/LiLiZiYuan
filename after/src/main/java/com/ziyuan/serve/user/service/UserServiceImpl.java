package com.ziyuan.serve.user.service;


//import com.ziyuan.serve.securityComponent.ServeUserExtend;
//import com.ziyuan.serve.securityComponent.UserBCryptPasswordEncoder;
import com.ziyuan.serve.securityComponent.PasswordEncoder;
import com.ziyuan.serve.user.dto.CustomerUserDetails;
import com.ziyuan.serve.user.entity.Customer;
import com.ziyuan.serve.user.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * class description goes here
 *
 * @ClassName: ClientServiceImpl
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据id/用户名/邮箱查找用户
     */
    public Customer findUser(int id, String username, String mail) {

        username = Objects.equals(username, "") ? null : username;

        Customer customer = userMapper.findUserByIUP(id, username, mail);
        return customer;
    }

    /**
     * 邮箱、用户名、密码注册账号
     */
    public int register(String username, String password, String mail) {

        Customer customer = new Customer();

        String encode = new PasswordEncoder().encode(password);
        customer.setUsername(username);
        customer.setPassword(encode);
        customer.setMail(mail);
        customer.setGroup(0);
        customer.setMessage(0);

        userMapper.insertUser(customer);

        return 0;
    }


    /**
     * 根据用户名查找用户，但其实是根据邮箱
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Customer customer = userMapper.findUserByMail(s);

        if (Objects.isNull(customer)){
            throw new UsernameNotFoundException("用户名不存在");
        }

        return new CustomerUserDetails(customer, null);
    }
}
