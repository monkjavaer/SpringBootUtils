package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "orbit_control_white_record")
public class OrbitControlWhiteRecord {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车牌号
     */
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    /**
     * 设备ID
     */
    @Column(name = "DEVICE_ID")
    private String deviceId;

    /**
     * 抓拍时间
     */
    @Column(name = "CAPTURE_TIME")
    private Date captureTime;

    /**
     * 车道编号
     */
    @Column(name = "ROADWAY_NO")
    private Integer roadwayNo;

    /**
     * 白名单类型
     */
    @Column(name = "WHITE_LIST_TYPE")
    private String whiteListType;

    /**
     * 车辆图片在FastDFS的URL
     */
    @Column(name = "PHOTO_FASTDFS_URL")
    private String photoFastdfsUrl;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "ROAD_NAME")
    private String roadName;

    /**
     * 城市ID
     */
    @Column(name = "CITY_ID")
    private String cityId;

    /**
     * 区域ID
     */
    @Column(name = "AREA_ID")
    private String areaId;

    /**
     * 路口点位ID
     */
    @Column(name = "ROAD_CROSS_POINT_ID")
    private String roadCrossPointId;

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
     * 获取车牌号
     *
     * @return PLATE_NUMBER - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取设备ID
     *
     * @return DEVICE_ID - 设备ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置设备ID
     *
     * @param deviceId 设备ID
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取抓拍时间
     *
     * @return CAPTURE_TIME - 抓拍时间
     */
    public Date getCaptureTime() {
        return captureTime;
    }

    /**
     * 设置抓拍时间
     *
     * @param captureTime 抓拍时间
     */
    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    /**
     * 获取车道编号
     *
     * @return ROADWAY_NO - 车道编号
     */
    public Integer getRoadwayNo() {
        return roadwayNo;
    }

    /**
     * 设置车道编号
     *
     * @param roadwayNo 车道编号
     */
    public void setRoadwayNo(Integer roadwayNo) {
        this.roadwayNo = roadwayNo;
    }

    /**
     * 获取白名单类型
     *
     * @return WHITE_LIST_TYPE - 白名单类型
     */
    public String getWhiteListType() {
        return whiteListType;
    }

    /**
     * 设置白名单类型
     *
     * @param whiteListType 白名单类型
     */
    public void setWhiteListType(String whiteListType) {
        this.whiteListType = whiteListType;
    }

    /**
     * 获取车辆图片在FastDFS的URL
     *
     * @return PHOTO_FASTDFS_URL - 车辆图片在FastDFS的URL
     */
    public String getPhotoFastdfsUrl() {
        return photoFastdfsUrl;
    }

    /**
     * 设置车辆图片在FastDFS的URL
     *
     * @param photoFastdfsUrl 车辆图片在FastDFS的URL
     */
    public void setPhotoFastdfsUrl(String photoFastdfsUrl) {
        this.photoFastdfsUrl = photoFastdfsUrl;
    }

    /**
     * @return CITY_NAME
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return AREA_NAME
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return ROAD_NAME
     */
    public String getRoadName() {
        return roadName;
    }

    /**
     * @param roadName
     */
    public void setRoadName(String roadName) {
        this.roadName = roadName;
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
     * 获取区域ID
     *
     * @return AREA_ID - 区域ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置区域ID
     *
     * @param areaId 区域ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取路口点位ID
     *
     * @return ROAD_CROSS_POINT_ID - 路口点位ID
     */
    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    /**
     * 设置路口点位ID
     *
     * @param roadCrossPointId 路口点位ID
     */
    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }
}