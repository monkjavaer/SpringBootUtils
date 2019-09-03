package com.car.orbit.orbitservice.enums;

/**
 * @Title: StatusEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/04/01 10:44
 * @Version: V1.0
 */
public enum ResponseStatusEnum {

    /**
     * vid和exception不能为空
     */
    PARAMS_ERROR(1002, "vid和exception不能为空");

    private int value;
    private String description;

    ResponseStatusEnum(int value, String description) {
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
