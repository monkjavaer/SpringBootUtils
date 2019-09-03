package com.car.orbit.orbitservice.vo;

/**
 * @Title: DeviceTreeVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: DeviceTreeVO
 * @Author: monkjavaer
 * @Data: 2019/3/22 18:58
 * @Version: V1.0
 */
public class DeviceTreeVO {
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 在线状态
     */
    private Integer online;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    private String cityId;
    private String cityName;
    private String areaId;
    private String areaName;
    private String roadCrossPointId;
    private String roadName;

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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
}
