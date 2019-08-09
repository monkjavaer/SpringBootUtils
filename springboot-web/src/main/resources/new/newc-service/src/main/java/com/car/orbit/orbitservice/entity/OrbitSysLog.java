package com.car.orbit.orbitservice.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "orbit_sys_log")
public class OrbitSysLog {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     */
    @Column(name = "DATA_TYPE")
    private Integer dataType;

    /**
     * 操作类型(0,添加；1，修改；2，删除)
     */
    @Column(name = "ACTION_TYPE")
    private Integer actionType;

    /**
     * 操作参数
     */
    @Column(name = "DESCRIPTION")
    private String description;

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
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
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
     * 获取用户ID
     *
     * @return USER_ID - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     *
     * @return DATA_TYPE - 数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * 设置数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     *
     * @param dataType 数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取操作类型(0,添加；1，修改；2，删除)
     *
     * @return ACTION_TYPE - 操作类型(0,添加；1，修改；2，删除)
     */
    public Integer getActionType() {
        return actionType;
    }

    /**
     * 设置操作类型(0,添加；1，修改；2，删除)
     *
     * @param actionType 操作类型(0,添加；1，修改；2，删除)
     */
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    /**
     * 获取操作参数
     *
     * @return DESCRIPTION - 操作参数
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置操作参数
     *
     * @param description 操作参数
     */
    public void setDescription(String description) {
        this.description = description;
    }
}