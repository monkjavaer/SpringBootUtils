package com.car.orbit.orbitservice.vo;

/**
 * @Title: RoleOperatorVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 删除角色的参数
 * @Author: monkjavaer
 * @Date: 2019/03/19 16:08
 * @Version: V1.0
 */
public class RoleOperatorVO {

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 权限id字符串（通过逗号连接）
     */
    private String authorityIdStr;
    /**
     * 当前操作用户的密码（用于删除时进行安全校验）
     */
    private String myPassword;
    /**
     * 操作用户id
     */
    private String operatorId;
    /**
     * 操作用户名
     */
    private String operatorName;

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

    public String getAuthorityIdStr() {
        return authorityIdStr;
    }

    public void setAuthorityIdStr(String authorityIdStr) {
        this.authorityIdStr = authorityIdStr;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
