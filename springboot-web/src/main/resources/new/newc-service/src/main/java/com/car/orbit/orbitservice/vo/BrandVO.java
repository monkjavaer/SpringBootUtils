package com.car.orbit.orbitservice.vo;

/**
 * @Title: BrandVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/22 16:58
 * @Version: V1.0
 */
public class BrandVO {

    private String code;
    private String name;
    private String parentCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
