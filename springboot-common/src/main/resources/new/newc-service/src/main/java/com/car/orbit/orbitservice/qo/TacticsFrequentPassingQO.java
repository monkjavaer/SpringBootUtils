package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * CreateDate：2019/3/27 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-频繁过车查询条件类
 **/
public class TacticsFrequentPassingQO extends PageParentVO {

    /** 查询的开始时间 */
    private String startTime;

    /** 查询的结束时间 */
    private String endTime;

    /** redis缓存查询key */
    private String searchKey;

    /** 最低过车次数 */
    private int passTimes;

    /** 车辆类型id */
    private List<String> vehicleType;

    /** 车辆颜色id */
    private String vehicleColor;

    /** 车辆品牌id */
    private String brand;

    /** 车辆子品牌id */
    private String childBrand;

    /** 设备id集合 */
    private List<String> deviceIdList;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int getPassTimes() {
        return passTimes;
    }

    public void setPassTimes(int passTimes) {
        this.passTimes = passTimes;
    }

    public List<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<String> vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    @Override
    public String toString() {
        return "TacticsFrequentPassingQO{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", passTimes=" + passTimes +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", brand='" + brand + '\'' +
                ", childBrand='" + childBrand + '\'' +
                ", deviceIdList=" + deviceIdList +
                "} " + super.toString();
    }
}