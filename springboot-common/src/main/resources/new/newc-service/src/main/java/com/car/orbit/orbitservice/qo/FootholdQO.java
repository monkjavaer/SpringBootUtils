package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: FootholdQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 落脚点分析查询条件
 * @Author: monkjavaer
 * @Date: 2019/03/28 11:26
 * @Version: V1.0
 */
public class FootholdQO extends PageParentVO {

    /**
     * 聚合结果，Redis key
     */
    private String searchKey;
    /**
     * 精确车牌号
     */
    private String plateNumber;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 卡口id列表
     */
    private List<String> deviceIdList;
    /**
     * 最小落脚时长
     */
    private Integer minHour;
    /**
     * 白天区间的开始小时
     */
    private Integer daytimeStartHour;
    /**
     * 白天区间的结束小时
     */
    private Integer daytimeEndHour;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    public Integer getMinHour() {
        return minHour;
    }

    public void setMinHour(Integer minHour) {
        this.minHour = minHour;
    }

    public Integer getDaytimeStartHour() {
        return daytimeStartHour;
    }

    public void setDaytimeStartHour(Integer daytimeStartHour) {
        this.daytimeStartHour = daytimeStartHour;
    }

    public Integer getDaytimeEndHour() {
        return daytimeEndHour;
    }

    public void setDaytimeEndHour(Integer daytimeEndHour) {
        this.daytimeEndHour = daytimeEndHour;
    }
}
