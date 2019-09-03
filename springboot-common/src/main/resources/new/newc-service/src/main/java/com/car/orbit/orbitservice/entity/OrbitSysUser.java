package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_sys_user")
public class OrbitSysUser {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户名
     */
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 性别(1:男; 2:女)
     */
    @Column(name = "GENDER")
    private Integer gender;

    /**
     * 电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 监控中心ID
     */
    @Column(name = "MONITOR_CENTER_ID")
    private String monitorCenterId;

    /**
     * 角色ID
     */
    @Column(name = "ROLE_ID")
    private String roleId;

    /**
     * 头像路径
     */
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    @Transient
    private String language;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return USERNAME - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return NAME - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别(1:男; 2:女)
     *
     * @return GENDER - 性别(1:男; 2:女)
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别(1:男; 2:女)
     *
     * @param gender 性别(1:男; 2:女)
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取电话
     *
     * @return PHONE - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return EMAIL - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取监控中心ID
     *
     * @return MONITOR_CENTER_ID - 监控中心ID
     */
    public String getMonitorCenterId() {
        return monitorCenterId;
    }

    /**
     * 设置监控中心ID
     *
     * @param monitorCenterId 监控中心ID
     */
    public void setMonitorCenterId(String monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    /**
     * 获取角色ID
     *
     * @return ROLE_ID - 角色ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取头像路径
     *
     * @return photo_url - 头像路径
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置头像路径
     *
     * @param photoUrl 头像路径
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 获取是否已删除
     *
     * @return DELETED - 是否已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否已删除
     *
     * @param deleted 是否已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}