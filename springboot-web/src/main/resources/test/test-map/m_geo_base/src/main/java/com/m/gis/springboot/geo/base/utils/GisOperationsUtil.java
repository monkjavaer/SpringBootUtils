package com.m.gis.springboot.geo.base.utils;

import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.common.JTSFactoryFinder;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

import java.util.List;

/**
 * @Title: GisOperationsUtil
 * @Package: com.m.gis.springboot.geo.base.utils
 * @Description: 提供几何操作的工具
 * @Author: monkjavaer
 * @Data: 2018/7/31
 * @Version: V1.0
 */
public class GisOperationsUtil {

    /***
     * @Description: 提供几何图形的合并操作
     * @Author: monkjavaer
     * @Data: 16:07 2018/7/31
     * @Param: [geoms]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry union(List<Geometry> geoms){
        GisPreconditions.checkNotNull(geoms,"GisOperationsUtil union function errors, geoms param is null.");
        Geometry geom = null;
        boolean first = true;
        for(int i = 0;i<geoms.size();i++){
            if(i==0)
                geom = geoms.get(i);
            geom = geom.union(geoms.get(i));
        }

        return geom;
    }

    /***
     * @Description: 生成多边形外边界的轮廓图（可以消除polygon内部的洞）
     * @Author: monkjavaer
     * @Data: 16:07 2018/7/31
     * @Param: [geoms]
     * @Throws
     * @Return com.vividsolutions.jts.geom.Geometry
     */
    public static Geometry exteriorRingPolygon(Polygon polygon){
        GisPreconditions.checkNotNull(polygon,"GisOperationsUtil exteriorRingPolygon function errors, polygon param is null.");
        Geometry geom = JTSFactoryFinder.getDefaultGeometryFactory().createPolygon(polygon.getExteriorRing().getCoordinates());
        return geom;
    }

}
