package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.CoordinateInterface;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.common.JTSFactoryFinder;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.commons.lang3.StringUtils;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisGeometryFactoryUtil
 * @Package: springboot.geo.base.utils
 * @Description: 用于gis几何生成的静态工具类
 * @Author: monkjavaer
 * @Data: 2018/6/8
 * @Version: V1.0
 */
public class GisGeometryFactoryUtil {
    private static Logger logger = LoggerFactory.getLogger(GisGeometryFactoryUtil.class);

    /**
     * @Description: 创建点
     * @Author: monkjavaer
     * @Data: 13:41 2018/6/9
     * @Param: [coord]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     */
    public static Point createPoint(GisCoordinate coord) {
        GisPreconditions.checkNotNull(coord);
        Point point = JTSFactoryFinder.getDefaultGeometryFactory().createPoint(coord.toJTSCoordinate());

        return point;
    }

    /**
     * @Description: 创建点，支持格式化
     * @Author: monkjavaer
     * @Data: 12:00 2018/6/8
     * @Param: [lon, lat]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     */
    public static Point createPoint(double lon, double lat, boolean truncate) {
        return createPoint(new GisCoordinate(lon, lat, truncate));
    }

    /**
     * @Description: 创建点, 默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 14:00 2018/6/9
     * @Param: [lon, lat]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     */
    public static Point createPoint(double lon, double lat) {
        return createPoint(lon, lat, false);
    }

    /*	*//**
     * @Description: 创建点
     * @Author: monkjavaer
     * @Data: 12:01 2018/6/8
     * @Param: [lon, lat]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     *//*
	public static Point createPoint(String lon, String lat) {
		GisPreconditions.checkLonLat(lon,lat);
		double lonD = Double.valueOf(lon);
		double latD = Double.valueOf(lat);
		return createPoint(lonD, latD);
	}*/

    /**
     * @Description: 创建点，truncate为true时，默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 12:02 2018/6/8
     * @Param: [pt]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     */
    public static <T extends CoordinateInterface> Point createPoint(CoordinateInterface pt, boolean truncate) {
        GisPreconditions.checkNotNull(pt);
        return createPoint(new GisCoordinate(pt, truncate));
    }

    /**
     * @Description: 创建点
     * @Author: monkjavaer
     * @Data: 14:02 2018/6/9
     * @Param: [pt]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Point
     */
    public static <T extends CoordinateInterface> Point createPoint(CoordinateInterface pt) {
        return createPoint(pt, false);
    }

    /**
     * @Description: 生成线段，truncate为true时，默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 12:04 2018/6/8
     * @Param: [ptLst]
     * @Throws
     * @Return com.vividsolutions.jts.geom.LineString
     */
    public static <T extends CoordinateInterface> LineString createLine(List<T> ptLst, boolean truncate) {
        GisPreconditions.checkNotNull(ptLst);
        List<Coordinate> coords = new ArrayList<Coordinate>();
        for (CoordinateInterface pt : ptLst) {
            coords.add(new GisCoordinate(pt, truncate).toJTSCoordinate());
        }

        if (ptLst.size() == 1)
            coords.add(new GisCoordinate(ptLst.get(0), truncate).toJTSCoordinate());

        LineString line = JTSFactoryFinder.getDefaultGeometryFactory().createLineString(coords.toArray(new Coordinate[coords.size()]));
        return line;
    }

    /**
     * @Description: 生成线段
     * @Author: monkjavaer
     * @Data: 14:05 2018/6/9
     * @Param: [ptLst]
     * @Throws
     * @Return com.vividsolutions.jts.geom.LineString
     */
    public static <T extends CoordinateInterface> LineString createLine(List<T> ptLst) {
        return createLine(ptLst, false);
    }

    /**
     * @Description: 创建面，truncate为true时，默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 12:07 2018/6/8
     * @Param: [ptLst]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Polygon
     */
    public static <T extends CoordinateInterface> Polygon createPolygon(List<T> ptLst, boolean truncate) {
        GisPreconditions.checkNotNull(ptLst);
        List<Coordinate> coords = new ArrayList<Coordinate>();
        for (CoordinateInterface pt : ptLst) {
            coords.add(new GisCoordinate(pt, truncate).toJTSCoordinate());
        }
        Polygon polygon = JTSFactoryFinder.getDefaultGeometryFactory()
                .createPolygon(coords.toArray(new Coordinate[coords.size()]));

        return polygon;
    }

    /**
     * @Description: 生成面
     * @Author: monkjavaer
     * @Data: 14:06 2018/6/9
     * @Param: [ptLst]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Polygon
     */
    public static <T extends CoordinateInterface> Polygon createPolygon(List<T> ptLst) {
        return createPolygon(ptLst, false);
    }


    /**
     * @Description: 根据wkt构建多边形，truncate为true时默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 12:08 2018/6/8
     * @Param: [geom]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createGeometryByWKT(String geom, boolean truncate) throws GisParseGeometryBaseThrowableException {
        GisPreconditions.checkArgument(StringUtils.isNotBlank(geom));
        WKTReader reader = new WKTReader(JTSFactoryFinder.getDefaultGeometryFactory());
        Geometry polygon = null;
        try {
            polygon = (Geometry) reader.read(geom);
        } catch (ParseException e) {
            logger.error("parse failed when create geometry from wkt.");
            throw new GisParseGeometryBaseThrowableException("parse failed when create geometry from wkt.");
        }
        return truncate ? GisFormatUtil.formatGeometry(polygon) : polygon;
    }

    /**
     * @Description: 根据wkt生成几何
     * @Author: monkjavaer
     * @Data: 18:11 2018/6/9
     * @Param: [geom]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createGeometryByWKT(String geom) throws GisParseGeometryBaseThrowableException {
        return createGeometryByWKT(geom, false);
    }

    /**
     * @Description: 根据两个点生成一个矩形, truncate为true时默认保留小数点5位(GisBaseConstants.DEFAULT_LONLAT_PRECISION)
     * @Author: monkjavaer
     * @Data: 12:14 2018/6/8
     * @Param: [minLat, minLon, maxLat, maxLon]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Polygon
     */
    public static Polygon createEnvelop(double minLat, double minLon, double maxLat, double maxLon, Boolean truncate) {
        GisPreconditions.checkLonLat(minLon, minLat);
        GisPreconditions.checkLonLat(maxLon, maxLat);

        List<GisCoordinate> ptList = new ArrayList<GisCoordinate>();
        ptList.add(new GisCoordinate(minLon, minLat, truncate));
        ptList.add(new GisCoordinate(minLon, maxLat, truncate));
        ptList.add(new GisCoordinate(maxLon, maxLat, truncate));
        ptList.add(new GisCoordinate(maxLon, minLat, truncate));
        ptList.add(new GisCoordinate(minLon, minLat, truncate));

        return createPolygon(ptList, truncate);
    }

    /**
     * @Description: 根据两个点生成一个矩形
     * @Author: monkjavaer
     * @Data: 18:36 2018/6/9
     * @Param: [minLat, minLon, maxLat, maxLon]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Polygon
     */
    public static Polygon createEnvelop(double minLat, double minLon, double maxLat, double maxLon) {
        return createEnvelop(minLat, minLon, maxLat, maxLon, false);
    }

    /***
     * @Description: 构建圆，根据球面坐标来缓冲生成，误差较小
     * @Author: monkjavaer
     * @Data: 11:08 2018/6/19
     * @Param: [centerLon, centerLat, distanceInMeters]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createCircle(double centerLon, double centerLat, double distanceInMeters, boolean truncate){
        GisPreconditions.checkLonLat(centerLon,centerLat);
        GisPreconditions.checkArgument(distanceInMeters>0, "createCircle funtion errors, param {distanceInMeters} must be positive.");
        return GisGeodesyUtil.createPointBuffer(centerLon,centerLat,distanceInMeters,truncate);
    }

    /***
     * @Description: 构建圆，根据球面坐标来缓冲生成，误差较小，默认保留小数点精度
     * @Author: monkjavaer
     * @Data: 11:16 2018/6/19
     * @Param: [centerLon, centerLat, distanceInMeters]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createCircle(double centerLon, double centerLat, double distanceInMeters){
        return createCircle(centerLon,centerLat,distanceInMeters,false);
    }

    /***
     * @Description: 根据投影坐标转换来生成缓冲区，在高纬度地区会产生较大误差
     * @Author: monkjavaer
     * @Data: 18:11 2018/6/14
     * @Param: [geom, radiusInMeters]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createBuffer(Geometry geom, double radiusInMeters) throws TransformException {
            Geometry pGeom = GisCRSUtil.toProjection(geom);
            Geometry pBufferedGeom = pGeom.buffer(radiusInMeters);
            return GisCRSUtil.fromProjection(pBufferedGeom);
    }

}
