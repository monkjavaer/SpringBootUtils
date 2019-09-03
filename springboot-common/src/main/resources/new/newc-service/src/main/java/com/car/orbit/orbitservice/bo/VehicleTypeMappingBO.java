package com.car.orbit.orbitservice.bo;

/**
 * @Title: ColorMappingBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/07/31 15:06
 * @Version: V1.0
 */
public class VehicleTypeMappingBO {

    /**
     * code
     */
    private String id;

    /**
     * 类型
     */
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
