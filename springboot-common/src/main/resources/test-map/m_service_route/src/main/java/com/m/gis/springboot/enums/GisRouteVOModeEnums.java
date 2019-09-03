package com.m.gis.springboot.enums;

/**
 * @Title: GisAddressServiceExceptionEnums
 * @Package: com.m.gis.springboot.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public enum GisRouteVOModeEnums {
    /**
     * 几何模式，仅返回路径的几何形状
     */
    GEOMETRY(0),
    /**
     * 指南模式，返回路径的指南信息
     */
    INSTRUCTION(1);

    private Integer value;

    GisRouteVOModeEnums(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean equals(GisRouteVOModeEnums other){
        return other.getValue()==value;
    }

    public Boolean equals(Integer value){
        return getValue()==value;
    }
}
