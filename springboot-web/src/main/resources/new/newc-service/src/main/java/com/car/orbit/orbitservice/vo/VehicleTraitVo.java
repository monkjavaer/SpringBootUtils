package com.car.orbit.orbitservice.vo;

public class VehicleTraitVo {
    private String ID;

    private String vid;

    private String plateNumber;

    private String plateColor;

    private String vehicleType;

    private String vehicleColor;
    /**
     * clickHouse不保存
     */
    private String vehicleTypeName;

    private String roadCrossPointId;
    /**
     * clickHouse不保存
     */
    private String roadName;

    private String deviceId;
    /**
     * clickHouse不保存
     */
    private String deviceName;

    private String vehicleBrand;
    /**
     * clickHouse不保存
     */
    private String vehicleBrandName;

    private String vehicleBrandChild;
    /**
     * clickHouse不保存
     */
    private String vehicleBrandChildName;

    private String photoUrl;

    // @JsonFormat
    // private Date CAPTURE_TIME;

    private String captureTime;

    private String featureA;

    private Float sumNumerator;

    private Float modFeatureA;

    private Float ret ;

    /**
     * 图片数据转换，用于导出
     */
    private byte[] picture;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    // public Date getCAPTURE_TIME() {
    //     return CAPTURE_TIME;
    // }
    //
    // public void setCaptureTime(Date CAPTURE_TIME) {
    //     this.CAPTURE_TIME = captureTime;
    // }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getFeatureA() {
        return featureA;
    }

    public void setFeatureA(String featureA) {
        this.featureA = featureA;
    }

    public Float getSumNumerator() {
        return sumNumerator;
    }

    public void setSumNumerator(Float sumNumerator) {
        this.sumNumerator = sumNumerator;
    }

    public Float getModFeatureA() {
        return modFeatureA;
    }

    public void setModFeatureA(Float modFeatureA) {
        this.modFeatureA = modFeatureA;
    }

    public Float getRet() {
        return ret;
    }

    public void setRet(Float ret) {
        this.ret = ret;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(String roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getVehicleBrandChildName() {
        return vehicleBrandChildName;
    }

    public void setVehicleBrandChildName(String vehicleBrandChildName) {
        this.vehicleBrandChildName = vehicleBrandChildName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }
}
