package com.m.gis.springboot.enums;

/**
 * @Title: CostTypeEnums
 * @Package: com.m.gis.springboot.enums
 * @Description: 服务成本类型
 * @Author: monkjavaer
 * @Data: 2018/12/18 11:15
 * @Version: V1.0
 */
public enum CostTypeEnums {
    /**
     * 距离，单位米
     */
    DISTANCE(0,"距离"),
    /**
     * 时间，单位分钟
     */
    TIME(1,"时间");

    private Integer value;
    private String name;

    CostTypeEnums(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
