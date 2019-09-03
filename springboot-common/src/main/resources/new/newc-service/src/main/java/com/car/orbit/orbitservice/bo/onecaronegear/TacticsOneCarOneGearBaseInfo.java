package com.car.orbit.orbitservice.bo.onecaronegear;

import java.util.List;

/**
 * @Title: TacticsOneCarOneGearBaseInfo
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 一车一档基础信息结果返回
 * @Author: monkjavaer
 * @Data: 2019/3/29 10:23
 * @Version: V1.0
 */
public class TacticsOneCarOneGearBaseInfo {
    //过车id
    private String vid;

    //车牌号
    private String plateNumber;

    //车牌颜色
    private String plateColor;

    //车辆类型
    private String vehicleType;

    //车辆颜色
    private String vehicleColor;

    //车辆品牌名称
    private String brandName;

    //车辆子品牌名称
    private String childBrandName;

    // 全量品牌名
    private String fullBrand;

    //车辆照片名称
    private List<String> photoFastDfsUrl;

    //车辆归属地名称
    private String locationAt;

    //车辆年检标
    private List<String> tag;

    public List<String> getPhotoFastDfsUrl() {
        return photoFastDfsUrl;
    }

    public void setPhotoFastDfsUrl(List<String> photoFastDfsUrl) {
        this.photoFastDfsUrl = photoFastDfsUrl;
    }

    public String getLocationAt() {
        return locationAt;
    }

    public void setLocationAt(String locationAt) {
        this.locationAt = locationAt;
    }

    //车辆年检标
    private List<OneCarStatisticsNameAndTime> riskStatistics;
    public TacticsOneCarOneGearBaseInfo(){}

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getChildBrandName() {
        return childBrandName;
    }

    public void setChildBrandName(String childBrandName) {
        this.childBrandName = childBrandName;
    }

    public String getFullBrand() {
        return fullBrand;
    }

    public void setFullBrand(String fullBrand) {
        this.fullBrand = fullBrand;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<OneCarStatisticsNameAndTime> getRiskStatistics() {
        return riskStatistics;
    }

    public void setRiskStatistics(List<OneCarStatisticsNameAndTime> riskStatistics) {
        this.riskStatistics = riskStatistics;
    }


}
