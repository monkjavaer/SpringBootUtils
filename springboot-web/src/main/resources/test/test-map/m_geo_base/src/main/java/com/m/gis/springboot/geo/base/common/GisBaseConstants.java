package com.m.gis.springboot.geo.base.common;

/**
 * @Title: GisBaseConstants
 * @Package: springboot.geo.base.common
 * @Description: defined constants used in gis_geo_base project
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class GisBaseConstants {
    /**
     * 默认坐标系
     */
    public final static String DEFAULT_CRS = "EPSG:4326";

    /**
     * 默认投影坐标系
     */
    public final static String DEFAULT_PROJECTON_CRS = "EPSG:3857";

    /**
     * 用于拼接wkt为ewkt格式(EWKT格式不是OGC标准，而是包含空间参考系统(SRID)标识符的PostGIS特定格式)
     * 例如：'SRID=4269;LINESTRING(-71.160281 42.258729,-71.160837 42.259113,-71.161144 42.25932)'
     * */
    public final static String DEFAULT_SRID_STRING = "SRID=4326;";

    /**
     * 默认坐标的容差值，小于该容差值认为是一个点(单位度，0.00001度近似为1米)
     */
    public final static double DEFAULT_COORDINATE_TOLERANCE_IN_DEGRESS = 0.00001;

    /**
     * 默认抽稀的容差值
     */
    public final static double DEFAULT_SIMPLIFIER_TOLERANCE = 0.00001;

    /**
     * 默认圆的构造边数
     */
    public final static int CIRCLE_SIDES = 72;

    /**
     * 默认经纬度精度，小数点位数
     */
    public final static int DEFAULT_LONLAT_PRECISION = 5;

    /**
     * 经纬度范围
     */
    public final static double MAXLONGITUDE = 180;
    public final static double MINLONGITUDE = -180;
    public final static double MAXLATITUDE = 90;
    public final static double MINLATITUDE = -90;


    /**
     * 并行判断点是否在几何内的线程数
     */
    public final static int fastWithinThreadCounts = 8;

}
