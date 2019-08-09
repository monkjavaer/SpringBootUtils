package com.car.orbit.orbitservice.qo;

import java.util.List;

/**
 * CreateDate: 2019-3-26 <br/>
 * Author: monkjavaer <br/>
 * Description: 隐匿车辆查询QO
 * Version: 1.0
 **/
public class HiddenVehiclesQO {
    /**
     * 案发时间
     */
    private String timeOfTheIncident;

    /**
     * 案发前后时间段（天）
     */
    private Integer timePeriodBeforeAfterTheIncident;

    /**
     * 案发前过车次数
     */
    private Integer passesBeforeTheIncident;

    /**
     * 案发后过车次数
     */
    private Integer passesAfterTheIncident;

    /**
     * 车辆类型id列表
     */
    private List<String> vehicleTypeList;

    /**
     * 品牌id
     */
    private String brand;

    /**
     * 子品牌id
     */
    private String childBrand;

    /**
     * 设备id列表
     */
    private List<String> deviceIdList;

    /**
     * 车身颜色id
     */
    private String vehicleColor;

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页显示的最大记录数
     */
    private Integer pageSize;

    /**
     * 查询key
     */
    private String searchKey;

    public String getTimeOfTheIncident() {
        return timeOfTheIncident;
    }

    public void setTimeOfTheIncident(String timeOfTheIncident) {
        this.timeOfTheIncident = timeOfTheIncident;
    }

    public Integer getTimePeriodBeforeAfterTheIncident() {
        return timePeriodBeforeAfterTheIncident;
    }

    public void setTimePeriodBeforeAfterTheIncident(Integer timePeriodBeforeAfterTheIncident) {
        this.timePeriodBeforeAfterTheIncident = timePeriodBeforeAfterTheIncident;
    }

    public Integer getPassesBeforeTheIncident() {
        return passesBeforeTheIncident;
    }

    public void setPassesBeforeTheIncident(Integer passesBeforeTheIncident) {
        this.passesBeforeTheIncident = passesBeforeTheIncident;
    }

    public Integer getPassesAfterTheIncident() {
        return passesAfterTheIncident;
    }

    public void setPassesAfterTheIncident(Integer passesAfterTheIncident) {
        this.passesAfterTheIncident = passesAfterTheIncident;
    }

    public List<String> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<String> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
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

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
