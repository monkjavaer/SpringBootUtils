package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: DayHideNightOutQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 战法研判==》昼伏夜出/夜间频出==》分页查询实体
 * @Author: zks
 * @Date: 2019/03/26 09:43
 * @Version: V1.0
 */
public class DayHideNightOutQO extends PageParentVO {
    /**
     * 查询标识
     */
    private String searchKey;
    /**
     * 白天起始时间
     */
    private int dayTimeStart;
    /**
     * 白天结束时间
     * */
    private int dayTimeEnd;
    /**
     * 白天最多通过次数
     */
    private int dayTimePass;
    /**
     * 晚上最少通过次数
     */
    private int nightTimePass;
    /**
     * 查询区间==开始时间
     */
    private String startTime;
    /**
     * 查询区间==结束时间
     */
    private String endTime;
    /**
     * 设备编号
     */
    private List<String> deviceIds;
    /**
     * 车辆类型CODE列表
     */
    private List<String> vehicleTypeList;
    /**
     * 车辆品牌
     */
    private String brandCode;
    /**
     * 车辆子品牌
     */
    private String childBrandCode;
    /**
     * 车身颜色
     */
    private String bodyColorCode;
    /**
     * 昼伏夜出/夜间频出标识
     * true 昼伏夜出
     * false 夜间频出
     */
    private boolean dayNightFlag;


    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public int getDayTimePass() {
        return dayTimePass;
    }

    public void setDayTimePass(int dayTimePass) {
        this.dayTimePass = dayTimePass;
    }

    public int getNightTimePass() {
        return nightTimePass;
    }

    public void setNightTimePass(int nightTimePass) {
        this.nightTimePass = nightTimePass;
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

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<String> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<String> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getChildBrandCode() {
        return childBrandCode;
    }

    public void setChildBrandCode(String childBrandCode) {
        this.childBrandCode = childBrandCode;
    }

    public String getBodyColorCode() {
        return bodyColorCode;
    }

    public void setBodyColorCode(String bodyColorCode) {
        this.bodyColorCode = bodyColorCode;
    }

    public boolean isDayNightFlag() {
        return dayNightFlag;
    }

    public void setDayNightFlag(boolean dayNightFlag) {
        this.dayNightFlag = dayNightFlag;
    }
}
