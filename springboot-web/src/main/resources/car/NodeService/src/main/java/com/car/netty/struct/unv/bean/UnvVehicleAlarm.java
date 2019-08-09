package com.car.netty.struct.unv.bean;


public class UnvVehicleAlarm
{
    /**
     * 唯一ID
     */
    private long alarmId = 0L;

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
     * 违法数据 json
     */
    private String alarmData;

    /**
     * 违法类型
     */
    private String driveStatus;

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

    public String getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(String alarmData) {
        this.alarmData = alarmData;
    }

    public String getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(String driveStatus) {
        this.driveStatus = driveStatus;
    }

    public int getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(int transFlag) {
        this.transFlag = transFlag;
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

    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }
}
