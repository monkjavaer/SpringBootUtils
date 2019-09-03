package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.bo;

import java.math.BigInteger;

public class UserBO {
	private BigInteger id;
    private String username;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private BigInteger monitorCenterId;
    private String monitorCenterName;
    private BigInteger roleId;


    public String getMonitorCenterName() {
        return monitorCenterName;
    }

    public void setMonitorCenterName(String monitorCenterName) {
        this.monitorCenterName = monitorCenterName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
