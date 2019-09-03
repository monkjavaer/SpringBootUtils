package com.m.gis.springboot.utils.route.instruction;

import com.mapbox.services.api.directions.v5.models.LegStep;

/**
 * @Title: GisLegStepHandle
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description: 策略模式，根据前后两条路的关系分发处理，生成路径指南
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public abstract class GisLegStepHandle {
    protected GisManeuverTypeEnum maneuverType;

    public GisLegStepHandle(GisManeuverTypeEnum maneuverType) {
        this.maneuverType = maneuverType;
    }

    /***
     * @Description: 根据前后两条路做相应的分发处理
     * @Author: monkjavaer
     * @Data: 20:38 2018/8/28
     * @Param: [request]
     * @Throws
     * @Return void
     */
    public abstract LegStep handle(GisLegStepHandleRequest request);

}
