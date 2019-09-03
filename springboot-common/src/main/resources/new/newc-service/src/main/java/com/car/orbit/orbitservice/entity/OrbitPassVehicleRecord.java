package com.car.orbit.orbitservice.entity;

/**
 * @Title: OrbitPassVehicleRecord
 * @Package: com.car.orbit.orbitservice.entity
 * @Description: 过车记录表
 * @Author: monkjavaer
 * @Date: 2019/03/15 18:20
 * @Version: V1.0
 */
public class OrbitPassVehicleRecord {

    /**
     * 过车记录id
     */
    private String id;
    /**
     * 车辆id
     */
    private String vid;
    /**
     * 抓拍时间
     */
    private String captureTime;
    /**
     * 抓拍时间段
     */
    private Short captureHour;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 车辆颜色
     */
    private String vehicleColor;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 车辆类型id
     */
    private String vehicleType;
    /**
     * 车辆品牌id
     */
    private String vehicleBrand;
    /**
     * 车辆品牌名
     */
    private String brandName;
    /**
     * 车辆子品牌id
     */
    private String vehicleBrandChild;
    /**
     * 车辆子品牌名
     */
    private String childBrandName;
    /**
     * 车辆年份
     */
    private String vehicleBrandChildYear;
    /**
     * 车速
     */
    private Short speed;
    /**
     * 车道编号
     */
    private Short roadwayNo;
    /**
     * 车道名称
     */
    private String roadwayName;
    /**
     * 抓拍设备id
     */
    private String deviceId;
    /**
     * 抓拍设备名
     */
    private String deviceName;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    /**
     * 方向编码
     */
    private Short directionCode;
    /**
     * 车辆图片在FastDFS的URL(压缩)
     */
    private String photoFastdfsUrl;
    /**
     * 车辆图片在FastDFS的URL(高清)
     */
    private String photoOriFastdfsUrl;
    /**
     * 城市
     */
    private String cityName;
    /**
     * 区域
     */
    private String areaName;
    /**
     * 路口
     */
    private String roadName;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 路口点位ID
     */
    private String roadCrossPointId;

    /**
     * 以图搜车，相似度
     */
    private Double ret;

    /**
     * 车辆特征
     */
    private String vehicleProperties;
    /**
     * 品牌-子品牌，如果没有子品牌，则只显示品牌（前端要求格式）
     */
    private String fullBrand;

    /**
     * 图片数据转换，用于导出
     */
    private byte[] picture;
    /**
     * 设备位置
     */
    private String devicePosition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public Short getCaptureHour() {
        return captureHour;
    }

    public void setCaptureHour(Short captureHour) {
        this.captureHour = captureHour;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    public String getChildBrandName() {
        return childBrandName;
    }

    public void setChildBrandName(String childBrandName) {
        this.childBrandName = childBrandName;
    }

    public String getVehicleBrandChildYear() {
        return vehicleBrandChildYear;
    }

    public void setVehicleBrandChildYear(String vehicleBrandChildYear) {
        this.vehicleBrandChildYear = vehicleBrandChildYear;
    }

    public Short getSpeed() {
        return speed;
    }

    public void setSpeed(Short speed) {
        this.speed = speed;
    }

    public Short getRoadwayNo() {
        return roadwayNo;
    }

    public void setRoadwayNo(Short roadwayNo) {
        this.roadwayNo = roadwayNo;
    }

    public String getRoadwayName() {
        return roadwayName;
    }

    public void setRoadwayName(String roadwayName) {
        this.roadwayName = roadwayName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Short getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(Short directionCode) {
        this.directionCode = directionCode;
    }

    public String getPhotoFastdfsUrl() {
        return photoFastdfsUrl;
    }

    public void setPhotoFastdfsUrl(String photoFastdfsUrl) {
        this.photoFastdfsUrl = photoFastdfsUrl;
    }

    public String getPhotoOriFastdfsUrl() {
        return photoOriFastdfsUrl;
    }

    public void setPhotoOriFastdfsUrl(String photoOriFastdfsUrl) {
        this.photoOriFastdfsUrl = photoOriFastdfsUrl;
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

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public Double getRet() {
        return ret;
    }

    public void setRet(Double ret) {
        this.ret = ret;
    }

    public String getVehicleProperties() {
        return vehicleProperties;
    }

    public void setVehicleProperties(String vehicleProperties) {
        this.vehicleProperties = vehicleProperties;
    }

    public String getFullBrand() {
        return fullBrand;
    }

    public void setFullBrand(String fullBrand) {
        this.fullBrand = fullBrand;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }
}
