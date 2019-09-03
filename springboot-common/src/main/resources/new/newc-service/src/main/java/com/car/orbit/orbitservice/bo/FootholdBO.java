package com.car.orbit.orbitservice.bo;

/**
 * @Title: FootholdBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 落脚点数据
 * @Author: monkjavaer
 * @Date: 2019/03/28 11:16
 * @Version: V1.0
 */
public class FootholdBO {

    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 设备位置
     */
    private String devicePosition;
    /**
     * 落脚次数
     */
    private Integer landingNumber;
    /**
     * 落脚总时长
     */
    private Double totalHour;
    /**
     * 白天落脚总时长
     */
    private Double dayHour;
    /**
     * 夜晚落脚总时长
     */
    private Double nightHour;
    /**
     * 白天占比
     */
    private String daytimePercentage;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;

    public FootholdBO() {
        landingNumber = 0;
        totalHour = 0.0;
        dayHour = 0.0;
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

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public Integer getLandingNumber() {
        return landingNumber;
    }

    public void setLandingNumber(Integer landingNumber) {
        this.landingNumber = landingNumber;
    }

    public Double getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(Double totalHour) {
        this.totalHour = totalHour;
    }

    public Double getDayHour() {
        return dayHour;
    }

    public void setDayHour(Double dayHour) {
        this.dayHour = dayHour;
    }

    public Double getNightHour() {
        return nightHour;
    }

    public void setNightHour(Double nightHour) {
        this.nightHour = nightHour;
    }

    public String getDaytimePercentage() {
        return daytimePercentage;
    }

    public void setDaytimePercentage(String daytimePercentage) {
        this.daytimePercentage = daytimePercentage;
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
