package com.car.trunk.dal.device.vo;

import java.math.BigInteger;

public class DeviceUserVO {
    private BigInteger id;
    private String name;
    private Byte online;
    private String url;
    private Double latitude;
    private Double longitude;
    private boolean opFlag;

    public DeviceUserVO(BigInteger id, String name, Byte online, String url, Double latitude, Double longitude, boolean opFlag) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.opFlag = opFlag;
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }
}
