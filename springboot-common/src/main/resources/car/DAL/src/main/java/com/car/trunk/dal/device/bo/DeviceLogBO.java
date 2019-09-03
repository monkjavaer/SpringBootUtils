package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.bo;

/**
 * Created by monkjavaer on 2017/12/7 0007.
 */
public class DeviceLogBO {
    private String name;
    private Integer type;
    private Byte online;
    private String cityName;
    private String areaName;
    private String roadName;
    private String installAddress;
    private String createTime;
    private String recentTime;
    private Integer continuedTime;
    private Float onlineRate;

    public Float getOnlineRate() {
        return onlineRate;
    }

    public void setOnlineRate(Float onlineRate) {
        this.onlineRate = onlineRate;
    }

    public String getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(String recentTime) {
        this.recentTime = recentTime;
    }

    public Integer getContinuedTime() {
        return continuedTime;
    }

    public void setContinuedTime(Integer continuedTime) {
        this.continuedTime = continuedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
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

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
