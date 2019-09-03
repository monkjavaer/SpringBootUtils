package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_res_area")
public class OrbitResArea {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 行政区域编码
     */
    @Column(name = "ADMIN_REGION_CODE")
    private String adminRegionCode;

    /**
     * 城市ID
     */
    @Column(name = "CITY_ID")
    private String cityId;

    /**
     * 是否删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

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
     * 获取名称
     *
     * @return NAME - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取行政区域编码
     *
     * @return ADMIN_REGION_CODE - 行政区域编码
     */
    public String getAdminRegionCode() {
        return adminRegionCode;
    }

    /**
     * 设置行政区域编码
     *
     * @param adminRegionCode 行政区域编码
     */
    public void setAdminRegionCode(String adminRegionCode) {
        this.adminRegionCode = adminRegionCode;
    }

    /**
     * 获取城市ID
     *
     * @return CITY_ID - 城市ID
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置城市ID
     *
     * @param cityId 城市ID
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * 获取是否删除
     *
     * @return DELETED - 是否删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除
     *
     * @param deleted 是否删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}