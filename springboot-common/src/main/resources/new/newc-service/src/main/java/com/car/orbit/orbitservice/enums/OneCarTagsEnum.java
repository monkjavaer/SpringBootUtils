package com.car.orbit.orbitservice.enums;

public enum OneCarTagsEnum {

    /** 布控车辆 **/
    MONITOR_VEHICLE(1,"monitor vehicle"),
    /** 黑名单车辆 **/
    BLACKLISTED_VEHICLE(2,"blacklisted vehicle"),
    /** 白名单车辆 **/
    WHITE_LIST_VEHICLE(3,"white list vehicle"),
    /** 套牌车辆 **/
    DECK_VEHICLE(4,"deck vehicle");

    OneCarTagsEnum(int value, String deviceDescription) {
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
