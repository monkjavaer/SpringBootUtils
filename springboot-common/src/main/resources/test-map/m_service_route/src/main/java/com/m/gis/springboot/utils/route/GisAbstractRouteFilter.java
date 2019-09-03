package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.geo.base.common.GisCoordinate;

import java.util.List;

/**
 * @Title: GisAbstractRouteFilter
 * @Package: com.m.gis.springboot.utils
 * @Description: 用于描述路径查询时的空间范围，减少路网查询量
 * @Author: monkjavaer
 * @Data: 2018/7/31
 * @Version: V1.0
 */
public abstract class GisAbstractRouteFilter {

    /***
     * @Description: 给定路径的起始、终止和经过点，给出路网的查询空间范围
     * @Author: monkjavaer
     * @Data: 17:12 2018/7/31
     * @Param: [points]
     * @Throws
     * @Return java.lang.String
     */
    public abstract String getRouteFilterWkt(List<GisCoordinate> points);

}
