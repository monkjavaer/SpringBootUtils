package com.car.orbit.orbitservice.test;

public class BlacklistAlarm {

    private String alarmId;
    //布控ID，黑名单就没有
    private String taskId;
    private String blacklistId;

    //过车记录ID
    private String passVehicleId;

    //车牌号码
    private String plateNumber;

    //报警时间
    private String alarmTime;

    //图片URL
    private String photoFastdfsUrl;

    //设备ID
    private String deviceId;

    //道路号码
    private String roadwayNo;

    //道路名称
    private String roadwayName;

    //抓拍时间
    private String captureTime;

    //城市id
    private String cityId;

    //区域id
    private String areaId;

    //路口id
    private String roadCrossPointId;

    private String vehicleType;

    private String vehicleColor;
    private String vehicleBrand;
    private String vehicleBrandChild;

    public BlacklistAlarm(){}

    public BlacklistAlarm(String alarmId, String taskId, String blacklistId, String passVehicleId, String plateNumber, String alarmTime, String photoFastdfsUrl, String deviceId, String roadwayNo, String roadwayName, String captureTime, String cityId, String areaId, String roadCrossPointId, String vehicleType, String vehicleColor, String vehicleBrand, String vehicleBrandChild) {
        this.alarmId = alarmId;
        this.taskId = taskId;
        this.blacklistId = blacklistId;
        this.passVehicleId = passVehicleId;
        this.plateNumber = plateNumber;
        this.alarmTime = alarmTime;
        this.photoFastdfsUrl = photoFastdfsUrl;
        this.deviceId = deviceId;
        this.roadwayNo = roadwayNo;
        this.roadwayName = roadwayName;
        this.captureTime = captureTime;
        this.cityId = cityId;
        this.areaId = areaId;
        this.roadCrossPointId = roadCrossPointId;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.vehicleBrand = vehicleBrand;
        this.vehicleBrandChild = vehicleBrandChild;
    }

    public String getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(String blacklistId) {
        this.blacklistId = blacklistId;
    }

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

    public String getRoadwayNo() {
        return roadwayNo;
    }

    public void setRoadwayNo(String roadwayNo) {
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
