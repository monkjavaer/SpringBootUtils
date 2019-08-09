package com.car.orbit.orbitservice.vo;

/**
 * @Title: DeviceSimpleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 设备返回简单对象
 * @Author: monkjavaer
 * @Data: 2019/3/8 13:12
 * @Version: V1.0
 */
public class DeviceTreeResponseVO {
    /**
     * 设备id
     */
    private String id;
    /**
     * 设备名
     */
    private String label;
    /**
     * 在线状态
     */
    private Integer online;
    /**
     * 经度
     */
    private Double latitude;
    /**
     * 纬度
     */
    private Double longitude;

    private String roadId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
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

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }
}
