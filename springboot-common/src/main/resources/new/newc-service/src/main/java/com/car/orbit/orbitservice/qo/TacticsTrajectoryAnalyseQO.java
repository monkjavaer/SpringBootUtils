package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-轨迹分析查询对象
 **/
public class TacticsTrajectoryAnalyseQO extends PageParentVO {

    /** 查询的开始时间 */
    private String startTime;

    /** 查询的结束时间 */
    private String endTime;

    /** redis缓存key */
    private String searchKey;

    /** 车牌号 */
    private String plateNumber;

    /** 车牌颜色 */
    private String plateColor;

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

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    @Override
    public String toString() {
        return "TacticsTrajectoryAnalyseQO{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", deviceIdList=" + deviceIdList +
                "} " + super.toString();
    }
}