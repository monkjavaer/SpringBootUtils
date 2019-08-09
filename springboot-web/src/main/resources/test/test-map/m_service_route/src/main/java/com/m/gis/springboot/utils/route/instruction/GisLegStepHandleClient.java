package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.common.GisRouteConstants;
import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.exception.GisRouteLegStepHandleException;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.GisCropRouteUtil;
import com.m.gis.springboot.vo.GisPathStepVO;
import com.m.gis.springboot.vo.GisPathVO;
import com.mapbox.services.api.directions.v5.models.LegStep;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Title: GisLegStepHandle
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description:
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public class GisLegStepHandleClient {
    private static final Logger logger = LoggerFactory.getLogger(GisLegStepHandleClient.class);

    private static Map<GisManeuverTypeEnum, GisLegStepHandle> handleMaps = new HashMap<>();

    private static Map<String, TextInstructions> textInstructionsMaps = new HashMap<>();

    static {
        //初始化预置语种的导航词条
        textInstructionsMaps.put("zh-CN", new TextInstructions("zh-Hans", GisRouteConstants.TEXT_INSTRUCTION_VERSION));
        textInstructionsMaps.put("en-US", new TextInstructions("en", GisRouteConstants.TEXT_INSTRUCTION_VERSION));
        textInstructionsMaps.put("es-ES", new TextInstructions("es", GisRouteConstants.TEXT_INSTRUCTION_VERSION));
        textInstructionsMaps.put("es-BO", new TextInstructions("es", GisRouteConstants.TEXT_INSTRUCTION_VERSION));
        textInstructionsMaps.put("ru-RU", new TextInstructions("ru", GisRouteConstants.TEXT_INSTRUCTION_VERSION));

        //初始化各处理器
        handleMaps.put(GisManeuverTypeEnum.DEPART, new GisDepartLegStepHandle());
        handleMaps.put(GisManeuverTypeEnum.ARRIVE, new GisArriveLegStepHandle());
        handleMaps.put(GisManeuverTypeEnum.TURN, new GisTurnLegStepHandle());
        handleMaps.put(GisManeuverTypeEnum.END_OF_ROAD, new GisEndOfRoadLegStepHandle());
    }

    /***
     * @Description: 处理路径，生成路径指南的结果，index表示第几条路径，用于到达时生成{您已到达第index个目的地}这样的描述
     * @Author: monkjavaer
     * @Data: 14:30 2018/9/5
     * @Param: [index, gisRouteRoads]
     * @Throws
     * @Return com.m.gis.springboot.vo.GisPathVO
     */
    public static GisPathVO handle(Integer index, List<GisRouteRoad> gisRouteRoads, Locale locale) {
        if (logger.isDebugEnabled())
            logger.debug(String.format("begin to handle %sth route to create instruction.", index));

        if (CollectionUtils.isEmpty(gisRouteRoads)) {
            if (logger.isDebugEnabled())
                logger.debug(String.format("%sth route roads need to be handled is empty or null.", index));
            return null;
        }

        TextInstructions textInstructions = textInstructionsMaps.containsKey(locale.toLanguageTag()) ? textInstructionsMaps.get(locale.toLanguageTag()) : textInstructionsMaps.get("ru-RU");
        if (logger.isDebugEnabled())
            logger.debug(String.format("choose %s instruction.", locale.toLanguageTag()));
        String l = locale.getLanguage();
        String t = locale.toLanguageTag();

        List<GisPathStepVO> steps = new ArrayList<>();
        //先合并所有能合并的路径
        List<GisRouteRoad> gisMergeRouteRoads = GisCropRouteUtil.mergeRouteList(gisRouteRoads);
        if (logger.isDebugEnabled())
            logger.debug(String.format("merge %s routes, remains %s routes after merge.", gisRouteRoads.size(), gisMergeRouteRoads.size()));

        double totalDistance = 0.0;
        //记录路径总耗时
        double time = 0.0;
        LegStep previousLegStep = null;
        for (int i = gisMergeRouteRoads.size() - 1; i >= 0; i--) {
            if (i == gisMergeRouteRoads.size() - 1) {
                continue;
            }
            GisRouteRoad gisRouteRoad = gisMergeRouteRoads.get(i);
            //如果是补充的步行路段，且距离非常小（例如小于3米），不显示该路段
            if (gisRouteRoad.isFootWay() && gisRouteRoad.getLength() < GisRouteConstants.GIS_FOOT_WAY_IGNORE_DISTANCE_IN_METERS) {
                if (logger.isDebugEnabled()) {
                    logger.debug("foot way is too short, so removed.");
                }
                gisMergeRouteRoads.remove(gisRouteRoad);
            }
        }

        for (int i = 0; i < gisMergeRouteRoads.size(); i++) {
            GisLegStepHandleRequest request = new GisLegStepHandleRequest();
            request.setPrevious(i == 0 ? null : gisMergeRouteRoads.get(i - 1));
            request.setCurrent(gisMergeRouteRoads.get(i));
            request.setNext(i + 1 > gisMergeRouteRoads.size() - 1 ? null : gisMergeRouteRoads.get(i + 1));
            request.setPreviousLegStep(previousLegStep);

            LegStep legStep = dispatch(request);
            GisPathStepVO vo = new GisPathStepVO(i, gisMergeRouteRoads.get(i), legStep.getManeuver().getModifier(), textInstructions.compile(legStep, index));
            if (i == gisMergeRouteRoads.size() - 1) {
                if (gisMergeRouteRoads.get(i).getLength()<GisRouteConstants.GIS_FOOT_WAY_IGNORE_DISTANCE_IN_METERS){
                    vo.setGeom("");
                }
            }
            steps.add(vo);
            if (logger.isDebugEnabled())
                logger.debug(String.format("%s route after dispatch handle result : %s", i, JsonUtils.toJSONString(vo)));
            totalDistance += gisMergeRouteRoads.get(i).getLength();
            //路段最大速度为0或null时，默认为1000m/min
            Double maxSpeed = GisRouteConstants.MAX_SPEED;
            if (gisMergeRouteRoads.get(i).getMaxspeed() != null) {
                if (gisMergeRouteRoads.get(i).getMaxspeed() != 0) {
                    Double speed = Double.valueOf(gisMergeRouteRoads.get(i).getMaxspeed());
                    //将数据库的速度km/h装换为m/min
                    maxSpeed = (speed / 60) * GisRouteConstants.MAX_SPEED;
                }
            }
            time += gisMergeRouteRoads.get(i).getLength() / maxSpeed;
            previousLegStep = legStep;
        }
        return new GisPathVO(totalDistance, steps, time);
    }

    public static LegStep dispatch(GisLegStepHandleRequest request) {
        if (request.getCurrent() == null)
            throw new GisRouteLegStepHandleException("dispatch errors, request current is null.");

        LegStep legStep = null;

        //如果没有上一条路径，且有当前路径，那么肯定是出发路段
        if (request.getPrevious() == null && request.getCurrent() != null) {
            if (logger.isDebugEnabled())
                logger.debug(String.format("dispatch to %s handle, %s", GisManeuverTypeEnum.DEPART.getName(), request.getCurrent().getGeom()));
            return handleMaps.get(GisManeuverTypeEnum.DEPART).handle(request);
        }
        //如果后继路径为空，且有当前路径，那么肯定是到达路段
        else if (request.getNext() == null && request.getCurrent() != null) {
            if (logger.isDebugEnabled())
                logger.debug(String.format("dispatch to %s handle, %s", GisManeuverTypeEnum.ARRIVE.getName(), request.getCurrent().getGeom()));
            return handleMaps.get(GisManeuverTypeEnum.ARRIVE).handle(request);
        }
        //如果当前路径与上一路径为left/right/sharp left/sharp right/uturn，那么为转弯
        else if (GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.LEFT)
                || GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.RIGHT)
                || GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.SHARP_LEFT)
                || GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.SHARP_RIGHT)
                || GisManeuverModifierEnum.getModifier(request.getPrevious().getEndAzimuth(), request.getCurrent().getStartAzimuth()).equals(GisManeuverModifierEnum.UTURN)) {
            if (logger.isDebugEnabled())
                logger.debug(String.format("dispatch to %s handle, %s", GisManeuverTypeEnum.TURN.getName(), request.getCurrent().getGeom()));
            legStep = handleMaps.get(GisManeuverTypeEnum.TURN).handle(request);
        } else {
            if (logger.isDebugEnabled())
                logger.debug(String.format("dispatch to %s handle, %s", GisManeuverTypeEnum.END_OF_ROAD.getName(), request.getCurrent().getGeom()));
            legStep = handleMaps.get(GisManeuverTypeEnum.END_OF_ROAD).handle(request);
        }

        if (request.getPreviousLegStep() != null
                && StringUtils.isNotBlank(legStep.getManeuver().getModifier())
                && legStep.getManeuver().getModifier().equalsIgnoreCase(request.getPreviousLegStep().getManeuver().getModifier())) {
            if (logger.isDebugEnabled())
                logger.debug(String.format("change to dispatch to %s handle, %s", GisManeuverTypeEnum.CONTINUE.getName(), request.getCurrent().getGeom()));
            //如果与上一个legstep的moidfier相同，则type类型改为continue
            legStep.getManeuver().setType(GisManeuverTypeEnum.CONTINUE.getName());
        }

        return legStep;
    }

}
