package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.common.utils.JsonUtils;
import com.mapbox.services.api.directions.v5.models.LegStep;
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
public class GisArriveLegStepHandle extends GisLegStepHandle {
    private static final Logger logger = LoggerFactory.getLogger(GisArriveLegStepHandle.class);

    public GisArriveLegStepHandle() {
        super(GisManeuverTypeEnum.ARRIVE);
    }

    /***
     * @Description: 根据前后两条路做相应的分发处理
     * @Author: monkjavaer
     * @Data: 20:38 2018/8/28
     * @Param: [before, after]
     * @Throws
     * @Return void
     */
    public LegStep handle(GisLegStepHandleRequest request) {
        request.getLegStep().getManeuver().setType(maneuverType.getName());

        //用于路段长度四舍五入
        double param = 0.5;
        //当前路段长度为0
        if (request.getCurrent().getLength() < param) {
            request.getLegStep().getManeuver().setModifier(GisManeuverModifierEnum.DEFAULT.getName());
        } else {
            GisManeuverModifierEnum modifier = GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth());
            request.getLegStep().getManeuver().setModifier(modifier.getName());
        }

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("LegStep handle {%s} : {%s}", maneuverType.getName(), JsonUtils.toJSONString(request.getLegStep())));
        }
        return request.getLegStep();
    }

}
