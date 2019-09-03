package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;

/**
 * @Title: FirstEntryCityBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 首次入城记录
 * @Author: monkjavaer
 * @Date: 2019/03/25 15:55
 * @Version: V1.0
 */
public class FirstEntryCityBO extends TacticsVehicleBaseInfo {

    /**
     * 首次入城时间
     */
    private String entryTime;
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
     * 设备id
     */
    private String deviceId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 抓拍图片url
     */
    private String photoFastdfsUrl;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhotoFastdfsUrl() {
        return photoFastdfsUrl;
    }

    public void setPhotoFastdfsUrl(String photoFastdfsUrl) {
        this.photoFastdfsUrl = photoFastdfsUrl;
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
