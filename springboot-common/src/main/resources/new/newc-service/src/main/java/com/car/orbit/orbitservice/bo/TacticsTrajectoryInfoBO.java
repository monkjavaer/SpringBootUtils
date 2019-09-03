package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-轨迹分析BO类，供前台分页列表使用，罗列简单的抓拍信息
 **/
public class TacticsTrajectoryInfoBO extends TacticsVehicleBaseInfo {

    /** 通过时间，即抓拍时间 */
    private String captureTime;

    /** 抓拍设备id */
    private String deviceId;

    /** 抓拍设备名称 */
    private String deviceName;

    /** 抓拍所在城市名称 */
    private String cityName;

    /** 抓拍所在区域名称 */
    private String areaName;

    /** 抓拍所在路口名称 */
    private String roadName;

    /** 抓拍图片Url */
    private String photoFastdfsUrl;

    /** 抓拍高清图片Url */
    private String photoOriFastdfsUrl;

    /** 车牌号 */
    private String plateNumber;

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
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

    @Override
    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public String toString() {
        return "TacticsTrajectoryInfoBO{" +
                "captureTime='" + captureTime + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", roadName='" + roadName + '\'' +
                ", photoFastdfsUrl='" + photoFastdfsUrl + '\'' +
                '}';
    }
}