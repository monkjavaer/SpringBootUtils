package com.m.gis.springboot.enums;

import org.apache.commons.lang3.StringUtils;

public enum GisAddressTypeEnums implements GisBaseEnum{
    /**
     * 行政区域地名类型
     */
    DISTRICT("01"),
    /**
     * 道路地名类型
     */
    ROAD("02"),
    /**
     * 建筑物地名类型
     */
    BUILDING("03"),
    /**
     * 兴趣点地名类型
     */
    POI("04"),
    /**
     * 地址地名类型
     */
    ADDRESS("05"),
    /**
     * 用户编辑地名类型
     */
    USER("06"),
    /**
     * 国家级地名类型，01对应国家级行政区域类型编码，在district.xml中定义
     */
    NATION("0101", "01"),
    /**
     * 大区级地名类型，02对应大区级行政区域类型编码，在district.xml中定义
     */
    DEPARTMENT("0102", "02"),
    /**
     * 省级地名类型，03对应省级行政区域类型编码，在district.xml中定义
     */
    PROVINCE("0103","03"),
    /**
     * 市级地名类型，04对应市级行政区域类型编码，在district.xml中定义
     */
    CITY("0104","04");

    private String value;
    private String districtTypeCode;

    GisAddressTypeEnums(String value){
        this.value = value;
    }

    GisAddressTypeEnums(String value, String districtTypeCode){
        this.value = value;
        this.districtTypeCode = districtTypeCode;
    }

    public Boolean equals(GisAddressTypeEnums other){
        return StringUtils.equals(value,other.getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }


    @Override
    public String getValue() {
        return this.value;
    }

    public String getDistrictTypeCode() {
        return districtTypeCode;
    }

    /***
     * @Description: 将行政区域地名类型转换为对应的行政区域类型
     * @Author: monkjavaer
     * @Data: 17:35 2018/7/4
     * @Param: [type]
     * @Throws
     * @Return com.m.gis.springboot.district.common.GisDistrictType
     */
    public Boolean isDistrictAddressNameType(){
        return StringUtils.isNotBlank(districtTypeCode);
    }


    /***
     * @Description: 根据编码获取地名类型
     * @Author: monkjavaer
     * @Data: 17:57 2018/7/4
     * @Param: [type]
     * @Throws
     * @Return com.m.gis.springboot.enums.GisAddressTypeEnums
     */
    public static GisAddressTypeEnums getGisAddressType(String type){
        for(GisAddressTypeEnums item:values()){
            if(item.getValue().equals(type))
                return item;
        }
        return null;
    }

    /***
     * @Description: 判断type参数是否为合法地名类型编码
     * @Author: monkjavaer
     * @Data: 17:00 2018/7/4
     * @Param: [type]
     * @Throws
     * @Return java.lang.Boolean
     */
    public static Boolean isValid(String value){
        for(GisAddressTypeEnums item:values()){
            if(item.getValue().equals(value))
                return true;
        }
        return false;
    }

    /***
     * @Description: 经纬度查询地名时，行政区域类型的地名是不应该包含在返回结果中的
     * @Author: monkjavaer
     * @Data: 16:43 2018/7/4
     * @Param: []
     * @Throws
     * @Return java.lang.String
     */
    public static String getLonLatQueryType(){
        return new StringBuilder()
                .append(GisAddressTypeEnums.ROAD.getValue())
                .append(",")
                .append(GisAddressTypeEnums.BUILDING.getValue())
                .append(",")
                .append(GisAddressTypeEnums.POI.getValue())
                .append(",")
                .append(GisAddressTypeEnums.ADDRESS.getValue())
                .append(",")
                .append(GisAddressTypeEnums.USER.getValue()).toString();
    }

}
