package com.m.gis.springboot.district.common;

import com.m.gis.springboot.common.utils.JaxbUtil;
import com.m.gis.springboot.district.exception.GisDistrictException;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: GisDistrictTypeUtil
 * @Package: com.m.gis.springboot.district.common
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/2
 * @Version: V1.0
 */
public abstract class GisAbstractDistrictTypeUtil {
    private final static Logger logger = LoggerFactory.getLogger(GisAbstractDistrictTypeUtil.class);

    public GisAbstractDistrictTypeUtil(){
        districtTypeCollection = JaxbUtil.convertToJavaBean(com.m.gis.springboot.district.common.GisDistrictTypeCollection.class,getConfigPath());
        logger.info("district types are configured successfully.");
        logger.info(districtTypeCollection.getGisDistrictTypes().toString());
    }

    /**
     * 行政区域类型集合
     */
    protected com.m.gis.springboot.district.common.GisDistrictTypeCollection districtTypeCollection;

    /**
     * 默认行政区域类型配置文件路径
     */
    protected String DISTRICT_TYPE_CONFIG_PATH = "/district.xml";

    protected String getDefalutConfigPath(){return DISTRICT_TYPE_CONFIG_PATH;}

    public com.m.gis.springboot.district.common.GisDistrictTypeCollection getGisDistrictTypeCollection(){
        return districtTypeCollection;
    }


    /***
     * @Description: 返回配置文件路径
     * @Author: monkjavaer
     * @Data: 9:31 2018/7/3
     * @Param: []
     * @Throws
     * @Return java.lang.String
     */
    protected abstract String getConfigPath();


    /***
     * @Description: 判断是否存在编码为type的行政区域类型
     * @Author: monkjavaer
     * @Data: 17:13 2018/7/2
     * @Param: [type]
     * @Throws
     * @Return boolean
     */
    public Boolean contains(String type){
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.equals(type))
                return true;
        }
        return false;
    }

    /***
     * @Description: 获取行政区域有几级，根据行政区域类型的字典表配置
     * @Author: monkjavaer
     * @Data: 17:44 2018/6/26
     * @Param: []
     * @Throws
     * @Return java.lang.Integer
     */
    public Integer getDistrictLevels() {
        return districtTypeCollection.getGisDistrictTypes().size();
    }

    /***
     * @Description: 获取最高级别行政区域类型
     * @Author: monkjavaer
     * @Data: 14:11 2018/6/27
     * @Param: []
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getHighestDistrictType() {
        return districtTypeCollection.getGisDistrictTypes().get(0);
    }

    /***
     * @Description:  获取最低级别行政区域类型
     * @Author: monkjavaer
     * @Data: 14:11 2018/6/27
     * @Param: []
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getLowestDistrictType() {
        return districtTypeCollection.getGisDistrictTypes().get(getDistrictLevels()-1);
    }

    /**
     * 获取倒数第n级别行政区域类型
     * @param n
     * @return
     */
    public com.m.gis.springboot.district.common.GisDistrictType getLowerDistrictType(int n) {
        if (n > districtTypeCollection.getGisDistrictTypes().size()){
            return null;
        }
        return districtTypeCollection.getGisDistrictTypes().get(getDistrictLevels() - n);
    }

    /***
     * @Description: 获取父行政区域类型,如果已经是最高级别行政区域，则依然返回该行政区域
     * @Author: monkjavaer
     * @Data: 21:26 2018/6/28
     * @Param: [districtType]
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getParentDistrictType(com.m.gis.springboot.district.common.GisDistrictType districtType){
        com.m.gis.springboot.district.common.GisDistrictType parent = getHighestDistrictType();
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.equals(districtType)){
                return parent;
            }
            parent = item;
        }
        throw new GisDistrictException(String.format("GisDistrictTypeCollection class getParentDistrictType function errors, no district type be found with type {%s}.",districtType.toString()));
    }

    /***
     * @Description: 获取子行政区域类型,如果已经是最低级别行政区域，则依然返回该行政区域
     * @Author: monkjavaer
     * @Data: 21:32 2018/6/28
     * @Param: [districtType]
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getChildrenDistrictType(com.m.gis.springboot.district.common.GisDistrictType districtType){
        com.m.gis.springboot.district.common.GisDistrictType children = getLowestDistrictType();
        for(int i = getDistrictLevels()-1;i>=0;i--){
            if(districtTypeCollection.getGisDistrictTypes().get(i).equals(districtType)){
                return children;
            }
            children = districtTypeCollection.getGisDistrictTypes().get(i);
        }
        throw new GisDistrictException(String.format("GisDistrictTypeCollection class getChildrenDistrictType function errors, no district type be found with type {%s}.",districtType.toString()));
    }

    /***
     * @Description: 判断是否为最高级别的行政区域
     * @Author: monkjavaer
     * @Data: 10:49 2018/6/28
     * @Param: [type]
     * @Throws
     * @Return boolean
     */
    public boolean isHighestDistrictType(com.m.gis.springboot.district.common.GisDistrictType type) {
        return getHighestDistrictType().equals(type);
    }

    /***
     * @Description: 判断是否为最低级别的行政区域
     * @Author: monkjavaer
     * @Data: 10:49 2018/6/28
     * @Param: [type]
     * @Throws
     * @Return boolean
     */
    public boolean isLowestDistrictType(com.m.gis.springboot.district.common.GisDistrictType type) {
        return getLowestDistrictType().equals(type);
    }

    /***
     * @Description: 判断是否为合法的行政区域类型编码
     * @Author: monkjavaer
     * @Data: 21:00 2018/6/28
     * @Param: [code]
     * @Throws
     * @Return boolean
     */
    public boolean isValid(String code){
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.getType().equals(code))
                return true;
        }
        return false;
    }

    /***
     * @Description: 判断是否为合法的行政区域编码
     * @Author: monkjavaer
     * @Data: 15:32 2018/7/11
     * @Param: [districtCode]
     * @Throws
     * @Return boolean
     */
    public boolean isValidDistrictCode(String districtCode){
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.isValidDistrictCode(districtCode))
                return true;
        }
        return false;
    }


    /***
     * @Description: 根据行政区域类型字典获取行政等级，找不到抛出异常
     * @Author: monkjavaer
     * @Data: 17:27 2018/6/27
     * @Param: [type]
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getDistrictType(String type) {
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.getType().equals(type))
                return item;
        }
        throw new GisDistrictException(String.format("GisDistrictTypeCollection class getDistrictType function errors, no district type be found with type {%s}.",type));
    }

    /***
     * @Description: 根据行政区域类型等级来获取行政类型，0为最高级
     * @Author: monkjavaer
     * @Data: 11:05 2018/6/28
     * @Param: [level]
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getDistrictType(Integer level){
        GisPreconditions.checkArgument(level>=0&&level<=getDistrictLevels(),"GisDistrictTypeCollection class getDistrictType function errors, param {level} must be between 0 and lowest level.");
        return districtTypeCollection.getGisDistrictTypes().get(level);
    }

    /***
     * @Description: 根据行政编码返回该行政编码对应的行政等级，如果行政编码为空或者null，表示国家级，找不到抛出异常
     * @Author: monkjavaer
     * @Data: 16:48 2018/6/28
     * @Param: [districtCode]
     * @Throws
     * @Return com.m.gis.springboot.bo.GisDistrictType
     */
    public com.m.gis.springboot.district.common.GisDistrictType getDistrictTypeByDistrictCode(String districtCode){
        for(com.m.gis.springboot.district.common.GisDistrictType item:districtTypeCollection.getGisDistrictTypes()){
            if(item.isValidDistrictCode(districtCode))
                return item;
        }
        throw new GisDistrictException(String.format("GisDistrictTypeCollection class getDistrictTypeByDistrictCode function errors, no district type be found with districtCode {%s}.",districtCode));
    }


    /***
     * @Description: 根据指定的行政区域等级，获取该行政等级对应的编码
     * @Author: monkjavaer
     * @Data: 15:39 2018/10/8
     * @Param: [districtCode, districtType]
     * @Throws
     * @Return java.lang.String
     */
    public String getDistrictCodeByDistrictType(String districtCode, com.m.gis.springboot.district.common.GisDistrictType districtType){
        if(false==isValidDistrictCode(districtCode))
            throw new GisDistrictException(String.format("getDistrictCodeByDistrictType function errors, district code {%s} is not a valid district code.",districtCode));

        if(isHighestDistrictType(districtType)&&StringUtils.isBlank(districtType.getRegex())){
            //如果最高等级的行政区域编码规则没有定义（如国家级可能不需要行政编码），则返回空
            return "";
        }

        String regex = districtType.getRegex();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(districtCode);
        if(matcher.find()){
            return matcher.group();
        }
        throw new GisDistrictException(String.format("GisDistrict class getDistrictCodeByDistrictType function errors, no district code be found with pattern {%s}.",regex));
    }


    /***
     * @Description: 获取父行政编码
     * @Author: monkjavaer
     * @Data: 21:23 2018/6/28
     * @Param: []
     * @Throws
     * @Return java.lang.String
     */
    public String getParentDistrictCode(String districtCode){
        //获取行政区域相应的行政区域类型
        com.m.gis.springboot.district.common.GisDistrictType districtType = getDistrictTypeByDistrictCode(districtCode);
        //如果已经是最高级别行政区域，则返回空值
        if(isHighestDistrictType(districtType))
            throw new GisDistrictException("getParentDistrictCode function errors, highest district type can not get parent code.");

        //获取父行政区域的正则表达式
        com.m.gis.springboot.district.common.GisDistrictType parentDistrictType = getParentDistrictType(districtType);
        //district.xml文件中配置的级别type
        Integer parentType = Integer.valueOf(parentDistrictType.getType());

        //新版本行政区划数据编码规则：一级行政区划编码（3位）二级行政区划编码（3位）三级行政区划编码（3位）四级行政区划编码（3位）
        //实例：中国(001)四川省(008)成都市(001)双流区(002)=001008001002
        int digit = 3;

        //策略：截取掉不属于父级的再加上相应长度的“0”
        StringBuilder tempCode = new StringBuilder(districtCode.substring(0,parentType * digit));
        int add = districtCode.length() - parentType * digit;
        for (int i = 0;i < add;i++) {
            tempCode.append("0");
        }
        return tempCode.toString();
    }


}
