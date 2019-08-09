package com.car.orbit.orbitservice.bo;

/**
 * @Title: FootholdDetail
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 落脚点详细（单次）
 * @Author: monkjavaer
 * @Date: 2019/03/29 14:43
 * @Version: V1.0
 */
public class FootholdDetail {

    /**
     * 过车记录id
     *
     * Redis分页方式存储list数据,如果两条数据完全一样会合并导致结果异常,这里加入recordId防止这种情况
     */
    private String recordId;
    /**
     * 落脚时长
     */
    private Double hours;
    /**
     * 抓拍时间
     */
    private String captureTime;
    /**
     * 图片url
     */
    private String photoUrl;
    /**
     * 白天占比
     */
    private String daytimePercentage;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDaytimePercentage() {
        return daytimePercentage;
    }

    public void setDaytimePercentage(String daytimePercentage) {
        this.daytimePercentage = daytimePercentage;
    }
}
