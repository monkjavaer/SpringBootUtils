package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.geo.base.common.GisBaseConstants;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import java.math.BigDecimal;

/**
 * @Title: GisFormatUtil
 * @Package: springboot.geo.base.utils
 * @Description: 用于gis坐标数据处理的工具类
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class GisFormatUtil {
    /*0.00001度近似等于1米*/
    public final static int doubleLength = GisBaseConstants.DEFAULT_LONLAT_PRECISION;

    /**
     * @Description: used for truncate double with default settings. default value is 5.
     * @Author: monkjavaer
     * @Data: 18:17 2018/6/7
     * @Param: [coord]
     * @Throws
     * @Return double
     */
    public static double formatCoordinate(double coord) {
        return formatCoordinate(coord, doubleLength);
    }

    /**
     * @Description: used for truncate double with specified value.
     * @Author: monkjavaer
     * @Data: 18:16 2018/6/7
     * @Param: [coord, precision]
     * @Throws GisIllegalParameterCommonException
     * @Return double
     */
    public static double formatCoordinate(double coord, int precision) {
        if (precision < 0)
            throw new GisIllegalParameterCommonException("formatCoordinate precision is 0 or negative");

        long factor = 1;
        for (int i = 0; i < precision; i++)
            factor *= 10;
        return ((long) (coord * factor)) / (double) factor;
    }

    /**
     * @Description: used for truncate BigDecimal with default settings. default value is 5.
     * @Author: monkjavaer
     * @Data: 18:20 2018/6/7
     * @Param: [coord]
     * @Throws
     * @Return java.math.BigDecimal
     */
    public static BigDecimal formatCoordinate(BigDecimal coord) {
        return formatCoordinate(coord, doubleLength);
    }

    /**
     * @Description: used for truncate BigDecimal with specified value.
     * @Author: monkjavaer
     * @Data: 18:18 2018/6/7
     * @Param: [coord, precision]
     * @Throws
     * @Return java.math.BigDecimal
     */
    public static BigDecimal formatCoordinate(BigDecimal coord, int precision) {
        if (precision < 0)
            throw new GisIllegalParameterCommonException("formatCoordinate precision is 0 or negative");

        return new BigDecimal(coord.doubleValue()).setScale(precision, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @Description: used for truncate String with default settings. default value is 5.
     * @Author: monkjavaer
     * @Data: 18:20 2018/6/7
     * @Param: [coord]
     * @Throws
     * @Return java.lang.String
     */
    public static String formatCoordinate(String coord) {
        return formatCoordinate(coord, doubleLength);
    }

    /**
     * @Description: used for truncate String with specified value.
     * @Author: monkjavaer
     * @Data: 18:18 2018/6/7
     * @Param: [coord, precision]
     * @Throws
     * @Return java.lang.String
     */
    public static String formatCoordinate(String coord, int precision) {
        if (precision < 0)
            throw new GisIllegalParameterCommonException("formatCoordinate precision is 0 or negative");

        return new Double(formatCoordinate(Double.valueOf(coord), precision)).toString();
    }

    /**
     * @Description: used for truncate Geometry with default settings. default value is 5.
     * @Author: monkjavaer
     * @Data: 18:21 2018/6/7
     * @Param: [geo]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry formatGeometry(Geometry geo) {
        return formatGeometry(geo, doubleLength);
    }

    /**
     * @Description: used for truncate geometry with specified value.
     * @Author: monkjavaer
     * @Data: 18:18 2018/6/7
     * @Param: [geo, precision]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry formatGeometry(Geometry geo, int precision) {
        if (geo == null || geo.isEmpty())
            return geo;

        Coordinate[] coords = geo.getCoordinates();
        for (Coordinate item : coords) {
            item.x = formatCoordinate(item.x, precision);
            item.y = formatCoordinate(item.y, precision);
        }
        return geo;
    }

    /**
     * @Description: used for truncate wkt String with specified value.
     * @Author: monkjavaer
     * @Data: 18:19 2018/6/7
     * @Param: [wkt, precision]
     * @Throws
     * @Return java.lang.String
     */
/*	public static String formatWKT(String wkt, int precision) throws GisNullGeometryBaseException, GisEmptyGeometryBaseException, ParseException{
		Geometry geo = WktFormatConvertUtil.toGeometry(wkt);
		Geometry formatGeo = formatGeometry(geo);
		return WktFormatConvertUtil.fromGeometry(formatGeo);
	}

	public static String formatWKT(String wkt) throws GisNullGeometryBaseException, GisEmptyGeometryBaseException, ParseException{
		return formatWKT(wkt,doubleLength);
	}*/
}
