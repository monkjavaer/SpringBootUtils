package com.car.trunk.dal.system.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:
 * Original Author: monkjavaer, 2017/12/8
 */
public class RoleListBO {
    private static final Logger logger = LoggerFactory.getLogger(RoleListBO.class);

    private BigInteger roleId;
    private String roleName;
    private List<String> authority;

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }
}
