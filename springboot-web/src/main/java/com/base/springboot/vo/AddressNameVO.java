package com.base.springboot.vo;

/**
 * @Title: AddressNameVO
 * @Package: com.base.springboot
 * @Description: 返回VO
 * @Author: monkjavaer
 * @Date: 2019/8/7 10:13
 * @Version: V1.0
 */
public class AddressNameVO {

    private Integer id;
    private String name;
    private String districtCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

}
