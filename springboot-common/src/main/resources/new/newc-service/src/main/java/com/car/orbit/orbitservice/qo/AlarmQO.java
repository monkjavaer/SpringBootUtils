package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: AlarmQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 预警查询
 * @Author: monkjavaer
 * @Data: 2019/4/1 11:47
 * @Version: V1.0
 */
public class AlarmQO extends PageParentVO {

    /**
     * 处置状态
     */
    private Integer status;

    /**
     * 任务名
     */
    private String taskName;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 开始时间
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

    private String deviceIdListStr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 用户id
     */
    private String userId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getDeviceIdListStr() {
        return deviceIdListStr;
    }

    public void setDeviceIdListStr(String deviceIdListStr) {
        this.deviceIdListStr = deviceIdListStr;
    }
}
