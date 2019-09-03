package com.car.orbit.orbitservice.enums;

/**
 * @Title: StatusEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/04/01 10:44
 * @Version: V1.0
 */
public enum HandleStatusEnum {

    /**
     * 未处理
     */
    UNHANDLE(1, "未处理"),
    /**
     * 已排除
     */
    EXCLUDE(2, "已排除"),
    /**
     * 已添加布控
     */
    PUSH(3, "已添加布控");

    private int value;
    private String description;

    HandleStatusEnum(int value, String description) {
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
