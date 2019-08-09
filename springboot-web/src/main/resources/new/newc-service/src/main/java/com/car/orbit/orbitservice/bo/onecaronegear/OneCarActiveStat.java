package com.car.orbit.orbitservice.bo.onecaronegear;

public class OneCarActiveStat {
    private String active;

    private String activeName;
    //活跃地方的经度
    private Double lat;
    //活跃地方的纬度
    private Double lon;
    //活跃地方的次数
    private Long times;
    public OneCarActiveStat(){}

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }
}
