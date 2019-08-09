package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;

import java.util.List;

/**
 * @Title: GisDistrictCacheService
 * @Package: com.m.gis.springboot.service
 * @Description: 用于缓存从数据库查询的行政区域信息，若合并到GisDistrictService的话，Cache注释不起作用
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public interface GisDistrictCacheService {
    /***
     * @Description: 获取指定行政区域类型的所有行政区域数据
     * @Author: monkjavaer
     * @Data: 10:53 2018/6/27
     * @Param: [tableName]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisDistrict>
     */
    List<GisDistrict> getGisDistrictByType(GisDistrictType type);
}
