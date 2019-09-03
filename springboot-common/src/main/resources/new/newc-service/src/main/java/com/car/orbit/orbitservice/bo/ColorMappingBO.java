package com.car.orbit.orbitservice.bo;

/**
 * @Title: ColorMappingBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/07/31 15:06
 * @Version: V1.0
 */
public class ColorMappingBO {

    /**
     * code
     */
    private String id;

    /**
     * 颜色
     */
    private String label;

    /**
     * code
     */
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
