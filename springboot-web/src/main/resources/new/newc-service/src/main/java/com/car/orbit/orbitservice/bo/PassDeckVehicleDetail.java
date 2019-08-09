package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;

import java.util.List;

/**
 * @Title: PassDeckVehicleDetail
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 套牌车详细
 * @Author: monkjavaer
 * @Date: 2019/07/09 10:43
 * @Version: V1.0
 */
public class PassDeckVehicleDetail {

    /**
     * 异常状态 1表示套牌（相同车牌不同车型特征）、2表示套牌（相同车牌轨迹异常）
     */
    private String exception;
    /**
     * 直线距离
     */
    private Float distance;
    /**
     * 抓拍时间差，单位秒
     */
    private Long captureTmDiffer;
    /**
     * 预计速度 单位：km/h
     */
    private Float estimatedSpeed;
    /**
     * 处理状态 1未处理，2已排除，3已确认
     */
    private String handleStatus;
    /**
     * 两辆车的过车信息
     */
    private OrbitPassVehicleRecord[] vehicle = new OrbitPassVehicleRecord[2];
    /**
     * 轨迹信息集合
     */
    private List<VehicleTrajectoryVO> trajectoryList;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Long getCaptureTmDiffer() {
        return captureTmDiffer;
    }

    public void setCaptureTmDiffer(Long captureTmDiffer) {
        this.captureTmDiffer = captureTmDiffer;
    }

    public Float getEstimatedSpeed() {
        return estimatedSpeed;
    }

    public void setEstimatedSpeed(Float estimatedSpeed) {
        this.estimatedSpeed = estimatedSpeed;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public OrbitPassVehicleRecord[] getVehicle() {
        return vehicle;
    }

    public void setVehicle(OrbitPassVehicleRecord[] vehicle) {
        this.vehicle = vehicle;
    }

    public List<VehicleTrajectoryVO> getTrajectoryList() {
        return trajectoryList;
    }

    public void setTrajectoryList(List<VehicleTrajectoryVO> trajectoryList) {
        this.trajectoryList = trajectoryList;
    }
}
