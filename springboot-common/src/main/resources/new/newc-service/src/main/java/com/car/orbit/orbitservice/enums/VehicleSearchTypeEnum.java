package com.car.orbit.orbitservice.enums;

/**
 * @Title: VehicleSearchTypeEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: 综合搜车筛选类型
 * @Author: monkjavaer
 * @Date: 2019/07/08 15:42
 * @Version: V1.0
 */
public enum VehicleSearchTypeEnum {

    /**
     * 全部
     */
    ALL(0, "全部"),
    /**
     * 有车牌
     */
    HAVE_PLATE_NUMBER(1, "有车牌"),
    /**
     * 无车牌
     */
    MISSING_PLATE_NUMBER(2, "无车牌");

    private int value;
    private String description;

    VehicleSearchTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
