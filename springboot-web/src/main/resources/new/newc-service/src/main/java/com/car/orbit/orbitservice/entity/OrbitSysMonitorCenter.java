package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "orbit_sys_monitor_center")
public class OrbitSysMonitorCenter {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 行政机构名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 所管辖的行政区域的编码
     */
    @Column(name = "ADMIN_REGION_CODE")
    private String adminRegionCode;

    /**
     * 文件服务器的IP
     */
    @Column(name = "IP")
    private String ip;

    /**
     * port
     */
    @Column(name = "PORT")
    private Integer port;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    @Column(name = "UNIFIED_ALARM_URL")
    private String unifiedAlarmUrl;

    @Column(name = "VIDEO_SERVER_IP")
    private String videoServerIp;

    @Column(name = "VIDEO_SDK")
    private String videoSdk;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "ROAD_NAME")
    private String roadName;

    @Column(name = "ROAD_ID")
    private String roadId;

    /**
     * 级别id，（选择城市或者区域就用相应的ID逗号隔开，选择全部不传值）
     */
    @Column(name = "LEVEL_ID")
    private String levelId;

    @Column(name = "LEVEL_NAME")
    private String levelName;

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
     * 获取行政机构名称
     *
     * @return NAME - 行政机构名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置行政机构名称
     *
     * @param name 行政机构名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取地址
     *
     * @return ADDRESS - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系电话
     *
     * @return PHONE - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取所管辖的行政区域的编码
     *
     * @return ADMIN_REGION_CODE - 所管辖的行政区域的编码
     */
    public String getAdminRegionCode() {
        return adminRegionCode;
    }

    /**
     * 设置所管辖的行政区域的编码
     *
     * @param adminRegionCode 所管辖的行政区域的编码
     */
    public void setAdminRegionCode(String adminRegionCode) {
        this.adminRegionCode = adminRegionCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    /**
     * @return UNIFIED_ALARM_URL
     */
    public String getUnifiedAlarmUrl() {
        return unifiedAlarmUrl;
    }

    /**
     * @param unifiedAlarmUrl
     */
    public void setUnifiedAlarmUrl(String unifiedAlarmUrl) {
        this.unifiedAlarmUrl = unifiedAlarmUrl;
    }

    /**
     * @return VIDEO_SERVER_IP
     */
    public String getVideoServerIp() {
        return videoServerIp;
    }

    /**
     * @param videoServerIp
     */
    public void setVideoServerIp(String videoServerIp) {
        this.videoServerIp = videoServerIp;
    }

    /**
     * @return VIDEO_SDK
     */
    public String getVideoSdk() {
        return videoSdk;
    }

    /**
     * @param videoSdk
     */
    public void setVideoSdk(String videoSdk) {
        this.videoSdk = videoSdk;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}