package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

/**
 * @Title: RoleQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 角色分页查询
 * @Author: monkjavaer
 * @Date: 2019/03/19 15:24
 * @Version: V1.0
 */
public class RoleQO extends PageParentVO {

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 创建者id
     */
    private String creator;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
