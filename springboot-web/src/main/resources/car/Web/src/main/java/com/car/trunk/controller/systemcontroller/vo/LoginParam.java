package com.car.trunk.controller.systemcontroller.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 用户登录参数
 * Original Author: monkjavaer, 2017/12/20
 */
public class LoginParam {
    private static final Logger logger = LoggerFactory.getLogger(LoginParam.class);

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
