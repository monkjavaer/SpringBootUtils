package com.car.base.message;

import com.car.base.message.vo.PhotoInfoVo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VehicleStructureMsg extends BaseMsg implements Serializable {

    private static final long serialVersionUID = -6897121151568746895L;
    private BigInteger id;
    private Timestamp captureTime;
    private String plateNumber;
    private String vehicleColor;
    private Integer plateColor;
    private Integer vehicleBrand;
    private String vehicleBody;
    private String vehicleType;
    private String rearVehiclePlateID;
    private Integer reaPlateConfidence;
    private String rearCharConfidence;
    private String rearPlateColor;
    private String rearPlateType;
    private Integer speed;
    private Integer limitedSpeed;
    private Integer markSpeed;
    private Integer roadwayNo;
    private String roadwayName;
    /** 卡口编码 */
    private String bayonetNo;
    private BigInteger deviceId;
    private String deviceName;
    private Integer directionCode;
    private Integer driveStatus;
    private BigInteger roadCrossPointId;
    private String camID;
    private String panoramaFlag;
    private String recordID;
    private String laneDirection;
    private String laneDescription;
    private String plateType;
    private String plateCoincide;
    private String vehicleLength;
    private List<PhotoInfoVo> phtotUrlList;
    private List<String> photoBase64List;
    private BigInteger cityId;
    private BigInteger areaId;

    /**
     * 过车图片
     */
    private ArrayList<byte[]> photoDataList;

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public BigInteger getAreaId() {
        return areaId;
    }

    public void setAreaId(BigInteger areaId) {
        this.areaId = areaId;
    }

    public ArrayList<byte[]> getPhotoDataList() {
        return photoDataList;
    }

    public void setPhotoDataList(ArrayList<byte[]> photoDataList) {
        this.photoDataList = photoDataList;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRearVehiclePlateID() {
        return rearVehiclePlateID;
    }

    public void setRearVehiclePlateID(String rearVehiclePlateID) {
        this.rearVehiclePlateID = rearVehiclePlateID;
    }

    public Integer getReaPlateConfidence() {
        return reaPlateConfidence;
    }

    public void setReaPlateConfidence(Integer reaPlateConfidence) {
        this.reaPlateConfidence = reaPlateConfidence;
    }

    public String getRearCharConfidence() {
        return rearCharConfidence;
    }

    public void setRearCharConfidence(String rearCharConfidence) {
        this.rearCharConfidence = rearCharConfidence;
    }

    public String getRearPlateColor() {
        return rearPlateColor;
    }

    public void setRearPlateColor(String rearPlateColor) {
        this.rearPlateColor = rearPlateColor;
    }

    public String getRearPlateType() {
        return rearPlateType;
    }

    public void setRearPlateType(String rearPlateType) {
        this.rearPlateType = rearPlateType;
    }

    public Integer getLimitedSpeed() {
        return limitedSpeed;
    }

    public void setLimitedSpeed(Integer limitedSpeed) {
        this.limitedSpeed = limitedSpeed;
    }

    public Integer getMarkSpeed() {
        return markSpeed;
    }

    public void setMarkSpeed(Integer markSpeed) {
        this.markSpeed = markSpeed;
    }

    public String getCamID() {
        return camID;
    }

    public void setCamID(String camID) {
        this.camID = camID;
    }

    public String getPanoramaFlag() {
        return panoramaFlag;
    }

    public void setPanoramaFlag(String panoramaFlag) {
        this.panoramaFlag = panoramaFlag;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getLaneDirection() {
        return laneDirection;
    }

    public void setLaneDirection(String laneDirection) {
        this.laneDirection = laneDirection;
    }

    public String getLaneDescription() {
        return laneDescription;
    }

    public void setLaneDescription(String laneDescription) {
        this.laneDescription = laneDescription;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getPlateCoincide() {
        return plateCoincide;
    }

    public void setPlateCoincide(String plateCoincide) {
        this.plateCoincide = plateCoincide;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public List<PhotoInfoVo> getPhtotUrlList() {
        return phtotUrlList;
    }

    public void setPhtotUrlList(List<PhotoInfoVo> phtotUrlList) {
        this.phtotUrlList = phtotUrlList;
    }

    public Timestamp getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Timestamp captureTime) {
        this.captureTime = captureTime;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(Integer vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(Integer directionCode) {
        this.directionCode = directionCode;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public BigInteger getRoadCrossPointId() {
        return roadCrossPointId;
    }

    public void setRoadCrossPointId(BigInteger roadCrossPointId) {
        this.roadCrossPointId = roadCrossPointId;
    }

    public Integer getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(Integer driveStatus) {
        this.driveStatus = driveStatus;
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

    public String getBayonetNo() {
        return bayonetNo;
    }

    public void setBayonetNo(String bayonetNo) {
        this.bayonetNo = bayonetNo;
    }

    public List<String> getPhotoBase64List() {
        return photoBase64List;
    }

    public void setPhotoBase64List(List<String> photoBase64List) {
        this.photoBase64List = photoBase64List;
    }

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "VehicleStructureMsg{" +
                "id=" + id +
                ", captureTime=" + captureTime +
                ", plateNumber='" + plateNumber + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", plateColor=" + plateColor +
                ", vehicleBrand=" + vehicleBrand +
                ", vehicleBody='" + vehicleBody + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", rearVehiclePlateID='" + rearVehiclePlateID + '\'' +
                ", reaPlateConfidence=" + reaPlateConfidence +
                ", rearCharConfidence='" + rearCharConfidence + '\'' +
                ", rearPlateColor='" + rearPlateColor + '\'' +
                ", rearPlateType='" + rearPlateType + '\'' +
                ", speed=" + speed +
                ", limitedSpeed=" + limitedSpeed +
                ", markSpeed=" + markSpeed +
                ", roadwayNo=" + roadwayNo +
                ", roadwayName='" + roadwayName + '\'' +
                ", bayonetNo='" + bayonetNo + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", directionCode=" + directionCode +
                ", driveStatus=" + driveStatus +
                ", roadCrossPointId=" + roadCrossPointId +
                ", camID='" + camID + '\'' +
                ", panoramaFlag='" + panoramaFlag + '\'' +
                ", recordID='" + recordID + '\'' +
                ", laneDirection='" + laneDirection + '\'' +
                ", laneDescription='" + laneDescription + '\'' +
                ", plateType='" + plateType + '\'' +
                ", plateCoincide='" + plateCoincide + '\'' +
                ", vehicleLength='" + vehicleLength + '\'' +
                ", phtotUrlList=" + phtotUrlList +
                ", photoBase64List=" + photoBase64List +
                ", cityId=" + cityId +
                ", areaId=" + areaId +
                ", photoDataList=" + photoDataList +
                '}';
    }
}
