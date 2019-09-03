package com.m.gis.springboot.vo;

/**
 * @Title: GisDistrictNameVO
 * @Package: com.m.gis.springboot.vo
 * @Description: 用于select中的行政区域列表，展示行政区域的名字和编码
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public class GisDistrictNameVO {
    String code;
    String name;

    public GisDistrictNameVO() {
    }

    public GisDistrictNameVO(String code, String name) {
        this.code = code;
        this.name = name;
    }

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
}
