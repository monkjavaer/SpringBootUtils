package com.car.orbit.orbitservice.qo;

import java.util.List;

public class RequestPrameterQo {
    private float[] img;

    private String begintime;

    private String endtime;

    private Double ret;

    private List<String>  deviceId;

    private String brand;

    private String childBrand;

    public Double getRet() {
        return ret;
    }

    public void setRet(Double ret) {
        this.ret = ret;
    }

    public float[] getImg() {
        return img;
    }

    public void setImg(float[] img) {
        this.img = img;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    @Override
    public String toString() {
        return "RequestPrameterQo{" +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", ret=" + ret +
                ", deviceId=" + deviceId +
                ", brand='" + brand + '\'' +
                ", childBrand='" + childBrand + '\'' +
                '}';
    }
}
