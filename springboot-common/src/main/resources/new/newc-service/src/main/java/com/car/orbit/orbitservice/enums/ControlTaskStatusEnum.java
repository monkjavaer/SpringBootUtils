package com.car.orbit.orbitservice.enums;

/**
 * @Title: ControlTaskStatusEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: 布控任务状态枚举类 1，布控中，2结束，3，审批中，4，未通过
 * @Author: zks
 * @Data: 2019/4/3 9:34
 * @Version: V1.0
 */
public enum ControlTaskStatusEnum {

    /**
     * 布控中
     */
    ON_CONTROL(1,"布控中"),
    /**
     * 布控结束
     */
    OVER(2,"布控结束"),
    /**
     * 审批中
     */
    PENDING(3,"审批中"),
    /**
     * 审批未通过
     */
    NOT_PASS(4,"审批未通过");
    private int value;
    private String description;

    ControlTaskStatusEnum(int value, String description) {
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
