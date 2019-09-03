package com.m.gis.springboot.common;

/**
 * @Title: GisRouteConstants
 * @Package: com.m.gis.springboot.common
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/27
 * @Version: V1.0
 */
public class GisRouteConstants {

    /**
     * 给定一个点，寻找最临近路段的容差值，这里近似为100米
     */
    public static Double GIS_NEAREST_EDGE_TOLERANCE = 0.001;

    /**
     * 路径服务中相邻两个位置点最小距离，这里近似为10米
     */
    public static Double GIS_MIN_DISTANCE_BETWEEN_POINTS = 0.0001;

    /**
     * 判断调头路段的距离，如A-B-C三条路构成U型路段，那么B路段小于GIS_UTURN_DISTANCE米，并且AB、BC是连续左拐或者右拐，确定为调头
     */
    public static Double GIS_UTURN_DISTANCE = 10.0;

    /**
     * 路网表的几何字段名，默认为the_geom，与pgrouting的默认保持一致
     */
    public static String GIS_ROUTING_ROAD_TABLE_GEOM_FIELD_NAME = "the_geom";

    /**
     * 相邻路径合并时，首尾连接点的容差值，单位为度，这里设置为1米
     */
    public static double GIS_MERGE_PATH_TOLERANCE = 0.00001;

    /**
     * 步行路径过短时，导航路径忽略该步行路径，设置忽略步行路径长度的阈值，单位米
     */
    public static double GIS_FOOT_WAY_IGNORE_DISTANCE_IN_METERS = 3;

    /**
     * 导航指南的默认版本号
     */
    public static String TEXT_INSTRUCTION_VERSION = "v5";

    /**
     * 最大速度，默认为1000m/min
     */
    public static Double MAX_SPEED = 1000.0;
}
