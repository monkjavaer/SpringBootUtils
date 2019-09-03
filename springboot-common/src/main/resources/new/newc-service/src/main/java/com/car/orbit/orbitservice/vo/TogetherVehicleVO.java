package com.car.orbit.orbitservice.vo;

/**
 * @Title: TogetherVehicleVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2019/3/27 16:15
 * @Version: V1.0
 */
public class TogetherVehicleVO extends TacticsVehicleBaseInfo{

    /**
     * 同行次数
     */
    private Long togetherCount;

    /**
     * 抓拍时间
     */
    private String captureTime;



    public Long getTogetherCount() {
        return togetherCount;
    }

    public void setTogetherCount(Long togetherCount) {
        this.togetherCount = togetherCount;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }
}
