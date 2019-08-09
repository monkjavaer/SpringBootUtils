package com.car.orbit.orbitservice.enums;


/**
 * @Title: DeviceTypeEnum
 * @Package: com.car.orbit.orbitservice.enums
 * @Description: es时间查询常量
 * @Author: monkjavaer
 * @Data: 2019/3/29 11:13
 * @Version: V1.0
 */
public enum EsDateAggEnum {

    /** 以天为聚合条件 **/
    DAY(1, "1d"),
    /** 以周为聚合条件 **/
    WEEK(2, "1w"),
    /** 以周为聚合条件 **/
    MONTH(3, "1m"),

    /** 以天为聚合别名 **/
    DAYAGG(4, "dayAgg"),
    /** 以周为聚合别名 **/
    MONTHAGG(5, "monthAgg"),
    /** 以月为聚合别名 **/
    THREEOFMONTAGG(6, "threeOfMonthAgg"),

    /** 以时间聚合别名 **/
    TIMEAGG_ALIAS(7, "timeAggs"),
    /** 以区域聚合别名 **/
    AREAAGG_ALIAS(8, "areaAggs"),
    /** 以路口为聚合别名 **/
    ROADAGG_ALIAS(9, "roadAggs"),

    /** 以特征值为聚合别名 **/
    FEATUTRAGG_ALIAS(10, "featureAggs"),
    /** 以特征值为聚合别名 **/
    FEATUTRAGG_ALIAS_CHILD(11, "feature"),

    /** redis对象的field **/
    REDIS_FIELD(12, "oneCarOneGear"),

    ES_VEHICLE_FIELD(13, "VEHICLE_PROPERTIES"),

    ES_VEHICLE_FIELD_CHILDNAME(14, "feature_id"),

    ES_VEHICLE_FIELD_VEHICLE_PROPERTIES_CHILDNAME(15, "VEHICLE_PROPERTIES.feature_id");





    private int value;
    private String aggType;

    EsDateAggEnum(int value, String aggType) {
        this.value = value;
        this.aggType = aggType;
    }

    public int getValue() {
        return value;
    }

    public String getAggType() {
        return aggType;
    }

}
