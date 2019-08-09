package com.car.trunk.center.system.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Description: 用户信息
 * Original Author: monkjavaer, 2017/12/20
 */
public class UserInfoBO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoBO.class);

    private BigInteger userId;
    private String userName;
    private String roleName;
    private BigInteger centerId;
    private List<String> authority;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public BigInteger getCenterId() {
        return centerId;
    }

    public void setCenterId(BigInteger centerId) {
        this.centerId = centerId;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }
}
