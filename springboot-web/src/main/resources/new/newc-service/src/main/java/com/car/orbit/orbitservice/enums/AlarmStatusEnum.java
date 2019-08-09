package com.car.orbit.orbitservice.enums;

/**
 * @Title: AlarmStatusEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: 套牌车
 * @Author: monkjavaer
 * @Data: 2019/4/3 9:34
 * @Version: V1.0
 */
public enum AlarmStatusEnum {

    /**
     * 全部
     */
    ALL(0,"全部警情"),

    /**
     * 未处置(警情的初始状态)
     */
    NO_ACK(1,"未处置"),
    /**
     * 已确认
     */
    PUSH(2,"已推送"),
    /**
     * 错误报警
     */
    NOT_DEAL_WITH(3,"不予处理");

    private int value;
    private String description;

    AlarmStatusEnum(int value, String description) {
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
