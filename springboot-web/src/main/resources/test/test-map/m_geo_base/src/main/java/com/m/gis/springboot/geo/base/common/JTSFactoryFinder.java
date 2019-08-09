package com.m.gis.springboot.geo.base.common;

import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * @Title: JTSFactoryFinder
 * @Package: springboot.geo.base.common
 * @Description: 几何图形构造器
 * @Author: monkjavaer
 * @Data: 2018/6/8
 * @Version: V1.0
 */
public class JTSFactoryFinder {
    private static GeometryFactory defaultGeometryFactory = new GeometryFactory();

    public static GeometryFactory getDefaultGeometryFactory() {
        return defaultGeometryFactory;
    }

}
