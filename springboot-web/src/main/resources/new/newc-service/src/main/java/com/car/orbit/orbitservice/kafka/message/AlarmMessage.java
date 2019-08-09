package com.car.orbit.orbitservice.kafka.message;

/**
 * @Title: AlarmMessage
 * @Package: com.car.orbit.orbitservice.kafka
 * @Description: AlarmMessage
 * @Author: monkjavaer
 * @Data: 2019/4/2 19:50
 * @Version: V1.0
 */
public class AlarmMessage {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 黑名单id
     */
    private String blacklistId;
    /**
     * 过车记录id
     */
    private String passVehicleId;
    /**
     * 车牌号码
     */
    private String plateNumber;
    /**
     * 预警时间
     */
    private String alarmTime;
    /**
     * 图片fastdfs地址
     */
    private String photoFastdfsUrl;
    /**
     * 图片fastdfs地址
     */
    private String deviceId;
    /**
     * 车道号
     */
    private Integer roadwayNo;
    /**
     * 车道
     */
    private String roadwayName;
    /**
     * captureTime
     */
    private String captureTime;
    private String cityId;
    private String areaId;
    private String roadCrossPointId;
    /**
     * 车型
     */
    private String vehicleType;
    /**
     * 车身颜色
     */
    private String vehicleColor;
    /**
     * 品牌
     */
    private String vehicleBrand;
    private String vehicleBrandChild;
    /**
     * 警情主键
     */
    private String alarmId;


    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleBrandChild() {
        return vehicleBrandChild;
    }

    public void setVehicleBrandChild(String vehicleBrandChild) {
        this.vehicleBrandChild = vehicleBrandChild;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(String blacklistId) {
        this.blacklistId = blacklistId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPassVehicleId() {
        return passVehicleId;
    }

    public void setPassVehicleId(String passVehicleId) {
        this.passVehicleId = passVehicleId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getPhotoFastdfsUrl() {
        return photoFastdfsUrl;
    }

    public void setPhotoFastdfsUrl(String photoFastdfsUrl) {
        this.photoFastdfsUrl = photoFastdfsUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRoadwayNo() {
        return roadwayNo;
    }

    public void setRoadwayNo(Integer roadwayNo) {
        this.roadwayNo = roadwayNo;
    }

    public String getRoadwayName() {
        return roadwayName;
    }

    public void setRoadwayName(String roadwayName) {
        this.roadwayName = roadwayName;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }
}
