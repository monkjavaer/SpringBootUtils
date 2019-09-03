package com.car.orbit.orbitservice.vo;

/**
 * CreateDate：2019/5/8 <br/>
 * Author：monkjavaer <br/>
 * Description: 供轨迹分析使用的设备点位信息
 **/
public class TrajectoryDeviceInfoVO extends DeviceDetailVO{

    private String captureTime;

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    @Override
    public String toString() {
        return "TrajectoryDeviceInfoVO{" +
                "captureTime='" + captureTime + '\'' +
                "} " + super.toString();
    }
}