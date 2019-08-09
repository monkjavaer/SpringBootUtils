package com.car.orbit.orbitservice.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "orbit_control_task")
public class OrbitControlTask {
    /**
     * 任务id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 任务名称
     */
    @Column(name = "TASK_NAME")
    private String taskName;

    /**
     * 任务状态（1，布控中，2结束，3，审批中，4，未通过）
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 任务等级（1一级，2二级，3三级，4四级）
     */
    @Column(name = "TASK_LEVEL")
    private Integer taskLevel;

    /**
     * 任务简要描述
     */
    @Column(name = "TASK_DESCRIPTION")
    private String taskDescription;

    /**
     * 布控的设备列表，逗号分隔
     */
    @Column(name = "DEVICES")
    private String devices;

    /**
     * 联动方式
     */
    @Column(name = "MESSAGE_TYPE")
    private Integer messageType;

    /**
     * 接收人ID
     */
    @Column(name = "RECEIVOR_ID")
    private String receivorId;

    /**
     * 布控开始时间
     */
    @Column(name = "START_TIME")
    private Date startTime;

    /**
     * 布控结束时间
     */
    @Column(name = "END_TIME")
    private Date endTime;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

    /**
     * 创建人id
     */
    @Column(name = "CREATOR_ID")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 删除标志(1未删除2删除)
     */
    @Column(name = "DELETED")
    private Integer deleted;

    /**
     * 审批人
     */
    @Column(name = "APPROVER")
    private String approver;

    /**
     * 审批人id
     */
    @Column(name = "APPROVER_ID")
    private String approverId;

    /**
     * 审批时间
     */
    @Column(name = "APPROVE_TIME")
    private Date approveTime;

    /**
     * 审批意见
     */
    @Column(name = "APPROVE_NOTE")
    private String approveNote;

    /**
     * 获取任务id
     *
     * @return ID - 任务id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置任务id
     *
     * @param id 任务id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取任务名称
     *
     * @return TASK_NAME - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务状态（1，布控中，2结束，3，审批中，4，未通过）
     *
     * @return STATUS - 任务状态（1，布控中，2结束，3，审批中，4，未通过）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置任务状态（1，布控中，2结束，3，审批中，4，未通过）
     *
     * @param status 任务状态（1，布控中，2结束，3，审批中，4，未通过）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取任务等级（1一级，2二级，3三级，4四级）
     *
     * @return TASK_LEVEL - 任务等级（1一级，2二级，3三级，4四级）
     */
    public Integer getTaskLevel() {
        return taskLevel;
    }

    /**
     * 设置任务等级（1一级，2二级，3三级，4四级）
     *
     * @param taskLevel 任务等级（1一级，2二级，3三级，4四级）
     */
    public void setTaskLevel(Integer taskLevel) {
        this.taskLevel = taskLevel;
    }

    /**
     * 获取任务简要描述
     *
     * @return TASK_DESCRIPTION - 任务简要描述
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * 设置任务简要描述
     *
     * @param taskDescription 任务简要描述
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * 获取联动方式
     *
     * @return MESSAGE_TYPE - 联动方式
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 设置联动方式
     *
     * @param messageType 联动方式
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取接收人ID
     *
     * @return RECEIVOR_ID - 接收人ID
     */
    public String getReceivorId() {
        return receivorId;
    }

    /**
     * 设置接收人ID
     *
     * @param receivorId 接收人ID
     */
    public void setReceivorId(String receivorId) {
        this.receivorId = receivorId;
    }

    /**
     * 获取布控开始时间
     *
     * @return START_TIME - 布控开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置布控开始时间
     *
     * @param startTime 布控开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取布控结束时间
     *
     * @return END_TIME - 布控结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置布控结束时间
     *
     * @param endTime 布控结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取创建人
     *
     * @return CREATOR - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人id
     *
     * @return CREATOR_ID - 创建人id
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人id
     *
     * @param creatorId 创建人id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除标志(1未删除2删除)
     *
     * @return DELETED - 删除标志(1未删除2删除)
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置删除标志(1未删除2删除)
     *
     * @param deleted 删除标志(1未删除2删除)
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取审批人
     *
     * @return APPROVER - 审批人
     */
    public String getApprover() {
        return approver;
    }

    /**
     * 设置审批人
     *
     * @param approver 审批人
     */
    public void setApprover(String approver) {
        this.approver = approver;
    }

    /**
     * 获取审批人id
     *
     * @return APPROVER_ID - 审批人id
     */
    public String getApproverId() {
        return approverId;
    }

    /**
     * 设置审批人id
     *
     * @param approverId 审批人id
     */
    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    /**
     * 获取审批时间
     *
     * @return APPROVE_TIME - 审批时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 设置审批时间
     *
     * @param approveTime 审批时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 获取审批意见
     *
     * @return APPROVE_NOTE - 审批意见
     */
    public String getApproveNote() {
        return approveNote;
    }

    /**
     * 设置审批意见
     *
     * @param approveNote 审批意见
     */
    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }
}