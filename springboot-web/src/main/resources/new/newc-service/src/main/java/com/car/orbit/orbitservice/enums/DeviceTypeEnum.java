package com.car.orbit.orbitservice.enums;

/**
 * @Title: DeviceTypeEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: 设备类型字典
 * @Author: monkjavaer
 * @Data: 2019/3/8 11:13
 * @Version: V1.0
 */
public enum DeviceTypeEnum {

    /** 卡口相机 **/
    IPC(1,"IPC"),
    /** 普通摄像机 **/
    COMMON_CAMERA(2,"Common Camera");

    DeviceTypeEnum(int value, String deviceDescription) {
        this.value = value;
        this.deviceDescription = deviceDescription;
    }

    private int value;
    private String deviceDescription;

    public int getValue() {
        return value;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }
}
