package com.m.gis.springboot.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Title: GisDistrictVO
 * @Package: com.m.gis.springboot.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/10
 * @Version: V1.0
 */
@JSONType(serialzeFeatures=SerializerFeature.BeanToArray, parseFeatures=Feature.SupportArrayToBean)
public class GisDistrictVO {

    /**
     * 行政区域类型
     */
    @JSONField(ordinal=1)
    private String type;

    /**
     * 行政区域编码
     */
    @JSONField(ordinal=2)
    private String code;

    /**
     * 行政区域名称
     */
    @JSONField(ordinal=3)
    private String name;

    /**
     * 行政区域轮廓WKT
     */
    @JSONField(ordinal=4)
    private String geom;

    public GisDistrictVO() {
    }

    public GisDistrictVO(String type,String code, String name, String geom) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.geom = geom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

}
