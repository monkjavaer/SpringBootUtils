package com.m.gis.springboot.service;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @Title: GisGeometryService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/15
 * @Version: V1.0
 */
public interface GisGeometryService {

    /***
     * @Description: 获取几何图形的缓冲区
     * @Author: monkjavaer
     * @Data: 12:48 2018/6/15
     * @Param: [geom, distanceInMeters]
     * @Throws
     * @Return org.postgis.Geometry
     */
    public Geometry getGeodesyBuffer(Geometry geom, double distanceInMeters);

}
