package com.car.trunk.controller.systemcontroller.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 用户登录参数
 * Original Author: monkjavaer, 2017/12/20
 */
public class LogoutParam {
    private static final Logger logger = LoggerFactory.getLogger(LogoutParam.class);

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
