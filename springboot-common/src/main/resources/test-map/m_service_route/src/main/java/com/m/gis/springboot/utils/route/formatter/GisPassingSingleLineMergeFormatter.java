package com.m.gis.springboot.utils.route.formatter;

import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.GisRouteMergeLineStringUtil;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisPassingSingleLineMergeFormatter
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public class GisPassingSingleLineMergeFormatter implements GisRouteFormatter<String> {

    @Override
    public String format(List<List<GisRouteRoad>> gisRouteRoadGroups) {
        List<String> result = new ArrayList<>();
        List<GisRouteRoad> allRoads = new ArrayList<>();
        for(List<GisRouteRoad> item:gisRouteRoadGroups){
            for(GisRouteRoad subItem:item)
                allRoads.add(subItem);
        }
        for(Geometry geom:GisRouteMergeLineStringUtil.mergeLineStrings(allRoads)){
            result.add(geom.toText());
        }

        if(CollectionUtils.isEmpty(result))
            return null;
        else if(result.size()>1)
            throw new GisRouteProcessorException("GisPassingSingleLineMergeFormatter format errors, line merge result collection size must be 1. ");
        else
            return result.get(0);
    }
}
