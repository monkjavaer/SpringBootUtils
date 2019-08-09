package com.car.orbit.orbitservice.vo;

/**
 * CreateDate：2019/3/26 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判中用到的车辆基本信息，各战法研判返回结果需要继承本类
 **/
public class TacticsVehicleBaseInfo {

    /** 车辆ID */
    private String vid;

    /** 车牌号 */
    private String plateNumber;

    /** 车牌颜色id */
    private String plateColor;

    /** 车辆类型id，车辆类型指大车，小车，suv等 */
    private String vehicleType;

    /** 车辆颜色id */
    private String vehicleColor;

    /** 车辆品牌名称 */
    private String brandName;

    /** 车辆子品牌名称 */
    private String childBrandName;

    /** 全量品牌名 */
    private String fullBrand;

    /** 是否存在黑名单库 */
    private boolean blackList;

    /** 是否在布控中*/
    private boolean underControl;

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

    public boolean isBlackList() {
        return blackList;
    }

    public void setBlackList(boolean blackList) {
        this.blackList = blackList;
    }

    @Override
    public String toString() {
        return "TacticsVehicleBaseInfo{" +
                "vid='" + vid + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", brandName='" + brandName + '\'' +
                ", childBrandName='" + childBrandName + '\'' +
                ", blackList=" + blackList +
                '}';
    }

    public boolean isUnderControl() {
        return underControl;
    }

    public void setUnderControl(boolean underControl) {
        this.underControl = underControl;
    }
}