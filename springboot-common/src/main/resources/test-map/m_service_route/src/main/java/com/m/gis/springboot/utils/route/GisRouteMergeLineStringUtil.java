package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.common.GisRouteConstants;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisLineStringUtil;
import com.m.gis.springboot.po.GisRouteRoad;
import com.vividsolutions.jts.geom.LineString;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisRouteMergeLineStringUtil
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public class GisRouteMergeLineStringUtil {
    /***
     * @Description: 将相互连接的路径规划结果合并为一条线段（可能合并后存在多条合并结果的情况）
     * @Author: monkjavaer
     * @Data: 10:55 2018/8/1
     * @Param: [gisRouteRoads]
     * @Throws
     * @Return java.util.List<com.vividsolutions.jts.geom.LineString>
     */
    public static List<LineString> mergeLineStrings(List<GisRouteRoad> gisRouteRoads){
        if(CollectionUtils.isEmpty(gisRouteRoads)){
            throw new GisRouteProcessorException("mergeLineStrings function errors, gisRouteRoads can not be null or empty.");
        }

        List<LineString> paths = new ArrayList<>();
        for(GisRouteRoad item:gisRouteRoads){
            try {
                paths.add((LineString) GisGeometryFactoryUtil.createGeometryByWKT(item.getGeom()));
            } catch (GisParseGeometryBaseThrowableException e) {
                throw new GisRouteProcessorException("mergeLineStrings function errors, route roads geom can not be parsed as a valid geometry, please check your road datas in database.");
            }
        }

        List<LineString> merges = GisLineStringUtil.lineMerge(paths,GisRouteConstants.GIS_MERGE_PATH_TOLERANCE);
        if(CollectionUtils.isEmpty(merges)){
            throw new GisRouteProcessorException("mergeLineStrings function errors, route roads merge result be empty.");
        }

        return merges;
    }

}
