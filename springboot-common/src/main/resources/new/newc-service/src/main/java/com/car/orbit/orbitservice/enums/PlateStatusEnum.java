package com.car.orbit.orbitservice.enums;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
public enum PlateStatusEnum {
    /** 黑名单 **/
    BLACK(1, "黑名单"),
    /** 违规车 **/
    VIOLATIONS(2, "违规车"),
    /** 套牌 **/
    FACK(3, "套牌");

    private int value;
    private String description;

    PlateStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
