package com.m.gis.springboot.utils.route.formatter;

import com.m.gis.springboot.po.GisRouteRoad;

import java.util.List;

/**
 * @Title: GisRouteFormatter
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public interface GisRouteFormatter<T> {
    /***
     * @Description: 将路径结果按照一定格式输出
     * @Author: monkjavaer
     * @Data: 15:52 2018/8/28
     * @Param: [gisRouteRoads]
     * @Throws
     * @Return T
     */
    T format(List<List<GisRouteRoad>> gisRouteRoadGroups);
}
