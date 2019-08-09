package com.m.gis.springboot.district.common;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Title: GisDistrictType
 * @Package: com.m.gis.springboot.bo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/26
 * @Version: V1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GisDistrictType {
    private String type;
    private String name;
    private String dictCode;
    private String tableName;
    private String regex;

    public GisDistrictType() {
    }

    public GisDistrictType(String tableName, String regex){
        this.tableName = tableName;
        this.regex = regex;
    }

    public GisDistrictType(String type, String name, String dictCode, String tableName, String regex) {
        this.type = type;
        this.name = name;
        this.dictCode = dictCode;
        this.tableName = tableName;
        this.regex = regex;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDictCode() {
        return dictCode;
    }

    public String getTableName() {
        return tableName;
    }

    public String getRegex() {
        return regex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GisDistrictType) {
            GisDistrictType other = (GisDistrictType) obj;
            return other.getType().equals(type);
        }
        return false;
    }

    /***
     * @Description: 判断districtCode参数是否为满足该行政区域等级的行政区域编码
     * @Author: monkjavaer
     * @Data: 16:46 2018/6/28
     * @Param: [code]
     * @Throws
     * @Return boolean
     */
    public boolean isValidDistrictCode(String districtCode){
        if(StringUtils.isEmpty(regex)){
            //国家级的一般无行政编码，所以规则不做限制，相应的，districtCode为null或者空的话认为是合法的国家行政编码
            return StringUtils.isBlank(districtCode);
        }
        else{
            if(StringUtils.isBlank(districtCode))
                return false;
            else
                return districtCode.matches(regex);
        }
    }

    @Override
    public String toString() {
        return "GisDistrictType{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", dictCode='" + dictCode + '\'' +
                ", tableName='" + tableName + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
