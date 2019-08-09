package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.util.List;

/**
 * @Title: LogQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 日志查询QO
 * @Author: monkjavaer
 * @Data: 2019/3/13 20:15
 * @Version: V1.0
 */
public class LogQO extends PageParentVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 操作类型
     */
    private Integer actionType;
    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /** 需要查询的用户id列表 */
    private List<String> userIdList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
