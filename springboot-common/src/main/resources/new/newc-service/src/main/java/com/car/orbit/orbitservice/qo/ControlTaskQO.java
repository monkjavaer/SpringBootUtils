package com.car.orbit.orbitservice.qo;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.qo
 * @ClassName: ControlTaskQO
 * @Description: java类作用描述
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 15:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 15:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ControlTaskQO {

    /**
     * 开始时间，这里的时间是指布控任务创建的时间是否在这个时间段范围内
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 布控任务名称
     */
    private String taskName;
    /**
     * 申请者/创建者名称
     */
    private String createName;
    /**
     * 审批者名称
     */
    private String approverName;
    /**
     * 查询标识：1：只查询当前用户创建布控任务
     */
    private int searchType;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页显示的最大记录数
     */
    private Integer pageSize;

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }
}