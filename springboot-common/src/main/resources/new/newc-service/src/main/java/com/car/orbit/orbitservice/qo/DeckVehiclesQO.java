package com.car.orbit.orbitservice.qo;

import java.util.List;

/**
 * CreateDate: 2019-3-26 <br/>
 * Author: monkjavaer <br/>
 * Description: 套牌车辆查询QO
 * Version: 1.0
 **/
public class DeckVehiclesQO {

    /**
     * 套牌记录id
     */
    private String id;
    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车牌颜色
     */
    private String plateColor;

    /**
     * 设备id列表
     */
    private List<String> deviceIdList;

    /**
     * 是否在黑名单中,true在，false不在
     */
    private Boolean black;

    /**
     * 处理状态，1未处理，2已排除
     * */
    private String status;

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页显示的最大记录数
     */
    private Integer pageSize;

    /**
     * 查询key
     */
    private String searchKey;

    /**
     * 排除操作-车辆vid
     * */
    private String vid;

    /**
     * 排除操作-车辆套牌异常状态
     * */
    private String exception;

    /**
     * 辅助前端 flag=true时，若查询结果为空，默认返回第一页（pageNo=1）数据
     */
    private boolean flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Boolean getBlack() {
        return black;
    }

    public void setBlack(Boolean black) {
        this.black = black;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
