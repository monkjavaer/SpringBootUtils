package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: PassVehicleRecordQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 过车记录分页查询
 * @Author: monkjavaer
 * @Date: 2019/03/16 09:43
 * @Version: V1.0
 */
public class PassVehicleRecordQO extends PageParentVO {

    /**
     * 聚合结果，Redis key
     */
    private String searchKey;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 车牌号,支持模糊匹配
     */
    private String plateNumber;
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
     * 车道编号
     */
    private Short roadwayNo;
    /**
     * 车辆类别
     */
    private List<String> vehicleTypeList;

    /**
     * 车身颜色
     */
    private String vehicleColor;

    /**
     * 车牌颜色
     */
    private String plateColor;

    /**
     * 车辆品牌
     */
    private String brand;

    /**
     * 车辆子品牌
     */
    private String childBrand;

    /**
     * 车辆特征属性
     */
    private List<String> feature;
    /**
     * 卡口id列表
     */
    private List<String> deviceIdList;
    /**
     * 是否开启一车一档
     */
    private boolean groupByVid;

    private String vid;
    /**
     * 标识查询结果区分：全天/白天/黑夜
     * 0：all
     * 1：白天
     * 2：黑夜
     */
    private int dayTimeFlag;
    /**
     * 白天起始时间 0~23
     */
    private int dayTimeStart;
    /**
     * 白天结束时间 0~23
     * */
    private int dayTimeEnd;

    /**
     * 是否套牌车
     * */
    private boolean statusException;
    /**
     * 过滤类型,0-全部,1-有车牌,2-无车牌(包括未识别和无牌车)
     */
    private Integer filterType;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
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

    public Short getRoadwayNo() {
        return roadwayNo;
    }

    public void setRoadwayNo(Short roadwayNo) {
        this.roadwayNo = roadwayNo;
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

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
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

    public List<String> getFeature() {
        return feature;
    }

    public void setFeature(List<String> feature) {
        this.feature = feature;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    public boolean isGroupByVid() {
        return groupByVid;
    }

    public void setGroupByVid(boolean groupByVid) {
        this.groupByVid = groupByVid;
    }

    public int getDayTimeFlag() {
        return dayTimeFlag;
    }

    public void setDayTimeFlag(int dayTimeFlag) {
        this.dayTimeFlag = dayTimeFlag;
    }

    public int getDayTimeStart() {
        return dayTimeStart;
    }

    public void setDayTimeStart(int dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    public int getDayTimeEnd() {
        return dayTimeEnd;
    }

    public void setDayTimeEnd(int dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    public boolean isStatusException() {
        return statusException;
    }

    public void setStatusException(boolean statusException) {
        this.statusException = statusException;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }
}
