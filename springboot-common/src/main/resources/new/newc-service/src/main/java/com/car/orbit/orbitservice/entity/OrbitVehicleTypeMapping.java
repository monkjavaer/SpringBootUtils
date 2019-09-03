package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "orbit_vehicle_type_mapping")
public class OrbitVehicleTypeMapping {

    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 国家编码
     */
    @Column(name = "COUNTRY")
    private String country;

    /**
     * 类型编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 类型-中文
     */
    @Column(name = "ZH_NAME")
    private String zhName;

    /**
     * 类型-英文
     */
    @Column(name = "EN_NAME")
    private String enName;

    /**
     * 类型-西语
     */
    @Column(name = "ES_NAME")
    private String esName;

    /**
     * 原类型编码
     */
    @Column(name = "SOURCE_CODE")
    private String sourceCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}