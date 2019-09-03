package com.car.orbit.orbitservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Title: AlarmVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 预警VO
 * @Author: monkjavaer
 * @Data: 2019/4/1 11:36
 * @Version: V1.0
 */
public class AlarmVO {

    /**
     * 报警id
     */
    private String alarmId;

    /**
     * 处置状态
     */
    private String status;
    /**
     * 车牌
     */
    private String plateNumber;
    /**
     * 图片路径
     */
    private String url;

    /**
     * 警情级别
     */
    private Integer level;

    /**
     * 布控任务名
     */
    private String taskName;

    /**
     * 抓拍时间
     */
    @JsonFormat
    private Date captureTime;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;


    private String cityName;

    private String areaName;

    private String roadName;

    /**
     * 过车id
     */
    private String passVehicleId;

    /**
     * 车辆颜色
     */
    private String vehicleColor;
    /**
     * 车牌颜色
     */
    private String plateColor;

    /**
     * 品牌-子品牌，如果没有子品牌，则只显示品牌（前端要求格式）
     */
    private String fullBrand;

    /**
     * 车辆类型id
     */
    private String vehicleType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPassVehicleId() {
        return passVehicleId;
    }

    public void setPassVehicleId(String passVehicleId) {
        this.passVehicleId = passVehicleId;
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

    public String getFullBrand() {
        return fullBrand;
    }

    public void setFullBrand(String fullBrand) {
        this.fullBrand = fullBrand;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }
}
