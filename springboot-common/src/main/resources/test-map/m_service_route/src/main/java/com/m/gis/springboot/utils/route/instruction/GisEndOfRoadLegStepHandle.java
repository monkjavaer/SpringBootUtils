package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.exception.GisRouteLegStepHandleException;
import com.mapbox.services.api.directions.v5.models.LegStep;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisLegStepHandle
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description:
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public class GisEndOfRoadLegStepHandle extends GisLegStepHandle{
    private static final Logger logger = LoggerFactory.getLogger(GisEndOfRoadLegStepHandle.class);

    public GisEndOfRoadLegStepHandle() {
        super(GisManeuverTypeEnum.END_OF_ROAD);
    }

    /***
     * @Description: 当前路与之前的路不是同一条路
     * @Author: monkjavaer
     * @Data: 20:38 2018/8/28
     * @Param: [before, after]
     * @Throws
     * @Return void
     */
    public LegStep handle(GisLegStepHandleRequest request){
        if(request.getPrevious()==null||request.getCurrent()==null||request.getPrevious().getEndAzimuth()==null||request.getCurrent().getStartAzimuth()==null)
            throw new GisRouteLegStepHandleException("GisEndOfRoadLegStepHandle handle error, road or azimuth of previous or current can not be null.");

        request.getLegStep().getManeuver().setType(maneuverType.getName());
        GisManeuverModifierEnum modifier = GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(),request.getCurrent().getStartAzimuth());
        request.getLegStep().getManeuver().setModifier(modifier.getName());

        //如果当前路的名字不为空，应为：{modifier}行驶，上{way_name}
        if(StringUtils.isNotBlank(request.getCurrent().getName()))
            request.getLegStep().setName(request.getCurrent().getName());

        //如果当前路名字为空，但下一条路名字不为空，应为：{modifier}行驶，前往{destination}
        else if(request.getNext()!=null&&StringUtils.isNotBlank(request.getNext().getName()))
            request.getLegStep().setDestinations(request.getNext().getName());

        if(logger.isDebugEnabled())
            logger.debug(String.format("LegStep handle {%s} : {%s}",maneuverType.getName(), JsonUtils.toJSONString(request.getLegStep())));
        return request.getLegStep();

    }

}
