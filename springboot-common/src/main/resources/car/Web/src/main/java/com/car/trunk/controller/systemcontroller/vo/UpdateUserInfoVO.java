package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.systemcontroller.vo;

import com.car.trunk.dal.model.UserEntity;

/**
 * Description: 更新用户信息
 * Original Author: monkjavaer, 2018/1/5
 */
public class UpdateUserInfoVO extends UserEntity {

    private String userId;
    private String oldPassword;
    private String verify;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
