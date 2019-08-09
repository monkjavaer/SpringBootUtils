package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.common.GisRouteConstants;
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
public class GisTurnLegStepHandle extends GisLegStepHandle{
    private static final Logger logger = LoggerFactory.getLogger(GisTurnLegStepHandle.class);

    public GisTurnLegStepHandle() {
        super(GisManeuverTypeEnum.TURN);
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
        if(request.getPrevious()==null||request.getCurrent()==null||request.getNext()==null)
            throw new GisRouteLegStepHandleException("GisTurnLegStepHandle handle error, previous or current or next can not be null.");

        if(request.getPrevious().getEndAzimuth()==null
                ||request.getCurrent().getStartAzimuth()==null
                ||request.getCurrent().getEndAzimuth()==null
                ||request.getNext().getStartAzimuth()==null)
            throw new GisRouteLegStepHandleException("GisTurnLegStepHandle handle error, azimuth of previous or current or next can not be null.");

        request.getLegStep().getManeuver().setType(maneuverType.getName());
        GisManeuverModifierEnum modifier = GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(),request.getCurrent().getStartAzimuth());
        request.getLegStep().getManeuver().setModifier(modifier.getName());

        if(GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(),request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.STRAIGHT)
                ||GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(),request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.SLIGHT_RIGHT)
                ||GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(),request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.SLIGHT_LEFT))
            throw new GisRouteLegStepHandleException("GisTurnLegStepHandle handle error, road direction must be left or right or sharp left or sharp right or uturn.");

        //如果当前路的名字不为空，应为：{modifier}行驶，上{way_name}
        if(StringUtils.isNotBlank(request.getCurrent().getName()))
            request.getLegStep().setName(request.getCurrent().getName());

            //如果当前路名字为空，但下一条路名字不为空，应为：{modifier}行驶，前往{destination}
        else if(request.getNext()!=null&&StringUtils.isNotBlank(request.getNext().getName()))
            request.getLegStep().setDestinations(request.getNext().getName());

        //如果previous/current/next三条路构成了U型转弯，current长度小于50米，且previous、next不是步行路，修改为掉头
        if(request.getCurrent().getLength()<GisRouteConstants.GIS_UTURN_DISTANCE
                &&request.getPrevious().isFootWay()==false
                &&request.getNext().isFootWay()==false){

            GisManeuverModifierEnum nextModifer = GisManeuverModifierEnum.getModifier(request.getCurrent().getEndAzimuth(),request.getNext().getStartAzimuth());

            if((modifier.equals(GisManeuverModifierEnum.SHARP_LEFT)||modifier.equals(GisManeuverModifierEnum.LEFT))
                    &&(nextModifer.equals(GisManeuverModifierEnum.SHARP_LEFT)||nextModifer.equals(GisManeuverModifierEnum.LEFT)))
                request.getLegStep().getManeuver().setModifier(GisManeuverModifierEnum.UTURN.getName());

            if((modifier.equals(GisManeuverModifierEnum.SHARP_RIGHT)||modifier.equals(GisManeuverModifierEnum.RIGHT))
                    &&(nextModifer.equals(GisManeuverModifierEnum.SHARP_RIGHT)||nextModifer.equals(GisManeuverModifierEnum.RIGHT)))
                request.getLegStep().getManeuver().setModifier(GisManeuverModifierEnum.UTURN.getName());
        }

        if(logger.isDebugEnabled())
            logger.debug(String.format("LegStep handle {%s} : {%s}",maneuverType.getName(), JsonUtils.toJSONString(request.getLegStep())));
        return request.getLegStep();
    }

}
