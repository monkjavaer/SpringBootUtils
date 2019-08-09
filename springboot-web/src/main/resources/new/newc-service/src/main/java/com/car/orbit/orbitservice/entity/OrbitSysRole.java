package com.car.orbit.orbitservice.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "orbit_sys_role")
public class OrbitSysRole {
    /**
     * 角色id
     */
    @Id
    private String id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人id
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    private String updator;

    /**
     * 更新人id
     */
    @Column(name = "updator_id")
    private String updatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除标志(1未删除2删除)
     */
    private Integer deleted;

    /**
     * 获取角色id
     *
     * @return id - 角色id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置角色id
     *
     * @param id 角色id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色名
     *
     * @return role_name - 角色名
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名
     *
     * @param roleName 角色名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人id
     *
     * @return creator_id - 创建人id
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人id
     *
     * @param creatorId 创建人id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return updator - 更新人
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * 设置更新人
     *
     * @param updator 更新人
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * 获取更新人id
     *
     * @return updator_id - 更新人id
     */
    public String getUpdatorId() {
        return updatorId;
    }

    /**
     * 设置更新人id
     *
     * @param updatorId 更新人id
     */
    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除标志(1未删除2删除)
     *
     * @return deleted - 删除标志(1未删除2删除)
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置删除标志(1未删除2删除)
     *
     * @param deleted 删除标志(1未删除2删除)
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}