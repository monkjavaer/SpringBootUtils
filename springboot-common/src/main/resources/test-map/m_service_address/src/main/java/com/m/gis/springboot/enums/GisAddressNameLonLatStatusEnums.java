package com.m.gis.springboot.enums;

public enum GisAddressNameLonLatStatusEnums {
    /**
     * 有地址
     */
    VALID(0),
    /**
     * 超出限定区域
     */
    OUT_OF_BOUNDS(1),
    /**
     * 无地址
     */
    INVALID(2);
    private Integer status;

    GisAddressNameLonLatStatusEnums(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
