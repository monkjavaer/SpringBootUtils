package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: TogetherVehicleQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 同行车分页查询对象
 * @Author: monkjavaer
 * @Data: 2019/3/27 17:00
 * @Version: V1.0
 */
public class TogetherVehicleQO extends PageParentVO {
    /**
     * 聚合结果，Redis key
     */
    private String searchKey;

    /**
     * 同行次数大于多少
     */
    private Integer togetherCount;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 时差(分钟)
     */
    private Integer jetLag;
    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 同行车车牌
     */
    private String togetherPlateNumber;
    /**
     * 同行车车牌颜色
     */
    private String togetherPlateColor;
    /**
     * 同行车vid
     */
    private String togetherVid;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 路口id
     */
    private String roadCrossPointId;

    /**
     * 卡口id列表
     */
    private List<String> deviceIdList;

    /**
     * 车辆类别
     */
    private List<String> vehicleTypeList;

    /**
     * 车身颜色
     */
    private String vehicleColor;

    /**
     * 车辆品牌
     */
    private String brand;

    /**
     * 车辆子品牌
     */
    private String childBrand;

    public Integer getTogetherCount() {
        return togetherCount;
    }

    public void setTogetherCount(Integer togetherCount) {
        this.togetherCount = togetherCount;
    }

    public String getTogetherPlateNumber() {
        return togetherPlateNumber;
    }

    public void setTogetherPlateNumber(String togetherPlateNumber) {
        this.togetherPlateNumber = togetherPlateNumber;
    }

    public String getTogetherPlateColor() {
        return togetherPlateColor;
    }

    public void setTogetherPlateColor(String togetherPlateColor) {
        this.togetherPlateColor = togetherPlateColor;
    }

    public String getTogetherVid() {
        return togetherVid;
    }

    public void setTogetherVid(String togetherVid) {
        this.togetherVid = togetherVid;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

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

    public Integer getJetLag() {
        return jetLag;
    }

    public void setJetLag(Integer jetLag) {
        this.jetLag = jetLag;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
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
}
