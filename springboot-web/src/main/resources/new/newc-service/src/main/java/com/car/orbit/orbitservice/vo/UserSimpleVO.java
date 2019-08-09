package com.car.orbit.orbitservice.vo;

/**
 * @Title: UserSimpleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 用户列表查询（分页）返回结果
 * @Author: monkjavaer
 * @Date: 2019/03/12 10:39
 * @Version: V1.0
 */
public class UserSimpleVO {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮件
     */
    private String email;
    /**
     * 头像url
     */
    private String photoUrl;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 机构id
     */
    private String monitorCenterId;
    /**
     * 机构名
     */
    private String monitorCenterName;
    /**
     * 是否可删除
     */
    private boolean deletable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(String monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    public String getMonitorCenterName() {
        return monitorCenterName;
    }

    public void setMonitorCenterName(String monitorCenterName) {
        this.monitorCenterName = monitorCenterName;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
