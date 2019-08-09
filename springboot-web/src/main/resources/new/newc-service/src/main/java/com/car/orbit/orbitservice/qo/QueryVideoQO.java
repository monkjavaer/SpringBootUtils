package com.car.orbit.orbitservice.qo;

/**
 * @author monkjavaer
 * @date 2018/03/02.
 */
public class QueryVideoQO {
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 抓拍时间
     */
    private String captureTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }
}
