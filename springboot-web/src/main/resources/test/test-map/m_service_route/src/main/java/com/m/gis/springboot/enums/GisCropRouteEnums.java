package com.m.gis.springboot.enums;

/**
 * @Title: GisAddressServiceExceptionEnums
 * @Package: com.m.gis.springboot.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public enum GisCropRouteEnums {
    /**
     * 从Source节点到定位点
     */
    SOURCE_LOCATION(1),
    /**
     * 从Target节点到定位点
     */
    TARGET_LOCATION(2),
    /**
     * 从定位点到Source节点
     */
    LOCATION_SOURCE(3),
    /**
     * 从定位点到Target节点
     */
    LOCATION_TARGET(4);

    private Integer value;

    GisCropRouteEnums(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean equals(GisCropRouteEnums other){
        return other.getValue()==value;
    }

}
