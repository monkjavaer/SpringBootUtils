package com.m.gis.springboot.geo.base.common;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.common.utils.Preconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.vividsolutions.jts.geom.Geometry;

/**
 * @Title: Preconditions
 * @Package: springboot.common.utils
 * @Description: extend from Preconditions, add some gis preconditions
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class GisPreconditions extends Preconditions {
    public GisPreconditions() {
        super();
    }

    /**
     * @Description: check longitude or latitude is legal or not.
     * @Author: monkjavaer
     * @Data: 18:49 2018/6/7
     * @Param: [longitude, latitude]
     * @Throws
     * @Return void
     */
    public static void checkLonLat(Double longitude, Double latitude) {
        checkNotNull(longitude, "longitude is null.");
        checkNotNull(latitude, "latitude is null.");

        if (!checkLon(longitude))
            checkArgument(false, "longitude {%s} is illegal.", longitude);

        if (!checkLat(latitude))
            checkArgument(false, "latitude {%s} is illegal.", latitude);
    }

    public static boolean checkLon(Double longitude){
        return (longitude < GisBaseConstants.MINLONGITUDE || longitude > GisBaseConstants.MAXLONGITUDE) ? false : true;
    }

    public static boolean checkLat(Double latitude){
        return (latitude < GisBaseConstants.MINLATITUDE || latitude > GisBaseConstants.MAXLATITUDE) ? false : true;
    }

    /**
     * @Description: 检查几何图形是否合法
     * @Author: monkjavaer
     * @Data: 18:18 2018/6/10
     * @Param: [geom, errorMessage]
     * @Throws
     * @Return void
     */
    public static void checkGeometry(Geometry geom, Object errorMessage) {
        if (geom == null || geom.isEmpty() || !geom.isValid())
            checkArgument(false, errorMessage);
    }

    /**
     * @Description: 检查几何图形是否合法
     * @Author: monkjavaer
     * @Data: 18:17 2018/6/10
     * @Param: [geom]
     * @Throws
     * @Return void
     */
    public static void checkGeometry(Geometry geom) {
        checkGeometry(geom, "geometry is illegal.");
    }

    /***
     * @Description: 检查wkt格式的图形是否合法
     * @Author: monkjavaer
     * @Data: 17:52 2018/7/27
     * @Param: [wkt, errorMessage]
     * @Throws
     * @Return void
     */
    public static void checkGeometry(String wkt,Object errorMessage){
        try {
            Geometry geom = GisGeometryFactoryUtil.createGeometryByWKT(wkt);
            checkGeometry(geom,errorMessage);
        } catch (GisParseGeometryBaseThrowableException e) {
            throw new GisIllegalParameterCommonException(String.valueOf(errorMessage));
        }
    }

    /***
     * @Description: 检查wkt格式的图形是否合法
     * @Author: monkjavaer
     * @Data: 17:55 2018/7/27
     * @Param: [wkt]
     * @Throws 
     * @Return void
     */ 
    public static void checkGeometry(String wkt) {
        checkGeometry(wkt, "geometry wkt is illegal.");
    }

}