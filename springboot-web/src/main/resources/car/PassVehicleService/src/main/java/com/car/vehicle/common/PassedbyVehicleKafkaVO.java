//package com.car.vehicle.common;
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.car.base.common.utilities.DateTimeUtils;
//
//import java.math.BigInteger;
//import java.text.ParseException;
//import java.util.Date;
//
///**
// * 发送到kafka的字段格式定义类
// *
// * @author monkjavaer
// * @date 2019/3/26 11:14
// */
//
//public class PassedbyVehicleKafkaVO {
//    @JSONField(name = "id")
//    private BigInteger id;
//    @JSONField(name = "capture_time")
//    private Date captureTime;
//    @JSONField(name = "plate_number")
//    private String plateNumber;
//    @JSONField(name = "vehicle_color")
//    private String vehicleColor;
//    @JSONField(name = "plate_color")
//    private Integer plateColor;
//    @JSONField(name = "vehicle_brand")
//    private Integer vehicleBrand;
//    @JSONField(name = "vehicle_type")
//    private String vehicleType;
//    @JSONField(name = "speed")
//    private Integer speed;
//    @JSONField(name = "roadway_no")
//    private Integer roadwayNo;
//    @JSONField(name = "roadway_name")
//    private String roadwayName;
//    @JSONField(name = "device_id")
//    private BigInteger deviceId;
//    @JSONField(name = "direction_code")
//    private Integer directionCode;
//    @JSONField(name = "plate_photo_id")
//    private BigInteger platePhotoId;
//    @JSONField(name = "photo_fastdfs_url")
//    private String photoFastDFSUrl;
//    @JSONField(name = "drive_status")
//    private Integer driveStatus;
//    @JSONField(name = "city_name")
//    private String cityName;
//    @JSONField(name = "area_name")
//    private String areaName;
//    @JSONField(name = "road_name")
//    private String roadName;
//    @JSONField(name = "city_id")
//    private BigInteger cityId;
//    @JSONField(name = "area_id")
//    private BigInteger areaId;
//    @JSONField(name = "road_cross_point_id")
//    private BigInteger roadCrossPointId;
//    @JSONField(name = "device_name")
//    private String deviceName;
//
//    public PassedbyVehicleKafkaVO(PassedbyVehicleEntity passedbyVehicleEntity){
//        this.id = passedbyVehicleEntity.getId();
//        try {
//            String timestr = DateTimeUtils.getTimeByTimestampDefault(passedbyVehicleEntity.getCaptureTime());
//            Date time = DateTimeUtils.formatDate(timestr,null);
//            this.captureTime = time;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        this.plateNumber = passedbyVehicleEntity.getPlateNumber();
//        this.vehicleColor = passedbyVehicleEntity.getVehicleColor();
//        this.plateColor = passedbyVehicleEntity.getPlateColor();
//        this.vehicleBrand = passedbyVehicleEntity.getVehicleBrand();
//        this.vehicleType = passedbyVehicleEntity.getVehicleType();
//        this.speed = passedbyVehicleEntity.getSpeed();
//        this.roadwayNo = passedbyVehicleEntity.getRoadwayNo();
//        this.roadwayName = passedbyVehicleEntity.getRoadwayName();
//        this.deviceId = passedbyVehicleEntity.getDeviceId();
//        this.directionCode = passedbyVehicleEntity.getDirectionCode();
//        this.platePhotoId = passedbyVehicleEntity.getPlatePhotoId();
//        this.photoFastDFSUrl = passedbyVehicleEntity.getPhotoFastDFSUrl();
//        this.driveStatus = passedbyVehicleEntity.getDriveStatus();
//        this.cityName = passedbyVehicleEntity.getCityName();
//        this.areaName = passedbyVehicleEntity.getAreaName();
//        this.roadName = passedbyVehicleEntity.getRoadName();
//        this.cityId = passedbyVehicleEntity.getCityId();
//        this.areaId = passedbyVehicleEntity.getAreaId();
//        this.roadCrossPointId = passedbyVehicleEntity.getRoadCrossPointId();
//        this.deviceName = passedbyVehicleEntity.getDeviceName();
//
//    }
//
//    public BigInteger getId() {
//        return id;
//    }
//
//    public void setId(BigInteger id) {
//        this.id = id;
//    }
//
//    public Date getCaptureTime() {
//        return captureTime;
//    }
//
//    public void setCaptureTime(Date captureTime) {
//        this.captureTime = captureTime;
//    }
//
//    public String getPlateNumber() {
//        return plateNumber;
//    }
//
//    public void setPlateNumber(String plateNumber) {
//        this.plateNumber = plateNumber;
//    }
//
//    public String getVehicleColor() {
//        return vehicleColor;
//    }
//
//    public void setVehicleColor(String vehicleColor) {
//        this.vehicleColor = vehicleColor;
//    }
//
//    public Integer getPlateColor() {
//        return plateColor;
//    }
//
//    public void setPlateColor(Integer plateColor) {
//        this.plateColor = plateColor;
//    }
//
//    public Integer getVehicleBrand() {
//        return vehicleBrand;
//    }
//
//    public void setVehicleBrand(Integer vehicleBrand) {
//        this.vehicleBrand = vehicleBrand;
//    }
//
//    public String getVehicleType() {
//        return vehicleType;
//    }
//
//    public void setVehicleType(String vehicleType) {
//        this.vehicleType = vehicleType;
//    }
//
//    public Integer getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(Integer speed) {
//        this.speed = speed;
//    }
//
//    public Integer getRoadwayNo() {
//        return roadwayNo;
//    }
//
//    public void setRoadwayNo(Integer roadwayNo) {
//        this.roadwayNo = roadwayNo;
//    }
//
//    public String getRoadwayName() {
//        return roadwayName;
//    }
//
//    public void setRoadwayName(String roadwayName) {
//        this.roadwayName = roadwayName;
//    }
//
//    public BigInteger getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(BigInteger deviceId) {
//        this.deviceId = deviceId;
//    }
//
//    public Integer getDirectionCode() {
//        return directionCode;
//    }
//
//    public void setDirectionCode(Integer directionCode) {
//        this.directionCode = directionCode;
//    }
//
//    public BigInteger getPlatePhotoId() {
//        return platePhotoId;
//    }
//
//    public void setPlatePhotoId(BigInteger platePhotoId) {
//        this.platePhotoId = platePhotoId;
//    }
//
//    public String getPhotoFastDFSUrl() {
//        return photoFastDFSUrl;
//    }
//
//    public void setPhotoFastDFSUrl(String photoFastDFSUrl) {
//        this.photoFastDFSUrl = photoFastDFSUrl;
//    }
//
//    public Integer getDriveStatus() {
//        return driveStatus;
//    }
//
//    public void setDriveStatus(Integer driveStatus) {
//        this.driveStatus = driveStatus;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getAreaName() {
//        return areaName;
//    }
//
//    public void setAreaName(String areaName) {
//        this.areaName = areaName;
//    }
//
//    public String getRoadName() {
//        return roadName;
//    }
//
//    public void setRoadName(String roadName) {
//        this.roadName = roadName;
//    }
//
//    public BigInteger getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(BigInteger cityId) {
//        this.cityId = cityId;
//    }
//
//    public BigInteger getAreaId() {
//        return areaId;
//    }
//
//    public void setAreaId(BigInteger areaId) {
//        this.areaId = areaId;
//    }
//
//    public BigInteger getRoadCrossPointId() {
//        return roadCrossPointId;
//    }
//
//    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
//        this.roadCrossPointId = roadCrossPointId;
//    }
//
//    public String getDeviceName() {
//        return deviceName;
//    }
//
//    public void setDeviceName(String deviceName) {
//        this.deviceName = deviceName;
//    }
//}
