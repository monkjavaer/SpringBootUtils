package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: BlackListQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 黑名单分页查询
 * @Author: monkjavaer
 * @Date: 2019/04/02 19:39
 * @Version: V1.0
 */
public class BlackListQO extends PageParentVO {

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
     * 状态
     */
    private Integer status;
    /**
     * 黑名单类型
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
