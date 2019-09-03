package com.car.orbit.orbitservice.util.redis;

import com.car.orbit.orbitservice.entity.OrbitExport;
import com.car.orbit.orbitutil.tools.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表头导出redis的增删改查转化等业务操作
 */
public class ExportHeaderRedis extends BaseBusinessRedis {
    /**
     * 获取所有的缓存表头对象列表
     * @return
     */
    public static List<OrbitExport> getAllHeaderList(){
        List<OrbitExport> list = new ArrayList<>();
        list = redisClient.getValues(EXPORT_HEADER_KEY,OrbitExport.class);
        return list;
    }


    /**
     * 根据Code获取指定表头Name
     * @return 表头数组
     */
    public static String[] getHeaderNameByCode(String code){
        //数据库中表头以英文逗号分割
        String splitStr = ",";
        String value = redisClient.get(EXPORT_HEADER_KEY).get(code);
        String name = getInternationalName(value);
        return name.split(splitStr);
    }


    /**
     * 保存表头
     * @return
     */
    public static void saveHeader(OrbitExport export){
        redisClient.save(BaseBusinessRedis.EXPORT_HEADER_KEY, export.getCode(), JsonUtils.toJSONString(export));
    }


    /**
     * 保存表头
     * @return
     */
    public static void saveHeaderList(List<OrbitExport> exports){
        if(exports == null){
            return;
        }
        for (OrbitExport export : exports) {
            ExportHeaderRedis.saveHeader(export);
        }
    }

}
