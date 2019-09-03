package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 系统业务相关Redis使用的基类
 * 处理系统字典的Code和对应名转换或其他转换，包含//城市编码 //区域编码 //路口编码 //品牌编码 //子品牌 //品牌名称 //子品牌名称等
 * @Author zks
 */
public class BaseBusinessRedis {

    protected static RedisClient redisClient;
    @Autowired
    private RedisClient redisClientTmp;
    @PostConstruct
    public void init(){
        redisClient = redisClientTmp;
    }

    public static final String SPLIT = ":";

    /**
     * redis中品牌key
     */
    public static final String  BRAND_KEY = "orbitVehicleBrandKey";
    /**
     * redis中子品牌key
     */
    public static final String  CHILD_BRAND_KEY = "orbitVehicleBrandChildKey";
    /**
     * redis中品牌nameKey
     */
    public static final String  BRAND_NAME_KEY = "orbitVehicleBrandNameKey";
    /**
     * redis中子品牌nameKey
     */
    public static final String  CHILD_BRAND_NAME_KEY = "orbitVehicleBrandChildNameKey";
    /**
     * redis中城市Key
     */
    public static final String  DIC_CITY_KEY = "orbitDicCityKey";
    /**
     * redis中区域Key
     */
    public static final String DIC_AREA_KEY = "orbitDicAreaKey";
    /**
     * redis中路口Key
     */
    public static final String  DIC_ROAD_KEY = "orbitDicRoadKey";
    /**
     * redis中路口Key
     */
    public static final String  DIC_DEVICE_KEY = "orbitDeviceRoadKey";
    /**
     * redis中颜色Key
     */
    public static final String  DIC_COLOR_KEY = "orbitDicColorKey";
    /**
     * redis中车辆类型Key
     */
    public static final String  DIC_VEHICLETYPE_KEY = "orbitDicVehivleTypeKey";
    /**
     * redis中特征Key
     */
    public static final String  DIC_FEATURE_KEY = "orbitDicFeatureKey";
    /**
     * 用户信息
     */
    public static final String USER_INFO = "UserInfo";
    /**
     * 正在布控中任务列表
     */
    public static final String ON_CONTROL_KEY = "OnControlTask";
    /**
     * 正在布控中任务匹配列表
     */
    public static final String ON_CONTROL_MATCHER_KEY = "OnControlTaskMatcher";
    /**
     * redis中导出表头key
     */
    public static final String  EXPORT_HEADER_KEY = "exportHeaderKey";
    /**
     * redis中黑名单key
     */
    public static final String  BLACKLIST_KEY = "orbitBlacklistKey";
    /**
     * redis中白名单key
     */
    public static final String  WHITELIST_KEY = "orbitWhitelistKey";
    /**
     * redis中颜色映射Key
     */
    public static final String DIC_COLOR_MAPPING_KEY = "orbitColorMappingKey";
    /**
     * redis中车辆类型映射Key
     */
    public static final String DIC_VEHICLE_TYPE_MAPPING_KEY = "orbitVehicleTypeMappingKey";

    protected static String getInternationalName(String value){
        InternationalObject vo = null;
        if (!StringUtils.isEmpty(value)) {
            vo = JsonUtils.toBean(value, InternationalObject.class);
        }
        if(vo != null){
            OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
            return getName(vo, user);
        }else{
            return "";
        }
    }

    protected static String getInternationalName(String value, OrbitSysUser user){
        InternationalObject vo = null;
        if (!StringUtils.isEmpty(value)) {
            vo = JsonUtils.toBean(value, InternationalObject.class);
        }
        if(vo != null){
            return getName(vo, user);
        } else {
            return "";
        }
    }

    private static String getName(InternationalObject vo, OrbitSysUser user) {
        String name = vo.getEnName();
        if(user != null && StringUtils.isNotEmpty(user.getLanguage())){
            String la =  user.getLanguage().split("_")[0].toUpperCase();
            switch (la){
                case "ZH-CN":
                    name = vo.getZhName();
                    break;

                case "EN":
                    name = vo.getEnName();
                    break;

                case  "ES":
                    name = vo.getEsName();
                    break;

                default:
                    break;
            }
        }
        return name;
    }

    protected static String getInternationalName(String value, String token){
        InternationalObject vo = null;
        if (!StringUtils.isEmpty(value)) {
            vo = JsonUtils.toBean(value, InternationalObject.class);
        }
        if(vo != null){
            OrbitSysUser user = OrbitSysUserRedis.getLoginUser(token);
            return getName(vo, user);
        }else{
            return "";
        }
    }

    /**
     * 获取分布式锁
     * lockKey：锁key
     * expireTime:超时时间
     */
    protected static String getLock(String lockKey, int expireTime){
        try {
            while (true){
                String requestId = UUID.randomUUID().toString();
                if(redisClient.tryGetLock(lockKey,requestId,expireTime)){
                    return requestId;
                }
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 释放分布式锁
     * lockKey：锁key
     * requestId:分布式请求标识，解铃还须系铃人。
     */
    protected static boolean releaseLock(String lockKey, String requestId){
        try {
            while (true){
                if(redisClient.releaseLock(lockKey,requestId)){
                    return true;
                }
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
