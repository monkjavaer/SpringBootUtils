package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: FirstEntryCityQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 首次入城查询条件
 * @Author: monkjavaer
 * @Date: 2019/03/25 16:11
 * @Version: V1.0
 */
public class FirstEntryCityQO extends PageParentVO {

    /**
     * 聚合结果，Redis key
     */
    private String searchKey;
    /**
     * 入城起始时间
     */
    private String entryStartTime;
    /**
     * 入城结束时间
     */
    private String entryEndTime;
    /**
     * 回溯天数
     */
    private Integer excludeDays;
    /**
     * 车身颜色id
     */
    private String vehicleColor;
    /**
     * 卡口id列表
     */
    private List<String> deviceIdList;
    /**
     * 车辆类别id列表
     */
    private List<String> vehicleTypeList;
    /**
     * 车辆品牌id
     */
    private String brand;
    /**
     * 车辆子品牌id
     */
    private String childBrand;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(String entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public String getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryEndTime(String entryEndTime) {
        this.entryEndTime = entryEndTime;
    }

    public Integer getExcludeDays() {
        return excludeDays;
    }

    public void setExcludeDays(Integer excludeDays) {
        this.excludeDays = excludeDays;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
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
}
