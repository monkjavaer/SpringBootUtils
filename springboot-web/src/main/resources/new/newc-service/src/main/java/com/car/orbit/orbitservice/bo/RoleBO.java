package com.car.orbit.orbitservice.bo;

/**
 * @Title: RoleBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/09 16:48
 * @Version: V1.0
 */
public class RoleBO {

    private String roleName;
    private RolePermissionBO rolePermission;

    public RoleBO() {
        rolePermission = new RolePermissionBO();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RolePermissionBO getRolePermission() {
        return rolePermission;
    }

    public void setRolePermission(RolePermissionBO rolePermission) {
        this.rolePermission = rolePermission;
    }
}
