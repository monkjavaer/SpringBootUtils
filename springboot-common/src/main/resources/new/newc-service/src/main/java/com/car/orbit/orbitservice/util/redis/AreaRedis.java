package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * area区域信息缓存
 * @author zks
 */
public class AreaRedis extends BaseBusinessRedis {

    /**
     * 获取所有区域信息
     * @return
     */
    public static List<OrbitResArea> getAllArea(){
        return redisClient.getValues(DIC_AREA_KEY,OrbitResArea.class);
    }

    /**
     * 根据code获取Area信息
     * @param code
     * @return
     */
    public static OrbitResArea getAreaByCode(String code) {
        String value = redisClient.get(DIC_AREA_KEY).get(code);
        if (!StringUtils.isEmpty(value)) {
            OrbitResArea vo = JsonUtils.toBean(value, OrbitResArea.class);
            return vo;
        }else{
            return null;
        }
    }

    /**
     * 根据code获取Name信息
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        OrbitResArea area = getAreaByCode(code);
        if(area == null){
            return null;
        }
        return area.getName();
    }

    /**
     * 保存对象
     * @param vo
     */
    public static void saveVo(OrbitResArea vo) {
        redisClient.save(DIC_AREA_KEY, vo.getId(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     * @param list
     */
    public static void saveVoList(List<OrbitResArea> list) {
        if(list == null ){
            return;
        }
        for(OrbitResArea vo : list){
            saveVo(vo);
        }
    }
}
