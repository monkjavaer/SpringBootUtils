package com.car.orbit.orbitservice.bo;

/**
 * @Title: FootholdRankBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/04/04 11:49
 * @Version: V1.0
 */
public class FootholdRankBO  {

    /**
     * 设备id
     */
    private String id;
    /**
     * 落脚总时长
     */
    private Double totalHour;
    /**
     * 白天落脚总时长
     */
    private Double daytimeHour;
    /**
     * 夜晚落脚总时长
     */
    private Double nightHour;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;
    /**
     * text
     */
    private String content;

    public FootholdRankBO() {
        totalHour = 0.0;
        daytimeHour = 0.0;
        nightHour = 0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(Double totalHour) {
        this.totalHour = totalHour;
    }

    public Double getDaytimeHour() {
        return daytimeHour;
    }

    public void setDaytimeHour(Double daytimeHour) {
        this.daytimeHour = daytimeHour;
    }

    public Double getNightHour() {
        return nightHour;
    }

    public void setNightHour(Double nightHour) {
        this.nightHour = nightHour;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
