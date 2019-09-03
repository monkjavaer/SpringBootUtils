package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.common.utils.JsonUtils;
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
public class GisDepartLegStepHandle extends GisLegStepHandle{
    private static final Logger logger = LoggerFactory.getLogger(GisDepartLegStepHandle.class);

    public GisDepartLegStepHandle() {
        super(GisManeuverTypeEnum.DEPART);
    }

    /***
     * @Description: 根据前后两条路做相应的分发处理
     * @Author: monkjavaer
     * @Data: 20:38 2018/8/28
     * @Param: [before, after]
     * @Throws
     * @Return void
     */
    public LegStep handle(GisLegStepHandleRequest request){
        request.getLegStep().getManeuver().setType(maneuverType.getName());
        request.getLegStep().getManeuver().setBearingAfter(request.getCurrent().getStartAzimuth());
        if(request.getNext()!=null&&StringUtils.isNotBlank(request.getNext().getName()))
            request.getLegStep().setName(request.getNext().getName());

        if(logger.isDebugEnabled())
            logger.debug(String.format("LegStep handle {%s} : {%s}",maneuverType.getName(), JsonUtils.toJSONString(request.getLegStep())));
        return request.getLegStep();

    }

}
