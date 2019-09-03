package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitColorMapping;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ColorMapping缓存
 */
public class ColorMappingRedis extends BaseBusinessRedis {

    /**
     * 保存对象
     *
     * @param vo
     */
    public static void saveVo(OrbitColorMapping vo) {
        redisClient.save(DIC_COLOR_MAPPING_KEY, vo.getSourceCode(), JsonUtils.toJSONString(vo));
    }

    /**
     * 保存列表
     *
     * @param list
     */
    public static void saveVoList(List<OrbitColorMapping> list) {
        if (list == null) {
            return;
        }
        for (OrbitColorMapping vo : list) {
            saveVo(vo);
        }
    }

    /**
     * 映射颜色
     *
     * @param srcCode
     * @return
     */
    public static String mapping(String srcCode) {
        String value = redisClient.get(DIC_COLOR_MAPPING_KEY).get(srcCode);

        if (!StringUtils.isEmpty(value)) {
            OrbitColorMapping vo = JsonUtils.toBean(value, OrbitColorMapping.class);
            return vo.getCode();
        } else {
            return srcCode;
        }
    }
}
