package com.car.orbit.orbitservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "orbit_control_whitelist")
public class OrbitControlWhitelist {
    /**
     * 主键
     */
    @Id
    @Column(name = "VID")
    private String vid;

    /**
     * 车牌号
     */
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    /**
     * 车辆品牌
     */
    @Column(name = "VEHICLE_BRAND")
    private String vehicleBrand;

    /**
     * 车辆子品牌
     */
    @Column(name = "VEHICLE_BRAND_CHILD")
    private String vehicleBrandChild;

    /**
     * 车辆年份 
     */
    @Column(name = "VEHICLE_BRAND_CHILD_YEAR")
    private String vehicleBrandChildYear;

    /**
     * 车牌颜色
     */
    @Column(name = "PLATE_COLOR")
    private String plateColor;

    /**
     * 车辆颜色
     */
    @Column(name = "VEHICLE_COLOR")
    private String vehicleColor;

    /**
     * 白名单类型
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @JsonFormat
    private Date createTime;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    /**
     * 是否启用0关闭，1开启
     */
    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "UPDATE_TIME")
    @JsonFormat
    private Date updateTime;

    @Column(name = "PHOTO_URL")
    private String photoUrl;

    /**
     * 白名单类型名
     */
    @Transient
    private String typeName;
    /**
     * 全量品牌名
     */
    @Transient
    private String fullBrand;

    /**
     * 获取主键
     *
     * @return VID - 主键
     */
    public String getVid() {
        return vid;
    }

    /**
     * 设置主键
     *
     * @param vid 主键
     */
    public void setVid(String vid) {
        this.vid = vid;
    }

    /**
     * 获取车牌号
     *
     * @return PLATE_NUMBER - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取车辆品牌
     *
     * @return VEHICLE_BRAND - 车辆品牌
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    /**
     * 设置车辆品牌
     *
     * @param vehicleBrand 车辆品牌
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    /**
     * 获取车辆子品牌
     *
     * @return VEHICLE_BRAND_CHILD - 车辆子品牌
     */
    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    /**
     * 设置车辆子品牌
     *
     * @param vehicleBrandChild 车辆子品牌
     */
    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    /**
     * 获取车辆年份 
     *
     * @return VEHICLE_BRAND_CHILD_YEAR - 车辆年份 
     */
    public String getVehicleBrandChildYear() {
        return vehicleBrandChildYear;
    }

    /**
     * 设置车辆年份 
     *
     * @param vehicleBrandChildYear 车辆年份 
     */
    public void setVehicleBrandChildYear(String vehicleBrandChildYear) {
        this.vehicleBrandChildYear = vehicleBrandChildYear;
    }

    /**
     * 获取车牌颜色
     *
     * @return PLATE_COLOR - 车牌颜色
     */
    public String getPlateColor() {
        return plateColor;
    }

    /**
     * 设置车牌颜色
     *
     * @param plateColor 车牌颜色
     */
    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    /**
     * 获取车辆颜色
     *
     * @return VEHICLE_COLOR - 车辆颜色
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * 设置车辆颜色
     *
     * @param vehicleColor 车辆颜色
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    /**
     * 获取白名单类型
     *
     * @return TYPE - 白名单类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置白名单类型
     *
     * @param type 白名单类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取是否已删除
     *
     * @return DELETED - 是否已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否已删除
     *
     * @param deleted 是否已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取是否启用0关闭，1开启
     *
     * @return STATUS - 是否启用0关闭，1开启
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用0关闭，1开启
     *
     * @param status 是否启用0关闭，1开启
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return VEHICLE_TYPE
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return PHOTO_URL
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFullBrand() {
        return fullBrand;
    }

    public void setFullBrand(String fullBrand) {
        this.fullBrand = fullBrand;
    }
}