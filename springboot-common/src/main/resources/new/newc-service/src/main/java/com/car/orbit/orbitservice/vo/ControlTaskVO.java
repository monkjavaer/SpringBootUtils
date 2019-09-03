package com.car.orbit.orbitservice.vo;

import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


public class ControlTaskVO{

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务状态（1，布控中，2结束，3，审批中，4，未通过）
     */
    private Integer status;

    /**
     * 任务等级（1一级，2二级，3三级，4四级）
     */
    private Integer taskLevel;

    /**
     * 任务简要描述
     */
    private String taskDescription;

    /**
     * 布控的设备列表，逗号分隔
     */
    private String devices;

    /**
     * 联动方式
     */
    private Integer messageType;

    /**
     * 接收人ID
     */
    private String receivorId;

    /**
     * 布控开始时间
     */
    @JsonFormat
    private Date startTime;

    /**
     * 布控结束时间
     */
    @JsonFormat
    private Date endTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建时间
     */
    @JsonFormat
    private Date createTime;

    /**
     * 删除标志(1未删除2删除)
     */
    private Integer deleted;

    /**
     * 审批人
     */
    private String approver;

    /**
     * 审批人id
     */
    private String approverId;

    /**
     * 审批时间
     */
    @JsonFormat
    private Date approveTime;

    /**
     * 审批意见
     */
    private String approveNote;

    /**
     * 声音提示
     */
    private boolean voz;

    /**
     * SMS
     */
    private boolean sms;
    /**
     * 创建人图像URL
     */
    private String creatorPicUrl;
    /**
     * 创建人电话
     */
    private String creatorTel;
    /**
     * 审批人图像URL
     */
    private String approverPicUrl;
    /**
     * 接收人信息
     */
    private List<OrbitSysUser> receivorList;
    /**
     * 设备列表
     */
    private List<OrbitResDevice> deviceList;

    public boolean isVoz() {
        return voz;
    }

    public void setVoz(boolean voz) {
        this.voz = voz;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public String getCreatorPicUrl() {
        return creatorPicUrl;
    }

    public void setCreatorPicUrl(String creatorPicUrl) {
        this.creatorPicUrl = creatorPicUrl;
    }

    public String getApproverPicUrl() {
        return approverPicUrl;
    }

    public void setApproverPicUrl(String approverPicUrl) {
        this.approverPicUrl = approverPicUrl;
    }

    public List<OrbitSysUser> getReceivorList() {
        return receivorList;
    }

    public void setReceivorList(List<OrbitSysUser> receivorList) {
        this.receivorList = receivorList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(Integer taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getReceivorId() {
        return receivorId;
    }

    public void setReceivorId(String receivorId) {
        this.receivorId = receivorId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public List<OrbitResDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<OrbitResDevice> deviceList) {
        this.deviceList = deviceList;
    }

    public String getCreatorTel() {
        return creatorTel;
    }

    public void setCreatorTel(String creatorTel) {
        this.creatorTel = creatorTel;
    }
}