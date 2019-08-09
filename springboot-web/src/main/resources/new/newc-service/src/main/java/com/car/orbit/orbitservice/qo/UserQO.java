package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

/**
 * @Title: UserQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 用户分页查询
 * @Author: monkjavaer
 * @Data: 2019/3/8 15:30
 * @Version: V1.0
 */
public class UserQO extends PageParentVO {

    /**
     * 用于模糊查询,name或者username
     */
    private String name;
    /**
     * 机构id
     */
    private String monitorCenterId;
    /**
     * 角色id
     */
    private String roleId;

    /**
     * 性别
     */
    private Integer gender;
    /**
     * 当前登录用户id，当查询用户列表应该过滤掉当前登录的用户
     */
    private String currentUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitorCenterId() {
        return monitorCenterId;
    }

    public void setMonitorCenterId(String monitorCenterId) {
        this.monitorCenterId = monitorCenterId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
