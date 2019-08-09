package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitservice.entity.OrbitControlTask;
import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;

import java.util.ArrayList;
import java.util.List;

/**布控任务模型
 * CreateDate: 2019-4-3 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
public class ControlTaskMatcher {

    private String taskId;

    public ControlTaskMatcher(String taskId) {
        this.taskId = taskId;
    }

    public ControlTaskMatcher() {
    }

    /** 任务详情列表*/
    private List<ControlTaskDetailMatcher> controlTaskDetailMatcherList = new ArrayList<>();

    /**
     * @param controlTaskDetailMatcher,任务详情实例
     * @return
     * @description 向布控任务中添加任务详情
     * @date: 2019-4-3 9:59
     * @author: monkjavaer
     */
    public void add(ControlTaskDetailMatcher controlTaskDetailMatcher) {
        controlTaskDetailMatcherList.add(controlTaskDetailMatcher);
    }

    /**
     * @param matchers,任务详情实例
     * @return
     * @description 批量向布控任务中添加任务详情
     */
    public void addAll(List<ControlTaskDetailMatcher> matchers) {
        controlTaskDetailMatcherList.addAll(matchers);
    }

    public boolean match(String plateNumber, String plateColor, String deviceId, String brand, String childBrand, String vehicleColor, String vehicleType) {
        for (int i = 0; i < controlTaskDetailMatcherList.size(); i++) {
            if(controlTaskDetailMatcherList.get(i).match(plateNumber, plateColor, deviceId, brand, childBrand, vehicleColor, vehicleType)){
                return true;
            }
        }
        return false;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<ControlTaskDetailMatcher> getControlTaskDetailMatcherList() {
        return controlTaskDetailMatcherList;
    }

    public void setControlTaskDetailMatcherList(List<ControlTaskDetailMatcher> controlTaskDetailMatcherList) {
        this.controlTaskDetailMatcherList = controlTaskDetailMatcherList;
    }
}
