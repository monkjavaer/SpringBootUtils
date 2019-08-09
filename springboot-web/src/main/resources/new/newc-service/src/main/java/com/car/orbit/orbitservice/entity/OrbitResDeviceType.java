package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_res_device_type")
public class OrbitResDeviceType {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 设备类型代号
     */
    @Column(name = "CODE")
    private Integer code;

    /**
     * 设备类型名字
     */
    @Column(name = "NAME")
    private String name;

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
     * 获取设备类型代号
     *
     * @return CODE - 设备类型代号
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置设备类型代号
     *
     * @param code 设备类型代号
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取设备类型名字
     *
     * @return NAME - 设备类型名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备类型名字
     *
     * @param name 设备类型名字
     */
    public void setName(String name) {
        this.name = name;
    }
}