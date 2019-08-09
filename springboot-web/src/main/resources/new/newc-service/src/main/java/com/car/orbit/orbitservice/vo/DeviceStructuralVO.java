package com.car.orbit.orbitservice.vo;

import java.io.Serializable;

/**
 * @Title: DeviceStructuralVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 设备VO（用于调用结构化服务器，添加任务）
 * @Author: monkjavaer
 * @Data: 2019/3/22 9:04
 * @Version: V1.0
 */
public class DeviceStructuralVO implements Serializable {
    private String deviceId;
    private String deviceName;
    private String cityId;
    private String cityName;
    private String areaId;
    private String areaName;
    private String roadCrossPointId;
    private String roadName;

    /**
     *卡口属性
     */
    private String ip;
    private Integer port;
    private String userName;
    private String password;
    private Integer manufacturer;

    /**
     * 用于播放的rtsp串
     */
    private String videoPath;

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

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
