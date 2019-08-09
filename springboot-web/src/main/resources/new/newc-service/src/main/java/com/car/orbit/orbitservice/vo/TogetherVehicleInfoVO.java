package com.car.orbit.orbitservice.vo;

/**
 * @Title: TogetherVehicleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 同行车详细信息
 * @Author: monkjavaer
 * @Data: 2019/3/27 16:15
 * @Version: V1.0
 */
public class TogetherVehicleInfoVO {

    /**
     * 同行车牌
     */
    private String togetherPlateNumber;
    /**
     * 同行VID
     */
    private String togetherVid;
    /**
     * 同行车型
     */
    private String togetherVehicleType;
    /**
     * 同行车抓拍时间
     */
    private String togetherCaptureTime;
    /**
     * 同行车过车地址
     */
    private String togetherAdress;
    private String togetherPhotourl;



    /**
     * 原始车牌
     */
    private String originalPlateNumber;

    /**
     * 原始车vid
     */
    private String originalVid;

    private String originalVehicleType;

    private String originalCaptureTime;

    private String originalAdress;
    private String originalPhotourl;


    public String getTogetherPlateNumber() {
        return togetherPlateNumber;
    }

    public void setTogetherPlateNumber(String togetherPlateNumber) {
        this.togetherPlateNumber = togetherPlateNumber;
    }

    public String getTogetherVid() {
        return togetherVid;
    }

    public void setTogetherVid(String togetherVid) {
        this.togetherVid = togetherVid;
    }

    public String getTogetherVehicleType() {
        return togetherVehicleType;
    }

    public void setTogetherVehicleType(String togetherVehicleType) {
        this.togetherVehicleType = togetherVehicleType;
    }

    public String getTogetherCaptureTime() {
        return togetherCaptureTime;
    }

    public void setTogetherCaptureTime(String togetherCaptureTime) {
        this.togetherCaptureTime = togetherCaptureTime;
    }

    public String getTogetherAdress() {
        return togetherAdress;
    }

    public void setTogetherAdress(String togetherAdress) {
        this.togetherAdress = togetherAdress;
    }

    public String getOriginalPlateNumber() {
        return originalPlateNumber;
    }

    public void setOriginalPlateNumber(String originalPlateNumber) {
        this.originalPlateNumber = originalPlateNumber;
    }

    public String getOriginalVid() {
        return originalVid;
    }

    public void setOriginalVid(String originalVid) {
        this.originalVid = originalVid;
    }

    public String getOriginalVehicleType() {
        return originalVehicleType;
    }

    public void setOriginalVehicleType(String originalVehicleType) {
        this.originalVehicleType = originalVehicleType;
    }

    public String getOriginalCaptureTime() {
        return originalCaptureTime;
    }

    public void setOriginalCaptureTime(String originalCaptureTime) {
        this.originalCaptureTime = originalCaptureTime;
    }

    public String getOriginalAdress() {
        return originalAdress;
    }

    public void setOriginalAdress(String originalAdress) {
        this.originalAdress = originalAdress;
    }

    public String getTogetherPhotourl() {
        return togetherPhotourl;
    }

    public void setTogetherPhotourl(String togetherPhotourl) {
        this.togetherPhotourl = togetherPhotourl;
    }

    public String getOriginalPhotourl() {
        return originalPhotourl;
    }

    public void setOriginalPhotourl(String originalPhotourl) {
        this.originalPhotourl = originalPhotourl;
    }
}
