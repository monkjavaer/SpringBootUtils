package com.car.trunk.dal.system.vo;

import java.math.BigInteger;

public class UserVO {
	private int pageNo;
    private int pageSize;
    private BigInteger monitorCenterId;
    private BigInteger roleId;
    private Integer gender;
    private String user;
    
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public BigInteger getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(BigInteger monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }
    
    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }
    
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
