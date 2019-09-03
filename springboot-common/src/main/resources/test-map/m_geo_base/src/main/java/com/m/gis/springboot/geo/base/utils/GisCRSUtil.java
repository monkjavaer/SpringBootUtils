package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.GisBaseConstants;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisCRSUtil
 * @Package: com.m.gis.springboot.geo.base.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/14
 * @Version: V1.0
 */
public class GisCRSUtil {
    static Logger logger = LoggerFactory.getLogger(GisCRSUtil.class);
    private static CoordinateReferenceSystem defaultCrs;
    private static CoordinateReferenceSystem projection;
    private static MathTransform toTransform;
    private static MathTransform fromTransform;
    /**
     * 默认坐标系
     */
    static {
        try {
            defaultCrs = CRS.decode(GisBaseConstants.DEFAULT_CRS,true);
            projection = CRS.decode(GisBaseConstants.DEFAULT_PROJECTON_CRS,true);
            toTransform = CRS.findMathTransform(GisCRSUtil.getDefaultCRS(), GisCRSUtil.getProjection());
            fromTransform = CRS.findMathTransform(GisCRSUtil.getProjection(), GisCRSUtil.getDefaultCRS());
        } catch (FactoryException e) {
            logger.error(String.format("CoordinateReferenceSystem {%s} or {%s} can not be found, Please recheck your configuration file.", GisBaseConstants.DEFAULT_CRS,GisBaseConstants.DEFAULT_PROJECTON_CRS));
        }
        if(defaultCrs == null)
            logger.error(String.format("CoordinateReferenceSystem {%s} can not be found, Please recheck your configuration file.", GisBaseConstants.DEFAULT_CRS));
        if(projection == null)
            logger.error(String.format("CoordinateReferenceSystem {%s} can not be found, Please recheck your configuration file.", GisBaseConstants.DEFAULT_PROJECTON_CRS));
        if(toTransform == null)
            logger.error(String.format("CoordinateReferenceSystem transform from {%s} to {%s} can not be found, Please recheck your configuration file.", GisBaseConstants.DEFAULT_CRS,GisBaseConstants.DEFAULT_PROJECTON_CRS));
        if(fromTransform == null)
            logger.error(String.format("CoordinateReferenceSystem transform from {%s} to {%s} can not be found, Please recheck your configuration file.", GisBaseConstants.DEFAULT_PROJECTON_CRS,GisBaseConstants.DEFAULT_CRS));

        logger.info("------------------------------------------------------------------------------------------------------------------------------------");
        logger.info("CoordinateReferenceSystem has been configured successfully.");
        logger.info(String.format("Default CoordinateReferenceSystem is {%s}, projection coordinateReferenceSystem is {%s}.", GisBaseConstants.DEFAULT_CRS,GisBaseConstants.DEFAULT_PROJECTON_CRS));
        logger.info("------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static CoordinateReferenceSystem getDefaultCRS(){
        return defaultCrs;
    }

    public static CoordinateReferenceSystem getProjection() {
        return projection;
    }

    /***
     * @Description: 变换为投影坐标系
     * @Author: monkjavaer
     * @Data: 17:51 2018/6/14
     * @Param: [geom]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry toProjection(Geometry geom) throws TransformException {
        return JTS.transform(geom, toTransform);
    }

    /***
     * @Description: 变换为地理坐标系
     * @Author: monkjavaer
     * @Data: 17:53 2018/6/14
     * @Param: [geom]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry fromProjection(Geometry geom) throws TransformException {
        return JTS.transform(geom, fromTransform);
    }
}
