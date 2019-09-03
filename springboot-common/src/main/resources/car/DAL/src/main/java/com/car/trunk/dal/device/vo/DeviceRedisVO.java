package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.vo;

import java.math.BigInteger;

/**
 * @Title: DeviceRedisVO
 * @Package: com.car.trunk.dal.device.vo
 * @Description: 设备信息放入redis
 * @Author: monkjavaer
 * @Date: 2019/5/28 14:24
 * @Version: V1.0
 */
public class DeviceRedisVO {
    /**
     * 设备ID(设备主键)
     */
    private BigInteger deviceId;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 路口id
     */
    private BigInteger roadId;

    /**
     * 路口名
     */
    private String roadName;

    /**
     * 城市ID
     */
    private BigInteger cityId;

    /**
     * 城市名
     */
    private String cityName;
    /**
     * 区域ID
     */
    private BigInteger areaId;

    /**
     * 区域名
     */
    private String areaName;

    /**
     * 宇视卡口编码
     */
    private String bayonetNo;


    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    public BigInteger getRoadId() {
        return roadId;
    }

    public void setRoadId(BigInteger roadId) {
        this.roadId = roadId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBayonetNo() {
        return bayonetNo;
    }

    public void setBayonetNo(String bayonetNo) {
        this.bayonetNo = bayonetNo;
    }
}
