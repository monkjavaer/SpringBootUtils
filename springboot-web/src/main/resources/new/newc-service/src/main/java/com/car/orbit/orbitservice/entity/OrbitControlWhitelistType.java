package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_control_whitelist_type")
public class OrbitControlWhitelistType {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 类型名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    /**
     * 是否可删除
     */
    @Transient
    private boolean deletable;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)
     *
     * @return TYPE - 类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)
     *
     * @param type 类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取类型名称
     *
     * @return NAME - 类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类型名称
     *
     * @param name 类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}