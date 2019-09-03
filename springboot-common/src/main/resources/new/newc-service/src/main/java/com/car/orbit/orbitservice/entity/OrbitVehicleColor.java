package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_vehicle_color")
public class OrbitVehicleColor {
    /**
     * 颜色代码
     */
    @Id
    @Column(name = "CODE")
    private String code;

    /**
     * 中文名
     */
    @Column(name = "ZH_NAME")
    private String zhName;

    /**
     * 英文名
     */
    @Column(name = "EN_NAME")
    private String enName;

    /**
     * 西班牙语
     */
    @Column(name = "ES_NAME")
    private String esName;

    /**
     * 获取颜色代码
     *
     * @return CODE - 颜色代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置颜色代码
     *
     * @param code 颜色代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取中文名
     *
     * @return ZH_NAME - 中文名
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * 设置中文名
     *
     * @param zhName 中文名
     */
    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    /**
     * 获取英文名
     *
     * @return EN_NAME - 英文名
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 设置英文名
     *
     * @param enName 英文名
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 获取西班牙语
     *
     * @return ES_NAME - 西班牙语
     */
    public String getEsName() {
        return esName;
    }

    /**
     * 设置西班牙语
     *
     * @param esName 西班牙语
     */
    public void setEsName(String esName) {
        this.esName = esName;
    }
}