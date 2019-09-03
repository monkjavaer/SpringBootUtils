package com.car.orbit.orbitservice.entity;

/**
 * @Title: OrbitPassVehicleRecord
 * @Package: com.car.orbit.orbitservice.entity
 * @Description: 套牌过车记录表
 * @Author: monkjavaer
 * @Date: 2019/03/15 18:20
 * @Version: V1.0
 */
public class OrbitPassDeckVehicleRecord extends OrbitPassVehicleRecord {

    /**
     * 异常状态,1表示套牌（相同车牌不同车型特征）、2表示套牌（相同车牌轨迹异常）
     */
    private String exception;
    /**
     * 处理状态,1表示未处理、2表示已排除、3表示已添加布控
     */
    private String handleStatus;
    /**
     * 上一辆车的过车ID
     */
    private String idLast;
    /**
     * 套牌车与上一车辆的距离，单位为km
     */
    private Float distance;
    /**
     * 上一辆车辆的抓拍时间
     */
    private String captureTimeLast;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getIdLast() {
        return idLast;
    }

    public void setIdLast(String idLast) {
        this.idLast = idLast;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getCaptureTimeLast() {
        return captureTimeLast;
    }

    public void setCaptureTimeLast(String captureTimeLast) {
        this.captureTimeLast = captureTimeLast;
    }
}
