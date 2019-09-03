package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.GisBaseConstants;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisUtilsBaseException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Title: GisGeodesyUtil
 * @Package: springboot.geo.base.utils
 * @Description: 用于Gis空间距离的量测工具
 * @Author: monkjavaer
 * @Data: 2018/6/8
 * @Version: V1.0
 */
public class GisGeodesyUtil {
    private static Logger logger = LoggerFactory.getLogger(GisGeodesyUtil.class);

    private static GeodeticCalculator getGeodeticCalculator(GisCoordinate start, GisCoordinate end, CoordinateReferenceSystem crs) {
        GisPreconditions.checkNotNull(crs);

        GeodeticCalculator gc = new GeodeticCalculator(crs);
        GisPreconditions.checkNotNull(gc);

        try {
            gc.setStartingPosition(JTS.toDirectPosition(new Coordinate(start.getLongitude(), start.getLatitude()), crs));
            gc.setDestinationPosition(JTS.toDirectPosition(new Coordinate(end.getLongitude(), end.getLatitude()), crs));
        } catch (TransformException e) {
            logger.error(e.getMessage());
            throw new GisUtilsBaseException(e.getMessage());
        }
        return gc;
    }

    /**
     * @Description: 给定两个点，返回两个点的距离（单位米）
     * @Author: monkjavaer
     * @Data: 17:46 2018/6/8
     * @Param: [start, end]
     * @Throws
     * @Return double
     */
    public static double distance(GisCoordinate start, GisCoordinate end, CoordinateReferenceSystem crs) {
        GeodeticCalculator gc = getGeodeticCalculator(start, end, crs);
        return gc.getOrthodromicDistance();
    }

    /**
     * @Description: 给定两个点，返回两个点的距离（单位米）,默认坐标系为WGS84
     * @Author: monkjavaer
     * @Data: 11:13 2018/6/9
     * @Param: [start, end]
     * @Throws
     * @Return double
     */
    public static double distance(GisCoordinate start, GisCoordinate end) {
        return distance(start, end, GisCRSUtil.getDefaultCRS());
    }

    /**
     * @Description: 给定一个点A，和以A为起点的方向（0-360）和距离（单位米），计算另一个点的位置
     * @Author: monkjavaer
     * @Data: 17:37 2018/6/8
     * @Param: [point, bearingInDegrees, distanceInMeters]
     * @Throws
     * @Return void
     */
    public static GisCoordinate moveInDirection(GisCoordinate point, double bearingInDegrees, double distanceInMeters, CoordinateReferenceSystem crs) {
        GisPreconditions.checkNotNull(crs);
        GisPreconditions.checkArgument(distanceInMeters>0,"parameter distanceInMeters must be positive.");

        GeodeticCalculator gc = new GeodeticCalculator(crs);
        GisPreconditions.checkNotNull(gc);

        gc.setStartingGeographicPoint(point.getLongitude(), point.getLatitude());
        gc.setDirection(bearingInDegrees /* azimuth */, distanceInMeters /* distance */);
        Point2D dest = gc.getDestinationGeographicPoint();
        return new GisCoordinate(dest.getX(), dest.getY());
    }

    /**
     * @Description: 给定一个点A，和以A为起点的方向（0-360）和距离（单位米），计算另一个点的位置，默认WGS84坐标系
     * @Author: monkjavaer
     * @Data: 11:33 2018/6/9
     * @Param: [point, bearingInDegrees, distanceInMeters]
     * @Throws
     * @Return GisCoordinate
     */
    public static GisCoordinate moveInDirection(GisCoordinate point, double bearingInDegrees, double distanceInMeters) {
        return moveInDirection(point, bearingInDegrees, distanceInMeters, GisCRSUtil.getDefaultCRS());
    }

    /**
     * @Description: 给定一个基准点A，计算B点相对于A点的夹角，正北为0度，正西为-90，正东90，正南-180
     * @Author: monkjavaer
     * @Data: 11:31 2018/6/9
     * @Param: [start, end, crs]
     * @Throws
     * @Return double
     */
    public static double angle(GisCoordinate start, GisCoordinate end, CoordinateReferenceSystem crs) {
        GeodeticCalculator gc = getGeodeticCalculator(start, end, crs);
        return gc.getAzimuth();
    }

    /**
     * @Description: 给定一个基准点A，计算B点相对于A点的夹角，默认WGS84坐标系
     * @Author: monkjavaer
     * @Data: 11:34 2018/6/9
     * @Param: [start, end]
     * @Throws
     * @Return double
     */
    public static double angle(GisCoordinate start, GisCoordinate end) {
        return angle(start, end, GisCRSUtil.getDefaultCRS());
    }


    /***
     * @Description: 根据圆心经纬度和半径生成圆，半径单位为米，truncate表示是否截断坐标精度
     * @Author: monkjavaer
     * @Data: 17:20 2018/6/14
     * @Param: [longitude, latitude, distanceMeters, truncate]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createPointBuffer(double longitude, double latitude, double distanceMeters, boolean truncate) {
        final int SIDES = GisBaseConstants.CIRCLE_SIDES;//圆上面的点个数
        List<GisCoordinate> ptList = new ArrayList<GisCoordinate>();

        GeodeticCalculator gc = new GeodeticCalculator(GisCRSUtil.getDefaultCRS());
        gc.setStartingGeographicPoint(longitude, latitude);

        for (int i = 0; i < SIDES; i++) {
            gc.setDirection(i * 360.0 / SIDES, distanceMeters);
            Point2D destGeoPoint = gc.getDestinationGeographicPoint();
            ptList.add(new GisCoordinate(destGeoPoint.getX(), destGeoPoint.getY(), truncate));
        }
        ptList.add(ptList.get(0));
        return GisGeometryFactoryUtil.createPolygon(ptList, truncate);
    }


    /***
     * @Description: 构建线的缓冲面,暂不开放使用
     * @Author: monkjavaer
     * @Data: 18:48 2018/6/14
     * @Param: [inline, distanceInMeters, truncate, crs]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry createGeodeticLineBuffer(Geometry inline, double distanceInMeters, boolean truncate, CoordinateReferenceSystem crs)
    {
        GisPreconditions.checkNotNull(crs);
        GisPreconditions.checkArgument(distanceInMeters>0,"parameter distanceInMeters must be positive.");
        GisPreconditions.checkGeometry(inline);

        GeodeticCalculator calc = new GeodeticCalculator(crs);
        GisPreconditions.checkNotNull(calc);

        Coordinate[] subsat_points = inline.getCoordinates();

        List<GisCoordinate> hull_right = new ArrayList<GisCoordinate>();
        List<GisCoordinate> hull_left = new ArrayList<GisCoordinate>();
        double angle=0;
        for (int i = 0; i < subsat_points.length-1; i++)
        {
            calc.setStartingGeographicPoint(subsat_points[i].x,subsat_points[i].y);
            calc.setDestinationGeographicPoint(subsat_points[i+1].x,subsat_points[i+1].y);
            angle = calc.getAzimuth();

            if (angle<=-90 ){angle = angle+180;}
            if (angle>=90 ){angle = angle-180;}
            calc.setStartingGeographicPoint(subsat_points[i].x,subsat_points[i].y);
            calc.setDirection(angle+90,distanceInMeters);
            Point2D dest1 = calc.getDestinationGeographicPoint();
            hull_right.add(new GisCoordinate(dest1.getX(),dest1.getY()));
            calc.setDirection(angle-90, distanceInMeters);
            Point2D dest2 = calc.getDestinationGeographicPoint();
            hull_left.add(new GisCoordinate(dest2.getX(),dest2.getY()));
        }

        calc.setStartingGeographicPoint(subsat_points[subsat_points.length-1].x,subsat_points[subsat_points.length-1].y);
        calc.setDirection(angle+90,distanceInMeters);
        Point2D dest1 = calc.getDestinationGeographicPoint();
        hull_right.add(new GisCoordinate(dest1.getX(),dest1.getY()));
        calc.setDirection(angle-90, distanceInMeters);
        Point2D dest2 = calc.getDestinationGeographicPoint();
        hull_left.add(new GisCoordinate(dest2.getX(),dest2.getY()));

        Collections.reverse(hull_left);

        ArrayList<GisCoordinate> hull = new ArrayList<>();
        hull.addAll(hull_right);
        hull.addAll(hull_left);
        hull.add(hull_right.get(0)); //close ring

        return GisGeometryFactoryUtil.createPolygon(hull,truncate);
    }


}
