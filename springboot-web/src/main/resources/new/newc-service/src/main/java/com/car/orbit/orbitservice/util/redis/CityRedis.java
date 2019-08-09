package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * city信息缓存
 * @author zks
 */
public class CityRedis extends BaseBusinessRedis {

    /**
     * 获取所有城市信息
     * @return
     */
    public static List<OrbitResCity> getAllCitys(){
        return redisClient.getValues(DIC_CITY_KEY,OrbitResCity.class);
    }

    /**
     * 根据code获取城市信息
     * @param code
     * @return
     */
    public static OrbitResCity getByCode(String code) {
        String value = redisClient.get(DIC_CITY_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitResCity vo = JsonUtils.toBean(value, OrbitResCity.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据code名称信息
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        OrbitResCity city = getByCode(code);
        if (city==null) {
            return null;
        }
        return city.getName();
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitResCity vo) {
        redisClient.save(DIC_CITY_KEY, vo.getId(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitResCity> list) {
        if(list == null ){
            return;
        }
        for(OrbitResCity vo : list){
            saveVo(vo);
        }
    }
}
