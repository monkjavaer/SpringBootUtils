package com.m.gis.springboot.utils;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import org.postgis.Point;

/**
 * @Title: PostgisUtil
 * @Package: com.m.gis.springboot.utils
 * @Description: 用于转换Postgis对象
 * @Author: monkjavaer
 * @Data: 2018/6/22
 * @Version: V1.0
 */
public class PostgisUtil {

    /***
     * @Description: 创建点
     * @Author: monkjavaer
     * @Data: 10:27 2018/6/22
     * @Param: [pt]
     * @Throws
     * @Return org.postgis.Point
     */
    public static Point createPoint(GisCoordinate pt){
        return new Point(pt.getLongitude(),pt.getLatitude());
    }

    /***
     * @Description: 转换为GisCoordinate坐标，truncate为true表示截取精度
     * @Author: monkjavaer
     * @Data: 11:25 2018/8/15
     * @Param: [pt, truncate]
     * @Throws
     * @Return com.m.gis.springboot.geo.base.common.GisCoordinate
     */
    public static GisCoordinate fromPoint(Point pt, boolean truncate){ return new GisCoordinate(pt.getX(),pt.getY(),truncate);}

    /***
     * @Description: 转换为GisCoordinate坐标，不截取精度
     * @Author: monkjavaer
     * @Data: 11:25 2018/8/15
     * @Param: [pt]
     * @Throws
     * @Return com.m.gis.springboot.geo.base.common.GisCoordinate
     */
    public static GisCoordinate fromPoint(Point pt){ return new GisCoordinate(pt.getX(),pt.getY());}

}
