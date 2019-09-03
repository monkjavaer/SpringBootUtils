package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_sys_role_authority")
public class OrbitSysRoleAuthority {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 权限id
     */
    @Column(name = "authority_id")
    private String authorityId;

    /**
     * 添加权限：1有，2无 
     */
    @Column(name = "operate_add")
    private Integer operateAdd;

    /**
     * 修改权限：1有，2无
     */
    @Column(name = "operate_update")
    private Integer operateUpdate;

    /**
     * 删除权限：1有，2无
     */
    @Column(name = "operate_delete")
    private Integer operateDelete;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限id
     *
     * @return authority_id - 权限id
     */
    public String getAuthorityId() {
        return authorityId;
    }

    /**
     * 设置权限id
     *
     * @param authorityId 权限id
     */
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    /**
     * 获取添加权限：1有，2无 
     *
     * @return operate_add - 添加权限：1有，2无 
     */
    public Integer getOperateAdd() {
        return operateAdd;
    }

    /**
     * 设置添加权限：1有，2无 
     *
     * @param operateAdd 添加权限：1有，2无 
     */
    public void setOperateAdd(Integer operateAdd) {
        this.operateAdd = operateAdd;
    }

    /**
     * 获取修改权限：1有，2无
     *
     * @return operate_update - 修改权限：1有，2无
     */
    public Integer getOperateUpdate() {
        return operateUpdate;
    }

    /**
     * 设置修改权限：1有，2无
     *
     * @param operateUpdate 修改权限：1有，2无
     */
    public void setOperateUpdate(Integer operateUpdate) {
        this.operateUpdate = operateUpdate;
    }

    /**
     * 获取删除权限：1有，2无
     *
     * @return operate_delete - 删除权限：1有，2无
     */
    public Integer getOperateDelete() {
        return operateDelete;
    }

    /**
     * 设置删除权限：1有，2无
     *
     * @param operateDelete 删除权限：1有，2无
     */
    public void setOperateDelete(Integer operateDelete) {
        this.operateDelete = operateDelete;
    }
}