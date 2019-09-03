package com.car.orbit.orbitservice.enums;

/**
 * @Title: DeviceTypeEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: 设备制造商字典
 * @Author: monkjavaer
 * @Data: 2019/3/8 11:13
 * @Version: V1.0
 */
public enum DeviceManufacturerEnum {

    HKVISION(1,"HKVISION"),
    LD(2,"LD"),
    MS(3,"MS"),
    TIANDY(4,"TIANDY");

    DeviceManufacturerEnum(int value, String deviceDescription) {
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
