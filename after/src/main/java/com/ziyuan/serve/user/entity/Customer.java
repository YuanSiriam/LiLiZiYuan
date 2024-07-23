package com.ziyuan.serve.user.entity;


import io.swagger.annotations.ApiModel;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * class description goes here
 *
 * @ClassName: Client
 * @Description:
 */


@Getter
@ApiModel("客户实体类")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String mail;

    private int group;

    private int message;

    private boolean isEnable;

    public boolean isEnable() {
        return this.isEnable;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
