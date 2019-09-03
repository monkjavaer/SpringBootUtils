package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.systemcontroller.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: 修改用户权限
 * Original Author: monkjavaer, 2017/12/19
 */
public class UpdateRoleAuthorityVO {
    private static final Logger logger = LoggerFactory.getLogger(UpdateRoleAuthorityVO.class);

    BigInteger roleId;
    List<String> authorityName;

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public List<String> getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(List<String> authorityName) {
        this.authorityName = authorityName;
    }
}
