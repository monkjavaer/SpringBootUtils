package com.base.springboot.car.NodeService.src.main.java.com.car.netty.struct.unv.bean;


public class UnvVehicle
{
    /**
     * 唯一ID
     */
    private long recordId = 0L;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 车牌号码
     */
    private String plateCode;

    /**
     * 过车时间
     */
    private String passTime;

    /**
     * 过车数据 json
     */
    private String recordData;

    /**
     * 转发标志位
     */
    private int transFlag ;

    /**
     * 更新时间
     */
    private String updateTime;


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getRecordData() {
        return recordData;
    }

    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }

    public int getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(int transFlag) {
        this.transFlag = transFlag;
    }
}
