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
public class GisKSingleLineMergeFormatter implements GisRouteFormatter<List<String>> {

    @Override
    public List<String> format(List<List<GisRouteRoad>> gisRouteRoadGroups) {
        List<String> result = new ArrayList<>();
        for(List<GisRouteRoad> item:gisRouteRoadGroups){
            List<String> subRouteResult = new ArrayList<>();
            for(Geometry geom:GisRouteMergeLineStringUtil.mergeLineStrings(item)){
                subRouteResult.add(geom.toText());
            }
            if(subRouteResult.size()>1)
                throw new GisRouteProcessorException("GisKSingleLineMergeFormatter format errors, line merge result collection size must be 1. ");
            else
                result.add(subRouteResult.get(0));
        }
        return CollectionUtils.isEmpty(result)?null:result;
    }
}
