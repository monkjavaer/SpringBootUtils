package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;
import com.car.orbit.orbitservice.vo.ControlTaskVO;

import java.util.List;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.bo
 * @ClassName: ControlTaskBO
 * @Description: 布控任务业务对象，用于存储添加布控时的大对象，包含一个任务的所有信息
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 16:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 16:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ControlTaskBO {

    /**任务信息*/
    private ControlTaskVO task;

    /**关联的车辆信息*/
    private List<OrbitControlTaskDetail> taskDetails;

    public ControlTaskBO() {
    }

    public ControlTaskBO(ControlTaskVO task, List<OrbitControlTaskDetail> taskDetails) {
        this.task = task;
        this.taskDetails = taskDetails;
    }

    public ControlTaskVO getTask() {
        return task;
    }

    public void setTask(ControlTaskVO task) {
        this.task = task;
    }

    public List<OrbitControlTaskDetail> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(List<OrbitControlTaskDetail> taskDetails) {
        this.taskDetails = taskDetails;
    }


}
