package com.car.orbit.orbitservice.vo;

/**
 * @Title: OneVehicleFileVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 一车一档
 * @Author: monkjavaer
 * @Date: 2019/03/18 19:24
 * @Version: V1.0
 */
public class OneVehicleFileVO {

    /**
     * VID【由车牌号和车辆颜色唯一确定】
     */
    private String vid;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 记录条数
     */
    private Long count;
    /**
     * 图片url
     */
    private String photoUrl;
    /**
     * 图片数据转换，用于导出
     */
    private byte[] picture;
    /**
     * 一车一档后最高相似度
     */
    private Float maxRet;

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public OneVehicleFileVO() {
    }

    public OneVehicleFileVO(String vid, String plateNumber, Long count, String photoUrl,Float maxRet) {
        this.vid = vid;
        this.plateNumber = plateNumber;
        this.count = count;
        this.photoUrl = photoUrl;
        this.maxRet = maxRet;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Float getMaxRet() {
        return maxRet;
    }

    public void setMaxRet(Float maxRet) {
        this.maxRet = maxRet;
    }
}
