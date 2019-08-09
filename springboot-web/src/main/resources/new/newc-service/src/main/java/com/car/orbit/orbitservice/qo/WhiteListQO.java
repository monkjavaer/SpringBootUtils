package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: WhiteListQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 白名单分页查询
 * @Author: monkjavaer
 * @Date: 2019/03/30 14:01
 * @Version: V1.0
 */
public class WhiteListQO extends PageParentVO {

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
     * 车辆类别
     */
    private List<String> vehicleTypeList;
    /**
     * 白名单类型
     */
    private String type;

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

    public List<String> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<String> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
