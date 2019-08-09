package com.car.orbit.orbitservice.qo;

/**
 * @Title: ColorMappingQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 车辆类型映射表查询条件
 * @Author: monkjavaer
 * @Date: 2019/07/31 14:52
 * @Version: V1.0
 */
public class VehicleTypeMappingQO {

    public VehicleTypeMappingQO(String country) {
        this.country = country;
    }

    /**
     * 国家编码
     */
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
