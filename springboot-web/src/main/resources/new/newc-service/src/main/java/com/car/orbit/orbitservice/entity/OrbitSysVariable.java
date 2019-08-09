package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_sys_variable")
public class OrbitSysVariable {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 变量名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 变量值 (-1永久）
     */
    @Column(name = "VALUE")
    private Integer value;

    /**
     * 单位
     */
    @Column(name = "UNIT")
    private String unit;
    /**
     * 单位
     */
    @Column(name = "TYPE")
    private String type;

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
     * 获取变量名
     *
     * @return NAME - 变量名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置变量名
     *
     * @param name 变量名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取变量值 (-1永久）
     *
     * @return VALUE - 变量值 (-1永久）
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 设置变量值 (-1永久）
     *
     * @param value 变量值 (-1永久）
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 获取单位
     *
     * @return UNIT - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}