package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 品牌、子品牌相关redis的增删改查转化等业务操作
 */
public class BrandRedis extends BaseBusinessRedis {

    public static String UNKNOW_BRAND = "9999";
    public static String UNKNOW_BRAND_NAME = "未知";

    /**
     * 获取所有的缓存品牌对象列表
     * @return
     */
    public static List<OrbitVehicleBrand> getAllBrandList(){
        List<OrbitVehicleBrand> list = new ArrayList<>();
        list = redisClient.getValues(BRAND_KEY,OrbitVehicleBrand.class);
        return list;
    }

    /**
     * 获取所有的缓存品牌对象
     * @return
     */
    public static Map<String, String> getAllBrand(){
        return redisClient.get(BRAND_KEY);
    }
    /**
     * 获取所有的缓存子品牌对象列表
     * @return
     */
    public static List<OrbitVehicleBrandChild> getAllChildBrandList(){
        List<OrbitVehicleBrandChild> list = new ArrayList<>();
        list = redisClient.getValues(CHILD_BRAND_KEY, OrbitVehicleBrandChild.class);
        return list;
    }

    /**
     * 获取所有的缓存子品牌对象
     * @return
     */
    public static Map<String, String> getAllChildBrand(){
        return redisClient.get(CHILD_BRAND_KEY);
    }

    /**
     * 根据Code获取指定品牌对象
     * @return
     */
    public static OrbitVehicleBrand getBrandByCode(String code){
        if(code == null|| StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND;
        }
        OrbitVehicleBrand brand = redisClient.get(BRAND_KEY, code, OrbitVehicleBrand.class);
        return brand;
    }
    /**
     * 根据Code获取指定子品牌对象
     * 注意，这里外部需要自行判断，父品牌存在，但是子品牌未知的情况，避免存在父，但是子为空,查询出的子为   未知-未知
     * @return
     */
    public static OrbitVehicleBrandChild getChildBrandByCode(String code){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND+UNKNOW_BRAND;
        }
        OrbitVehicleBrandChild childBrand = redisClient.get(CHILD_BRAND_KEY, code, OrbitVehicleBrandChild.class);
        return childBrand;
    }
    /**
     * 根据name获取指定品牌对象
     * 这里的name值中文的Name，暂时不支持ES和EN的
     * @return
     */
    public static OrbitVehicleBrand getBrandByName(String name){
        if(name == null || StringUtils.isEmpty(name)){
            name = UNKNOW_BRAND_NAME;
        }
        OrbitVehicleBrand brand = redisClient.get(BRAND_NAME_KEY, name, OrbitVehicleBrand.class);
        return brand;
    }
    /**
     * 根据name获取指定子品牌对象
     * 这里的name值中文的Name，暂时不支持ES和EN的
     * 注意，这里外部需要自行判断，父品牌存在，但是子品牌未知的情况，避免存在父，但是子为空,查询出的子为   未知-未知
     * @return
     */
    public static OrbitVehicleBrandChild getChildBrandByName(String name){
        if(name == null || StringUtils.isEmpty(name)){
            name = UNKNOW_BRAND_NAME+UNKNOW_BRAND_NAME;
        }
        OrbitVehicleBrandChild childBrand = redisClient.get(CHILD_BRAND_NAME_KEY, name, OrbitVehicleBrandChild.class);
        return childBrand;
    }
    /**
     * 根据Code获取指定品牌Name
     * @return
     */
    public static String getBrandNameByCode(String code){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND;
        }
        String value = redisClient.get(BRAND_KEY,code);
        return getInternationalName(value);
    }

    /**
     * 根据Code和用户信息获取指定品牌的国际化Name，传入用户信息是避免反复请求redis的当前用户信息
     * @return
     */
    public static String getBrandNameByCodeAndUserInfo(String code, OrbitSysUser user){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND;
        }
        String value = redisClient.get(BRAND_KEY,code);
        return getInternationalName(value, user);
    }

    /**
     * 根据Code和用户信息获取指定品牌的国际化Name，传入用户信息是避免反复请求redis的当前用户信息
     * @return
     */
    public static String getBrandNameByCodeAndUserInfoAndBRAND(String code, OrbitSysUser user, Map<String, String> brands){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND;
        }
        String value = brands.get(code);
        return getInternationalName(value, user);
    }
    /**
     * 根据Code和token获取指定品牌Name
     * @return
     */
    public static String getBrandNameByCodeAndToken(String code,String token){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND;
        }
        String value = redisClient.get(BRAND_KEY,code);
        return getInternationalName(value,token);
    }

    /**
     * 根据Code获取指定子品牌Name
     * 注意，这里外部需要自行判断，父品牌存在，但是子品牌未知的情况，避免存在父，但是子为空,查询出的子为   未知-未知
     * @return
     */
    public static String getChildBrandNameByCode(String code){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND+UNKNOW_BRAND;
        }
        String value = redisClient.get(CHILD_BRAND_KEY,code);
        return getInternationalName(value);
    }

    public static String getChildBrandNameByCodeAndUserInfo(String code, OrbitSysUser user){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND+UNKNOW_BRAND;
        }
        String value = redisClient.get(CHILD_BRAND_KEY,code);
        return getInternationalName(value, user);
    }

    public static String getChildBrandNameByCodeAndUserInfoAndChildBrands(String code, OrbitSysUser user, Map<String, String> childBrands){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND+UNKNOW_BRAND;
        }
        String value = childBrands.get(code);
        return getInternationalName(value, user);
    }

    /**
     * 根据Code和token获取指定子品牌Name
     * @param code
     * @param token
     * @return
     */
    public static String getChildBrandNameByCodeAndToken(String code,String token){
        if(code == null || StringUtils.isEmpty(code)){
            code = UNKNOW_BRAND+UNKNOW_BRAND;
        }
        String value = redisClient.get(CHILD_BRAND_KEY,code);
        return getInternationalName(value,token);
    }

    /**
     * 根据子Code获取指定full全量的父子品牌Name
     * 这里只有确保子品牌存在的时候才能这样使用
     * @return
     */
    public static String getFullBrandNameByCode(String childCode){
        OrbitVehicleBrandChild obj = getChildBrandByCode(childCode);
        if (obj == null){
            return null;
        }
        return getFullBrandNameByCode(obj.getParentCode(),obj.getCode());
    }

    public static String concatFullBrandName(String brandName, String childBrandName){
        if (!StringUtils.isEmpty(childBrandName)) {
            return brandName + "-" + childBrandName;
        } else {
            return brandName;
        }
    }

    /**
     * 根据子Code和childCode获取指定full全量的父子品牌Name
     * 这里需要输入两个是为了child为null而父不为null的情况
     * @return
     */
    public static String getFullBrandNameByCode(String code, String childCode){
        String brandName = getBrandNameByCode(code);
        String childBrandName = getChildBrandNameByCode(childCode);
        return concatFullBrandName(brandName, childBrandName);
    }
    /**
     * 根据name获取指定品牌code
     * @return
     */
    public static String getBrandCodeByName(String name){
        OrbitVehicleBrand obj = getBrandByName(name);
        if(obj != null){
            return obj.getCode();
        }else{
            return "";
        }
    }
    /**
     * 根据name获取指定子品牌Code
     * @return
     */
    public static String getChildBrandCodeByName(String name){
        OrbitVehicleBrandChild obj = getChildBrandByName(name);
        if(obj != null){
            return obj.getCode();
        }else{
            return "";
        }
    }
    /**
     * 保存品牌
     * @return
     */
    public static void saveBrand(OrbitVehicleBrand brand){
        redisClient.save(BaseBusinessRedis.BRAND_KEY, brand.getCode(), JsonUtils.toJSONString(brand));
        redisClient.save(BaseBusinessRedis.BRAND_NAME_KEY, brand.getZhName(), JsonUtils.toJSONString(brand));
    }
    /**
     * 保存子品牌
     * @return
     */
    public static void saveChildBrand(OrbitVehicleBrandChild child) {
        redisClient.save(BaseBusinessRedis.CHILD_BRAND_KEY, child.getCode(), JsonUtils.toJSONString(child));
        OrbitVehicleBrand brand = redisClient.get(BRAND_KEY, child.getParentCode(), OrbitVehicleBrand.class);
        redisClient.save(BaseBusinessRedis.CHILD_BRAND_NAME_KEY, brand.getZhName() + "-" + child.getZhName(), JsonUtils.toJSONString(child));
    }

    /**
     * 保存品牌
     * @return
     */
    public static void saveBrandList(List<OrbitVehicleBrand> brands){
        if(brands == null){
            return;
        }
        for (OrbitVehicleBrand brand : brands) {
            BrandRedis.saveBrand(brand);
        }
    }
    /**
     * 保存子品牌
     * @return
     */
    public static void saveChildBrandList(List<OrbitVehicleBrandChild> childs){
        if (childs == null){
            return;
        }
        for (OrbitVehicleBrandChild child : childs) {
            BrandRedis.saveChildBrand(child);
        }
    }
}
