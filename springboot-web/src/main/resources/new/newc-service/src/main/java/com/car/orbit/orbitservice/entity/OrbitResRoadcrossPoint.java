package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_res_roadcross_point")
public class OrbitResRoadcrossPoint {
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
     * 纬度
     */
    @Column(name = "LATITUDE")
    private Double latitude;

    /**
     * 经度
     */
    @Column(name = "LONGITUDE")
    private Double longitude;

    /**
     * 道路方向(可能是双向)
     */
    @Column(name = "DIRECTION_CODE")
    private Integer directionCode;

    /**
     * 车道数量
     */
    @Column(name = "ROADWAY_NUM")
    private Integer roadwayNum;

    @Column(name = "AREA_ID")
    private String areaId;

    /**
     * 是否已删除
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
     * 获取纬度
     *
     * @return LATITUDE - 纬度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取经度
     *
     * @return LONGITUDE - 经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取道路方向(可能是双向)
     *
     * @return DIRECTION_CODE - 道路方向(可能是双向)
     */
    public Integer getDirectionCode() {
        return directionCode;
    }

    /**
     * 设置道路方向(可能是双向)
     *
     * @param directionCode 道路方向(可能是双向)
     */
    public void setDirectionCode(Integer directionCode) {
        this.directionCode = directionCode;
    }

    /**
     * 获取车道数量
     *
     * @return ROADWAY_NUM - 车道数量
     */
    public Integer getRoadwayNum() {
        return roadwayNum;
    }

    /**
     * 设置车道数量
     *
     * @param roadwayNum 车道数量
     */
    public void setRoadwayNum(Integer roadwayNum) {
        this.roadwayNum = roadwayNum;
    }

    /**
     * @return AREA_ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取是否已删除
     *
     * @return DELETED - 是否已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否已删除
     *
     * @param deleted 是否已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}