package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.CoordinateInterface;
import com.m.gis.springboot.geo.base.common.GisBaseConstants;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.DouglasPeuckerSimplifier;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Title: GisSimplifyUtil
 * @Package: springboot.geo.base.utils
 * @Description: 抽稀工具
 * @Author: monkjavaer
 * @Data: 2018/6/9
 * @Version: V1.0
 */
public class GisSimplifyUtil {
    private static Logger logger = LoggerFactory.getLogger(GisGeometryFactoryUtil.class);

    public static Geometry simplifier(Geometry g) {
        return simplifier(g, GisBaseConstants.DEFAULT_SIMPLIFIER_TOLERANCE);
    }

    /***
     * @Description: 几何图形的抽稀，distanceTolerance为度，0.00001代表1米
     * @Author: monkjavaer
     * @Data: 14:36 2018/7/3
     * @Param: [g, distanceTolerance]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry simplifier(Geometry g, double distanceTolerance) {
        GisPreconditions.checkNotNull(g);
        GisPreconditions.checkArgument(distanceTolerance > 0);
        return TopologyPreservingSimplifier.simplify(g, distanceTolerance);
    }

    /**
     * @Description: 用于几何图形的抽稀，速度比simplifier更快
     * @Author: monkjavaer
     * @Data: 21:02 2018/6/9
     * @Param: [g, distanceTolerance]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry fastSimplifier(Geometry g, double distanceTolerance) {
        GisPreconditions.checkNotNull(g);
        GisPreconditions.checkArgument(distanceTolerance > 0);
        return DouglasPeuckerSimplifier.simplify(g, distanceTolerance);
    }

    /**
     * @param wkt
     * @param tolerance
     * @return
     * @name simplifier
     * @description 将wkt格式的几何形状抽稀, 入参为wkt格式
     * @author monkjavaer
     * @date 2017年11月9日
     */
    public static String simplifier(String wkt, double tolerance) throws GisParseGeometryBaseThrowableException {
        GisPreconditions.checkArgument(StringUtils.isNotBlank(wkt));

        Geometry geom = GisGeometryFactoryUtil.createGeometryByWKT(wkt);
        Geometry simpleGeom = simplifier(geom, tolerance);
        return simpleGeom.toString();
    }

    /**
     * @Description: 用于几何图形的抽稀，入参为wkt格式，默认抽稀精度
     * @Author: monkjavaer
     * @Data: 21:14 2018/6/9
     * @Param: [wkt]
     * @Throws
     * @Return java.lang.String
     */
    public static String simplifier(String wkt) throws GisParseGeometryBaseThrowableException {
        return simplifier(wkt, GisBaseConstants.DEFAULT_SIMPLIFIER_TOLERANCE);
    }


    /**
     * @Description: 将线的坐标点串抽稀，truncate为true时将各坐标点截断为默认精度
     * @Author: monkjavaer
     * @Data: 21:30 2018/6/9
     * @Param: [ptLst, tolerance, truncate]
     * @Throws
     * @Return java.lang.String
     */
    public static <T extends CoordinateInterface> String lineStringSimplifier(List<T> ptLst, double tolerance, boolean truncate) {
        GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptLst));
        Geometry geom = GisGeometryFactoryUtil.createLine(ptLst, truncate);
        Geometry simpleGeom = fastSimplifier(geom, tolerance);
        return simpleGeom.toString();
    }

    /**
     * @Description: 将线的坐标点串抽稀，采用默认抽稀精度，truncate为true时将各坐标点截断为默认精度
     * @Author: monkjavaer
     * @Data: 21:55 2018/6/9
     * @Param: [ptLst, truncate]
     * @Throws
     * @Return java.lang.String
     */
    public static <T extends CoordinateInterface> String lineStringSimplifier(List<T> ptLst, boolean truncate) {
        return lineStringSimplifier(ptLst, GisBaseConstants.DEFAULT_SIMPLIFIER_TOLERANCE, truncate);
    }


    /**
     * @Description: 将面的坐标点串抽稀，truncate为true时将各坐标点截断为默认精度
     * @Author: monkjavaer
     * @Data: 21:40 2018/6/9
     * @Param: [ptLst, tolerance, truncate]
     * @Throws
     * @Return java.lang.String
     */
    public static <T extends CoordinateInterface> String polygonSimplifier(List<T> ptLst, double tolerance, boolean truncate) {
        GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(ptLst));
        Geometry geom = GisGeometryFactoryUtil.createPolygon(ptLst, truncate);
        Geometry simpleGeom = fastSimplifier(geom, tolerance);
        return simpleGeom.toString();
    }

    /**
     * @Description: 将面的坐标点串抽稀，采用默认抽稀精度，truncate为true时将各坐标点截断为默认精度
     * @Author: monkjavaer
     * @Data: 21:56 2018/6/9
     * @Param: [ptLst, truncate]
     * @Throws
     * @Return java.lang.String
     */
    public static <T extends CoordinateInterface> String polygonSimplifier(List<T> ptLst, boolean truncate) {
        return polygonSimplifier(ptLst, GisBaseConstants.DEFAULT_SIMPLIFIER_TOLERANCE, truncate);
    }
}