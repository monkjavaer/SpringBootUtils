package com.car.orbit.orbitservice.vo;

/**
 * @Title: VehicleTrajectoryVO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 车辆轨迹VO
 * @Author: monkjavaer
 * @Data: 2019/3/21 11:14
 * @Version: V1.0
 */
public class VehicleTrajectoryVO {

    /**
     * 过车id
     */
    private String id;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 抓拍时间
     */
    private String captureTime;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;

    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 城市名
     */
    private String cityName;
    /**
     * 区域名
     */
    private String areaName;
    /**
     * 路口名
     */
    private String roadName;

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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
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
}
