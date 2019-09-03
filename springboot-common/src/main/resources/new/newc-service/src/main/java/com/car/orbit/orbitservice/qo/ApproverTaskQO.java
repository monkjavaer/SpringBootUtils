package com.car.orbit.orbitservice.qo;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.qo
 * @ClassName: ApproverTaskQO
 * @Description: 审批类
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 15:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 15:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ApproverTaskQO {

    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务状态
     */
    private int status;
    /**
     * 任务备注
     */
    private String note;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}