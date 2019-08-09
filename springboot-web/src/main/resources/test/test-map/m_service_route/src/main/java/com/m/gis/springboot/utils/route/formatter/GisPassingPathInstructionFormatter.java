package com.m.gis.springboot.utils.route.formatter;

import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.instruction.GisLegStepHandleClient;
import com.m.gis.springboot.vo.GisPathVO;
import com.m.gis.springboot.vo.GisShortestPathVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Title: GisPassingSingleLineMergeFormatter
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public class GisPassingPathInstructionFormatter implements GisRouteFormatter<GisShortestPathVO> {
    private Locale locale;
    public GisPassingPathInstructionFormatter(Locale locale) {
        this.locale = locale;
    }

    @Override
    public GisShortestPathVO format(List<List<GisRouteRoad>> gisRouteRoadGroups) {
        List<GisPathVO> routes = new ArrayList<>();

        for(int i=0;i<gisRouteRoadGroups.size();i++){
            List<GisRouteRoad> gisRouteRoads = gisRouteRoadGroups.get(i);
            GisPathVO gisPathVO = GisLegStepHandleClient.handle(i+1,gisRouteRoads,locale);
            routes.add(gisPathVO);
        }
        return new GisShortestPathVO(routes);
    }
}
