package com.car.orbit.orbitservice.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 车辆档案实体
 * Version: 1.0
 **/
public class OrbitVehicleFile {
    /**
     * 车辆id
     */
    @JSONField(name = "VID")
    private String vid;
    /**
     * 车牌号
     */
    @JSONField(name = "PLATE_NUMBER")
    private String plateNumber;
    /**
     * 车辆颜色
     */
    @JSONField(name = "VEHICLE_COLOR")
    private String vehicleColor;
    /**
     * 车牌颜色
     */
    @JSONField(name = "PLATE_COLOR")
    private String plateColor;
    /**
     * 车辆类型id
     */
    @JSONField(name = "VEHICLE_TYPE")
    private String vehicleType;
    /**
     * 车辆品牌id
     */
    @JSONField(name = "VEHICLE_BRAND")
    private String vehicleBrand;
    /**
     * 车辆子品牌id
     */
    @JSONField(name = "VEHICLE_BRAND_CHILD")
    private String vehicleBrandChild;
    /**
     * 车辆年份
     */
    @JSONField(name = "VEHICLE_BRAND_CHILD_YEAR")
    private String vehicleBrandChildYear;
    /**
     * 车辆图片在FastDFS的URL
     */
    @JSONField(name = "PHOTO_FASTDFS_URL")
    private String photoFastdfsUrl;
    /**
     * 车辆状态
     */
    @JSONField(name = "IS_DECK")
    private Integer isDeck;

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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    public String getVehicleBrandChildYear() {
        return vehicleBrandChildYear;
    }

    public void setVehicleBrandChildYear(String vehicleBrandChildYear) {
        this.vehicleBrandChildYear = vehicleBrandChildYear;
    }

    public String getPhotoFastdfsUrl() {
        return photoFastdfsUrl;
    }

    public void setPhotoFastdfsUrl(String photoFastdfsUrl) {
        this.photoFastdfsUrl = photoFastdfsUrl;
    }

    public Integer getIsDeck() {
        return isDeck;
    }

    public void setIsDeck(Integer isDeck) {
        this.isDeck = isDeck;
    }
}
