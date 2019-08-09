package com.car.orbit.orbitservice.vo;

/**
 * @Title: VehiclePositionVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 车辆位置识别信息，一个方框勾画出图片中识别出来的位置信息
 * @Author: zks
 * @Date: 2019/03/19 19:00
 * @Version: V1.0
 */
public class VehiclePositionVO {

    /**
     * 车辆框选出来的图片Url
     */
    private String picUrl;
    /**
     * 车辆位置左坐标百分比
     */
    private double left;
    /**
     * 车辆位置上坐标百分比
     */
    private double top;
    /**
     * 区域图片有坐标百分比
     */
    private double right;
    /**
     * 区域图片下坐标百分比
     */
    private double bottom;
    /**
     * 结构化返回的详细信息
     */
    private StructuralVehicleInfoVO vehicleInfo;
    /**
     * 图片特征值
     */
    private float[] featureList;

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public StructuralVehicleInfoVO getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(StructuralVehicleInfoVO vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public float[] getFeatureList() {
        return featureList;
    }

    public void setFeatureList(float[] featureList) {
        this.featureList = featureList;
    }
}
