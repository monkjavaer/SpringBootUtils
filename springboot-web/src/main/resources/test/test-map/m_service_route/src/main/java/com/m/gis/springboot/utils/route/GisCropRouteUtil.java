package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.common.GisRouteConstants;
import com.m.gis.springboot.enums.GisCropRouteEnums;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisLineStringUtil;
import com.m.gis.springboot.po.GisBaseRoad;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.bo.GisCropRoute;
import com.m.gis.springboot.utils.route.instruction.GisManeuverModifierEnum;
import com.vividsolutions.jts.geom.LineString;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisCropRouteUtil
 * @Package: com.m.gis.springboot.utils
 * @Description: 用于路径裁剪和调整的工具
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public class GisCropRouteUtil {
    private static final Logger logger = LoggerFactory.getLogger(GisCropRouteUtil.class);

    /***
     * @Description: 给定一条路径轨迹，指定start和end，裁剪该轨迹，start和end都是相对于该路径几何线段起点的比例
     * @Author: monkjavaer
     * @Data: 16:26 2018/8/8
     * @Param: [routeRoad, start, end]
     * @Throws
     * @Return com.m.gis.springboot.po.GisRouteRoad
     */
    public static GisRouteRoad subRouteRoad(GisBaseRoad routeRoad, double start, double end){
        GisPreconditions.checkArgument(start>=0&&start<=1&&end>=0&&end<=1,"GisCropRouteUtil subRouteRoad function errors, param start or end must be [0,1].");

        GisRouteRoad gisRouteRoad = new GisRouteRoad();
        LineString lineString = null;
        try {
            lineString  = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(routeRoad.getGeom());
        } catch (GisParseGeometryBaseThrowableException e) {
            throw new GisRouteProcessorException(String.format("GisCropRouteUtil subRouteRoad function errors, illegal wkt {%s}in barries.",routeRoad.getGeom()));
        }
        gisRouteRoad.setId(routeRoad.getId());
        gisRouteRoad.setName(routeRoad.getName());
        gisRouteRoad.setMaxspeed(routeRoad.getMaxspeed());
        gisRouteRoad.setBridge(routeRoad.getBridge());
        gisRouteRoad.setTunnel(routeRoad.getTunnel());
        gisRouteRoad.setDir(routeRoad.getDir());
        gisRouteRoad.setFootWay(false);
        if(start<end){
            gisRouteRoad.setSource(start==0?routeRoad.getSource():Integer.valueOf(-1));
            gisRouteRoad.setTarget(end==1?routeRoad.getTarget():Integer.valueOf(-1));
            gisRouteRoad.setStartNodeId(gisRouteRoad.getSource());

            lineString = GisLineStringUtil.subLineString(lineString,start,end);
            gisRouteRoad.setGeom(lineString.toString());
            gisRouteRoad.setStartAzimuth(GisLineStringUtil.startAngle(lineString));
            gisRouteRoad.setEndAzimuth(GisLineStringUtil.endAngle(lineString));
            gisRouteRoad.setLength(routeRoad.getLength()*(end-start));
            gisRouteRoad.setCost(routeRoad.getCost()<0?routeRoad.getCost():Double.valueOf(routeRoad.getCost()*(end-start)));
            gisRouteRoad.setReverseCost(routeRoad.getReverseCost()<0?routeRoad.getReverseCost():Double.valueOf(routeRoad.getReverseCost()*(end-start)));
            gisRouteRoad.setRouteCost(routeRoad.getCost());
        }
        else if(end<start){
            gisRouteRoad.setSource(start==1?routeRoad.getTarget():Integer.valueOf(-1));
            gisRouteRoad.setTarget(end==0?routeRoad.getSource():Integer.valueOf(-1));
            gisRouteRoad.setStartNodeId(gisRouteRoad.getSource());

            lineString = (LineString)GisLineStringUtil.subLineString(lineString,end,start).reverse();
            gisRouteRoad.setGeom(lineString.toString());
            gisRouteRoad.setStartAzimuth(GisLineStringUtil.startAngle(lineString));
            gisRouteRoad.setEndAzimuth(GisLineStringUtil.endAngle(lineString));
            gisRouteRoad.setLength(routeRoad.getLength()*(start-end));
            gisRouteRoad.setCost(routeRoad.getReverseCost()<0?routeRoad.getReverseCost():Double.valueOf(routeRoad.getReverseCost()*(start-end)));
            gisRouteRoad.setReverseCost(routeRoad.getCost()<0?routeRoad.getCost():Double.valueOf(routeRoad.getCost()*(start-end)));
            gisRouteRoad.setRouteCost(routeRoad.getCost());
        }
        else{
            //处理start和end相等的情况
            if(start==0){
                gisRouteRoad.setSource(routeRoad.getSource());
                gisRouteRoad.setTarget(routeRoad.getSource());
                gisRouteRoad.setStartNodeId(gisRouteRoad.getSource());
            }
            else if(start==1){
                gisRouteRoad.setSource(routeRoad.getTarget());
                gisRouteRoad.setTarget(routeRoad.getTarget());
                gisRouteRoad.setStartNodeId(gisRouteRoad.getSource());
            }
            else{
                gisRouteRoad.setSource(-1);
                gisRouteRoad.setTarget(-1);
                gisRouteRoad.setStartNodeId(-1);
            }
            lineString = GisLineStringUtil.subLineString(lineString,start,end);
            gisRouteRoad.setGeom(lineString.toString());
            gisRouteRoad.setStartAzimuth(0.0);
            gisRouteRoad.setEndAzimuth(0.0);
            gisRouteRoad.setLength(0.0);
            gisRouteRoad.setCost(0.0);
            gisRouteRoad.setReverseCost(0.0);
            gisRouteRoad.setRouteCost(0.0);

        }
        return gisRouteRoad;
    }


    /***
     * @Description: AB线段，A为起点（Source）B为终点（Target），C为AB上的定位点，fraction=AC/AB，startNodeId为实际路径规划的起点（可能为A或者B）
     * @Author: monkjavaer
     * @Data: 15:09 2018/8/7
     * @Param: [routeRoad, fraction, type]
     * @Throws
     * @Return com.m.gis.springboot.po.GisRouteRoad
     */
    public static GisRouteRoad cropRouteRoad(GisBaseRoad routeRoad, double fraction, GisCropRouteEnums type){
        if(type.equals(GisCropRouteEnums.LOCATION_SOURCE)){
            return subRouteRoad(routeRoad,fraction,0);
        }
        else if(type.equals(GisCropRouteEnums.LOCATION_TARGET)) {
            return subRouteRoad(routeRoad,fraction,1);
        }
        else if(type.equals(GisCropRouteEnums.SOURCE_LOCATION)) {
            return subRouteRoad(routeRoad,0,fraction);
        }
        else{
            return subRouteRoad(routeRoad,1,fraction);
        }
    }


    public static GisRouteRoad calculateRouteAzimuth(GisRouteRoad routeRoad){
        //调整边的方向，并计算角度
        if(routeRoad.getStartNodeId().equals(routeRoad.getSource())){
            routeRoad = GisCropRouteUtil.cropRouteRoad(routeRoad,1,GisCropRouteEnums.SOURCE_LOCATION);
        }
        else{
            routeRoad = GisCropRouteUtil.cropRouteRoad(routeRoad,0,GisCropRouteEnums.TARGET_LOCATION);
        }
        return routeRoad;
    }


    /***
     * @Description: 给定一个GisCropRoute对象和一条AB边，如果GisCropRoute对象的CropWay与AB边是同一条边，则根据CropWay裁剪AB边为实际的路径，否则追加CropWay到路径中
     * @Author: monkjavaer
     * @Data: 17:23 2018/8/7
     * @Param: [cropRoute, routeRoad]
     * @Throws
     * @Return com.m.gis.springboot.po.GisRouteRoad
     */
    public static GisRouteRoad cropOrAppendRoute(GisCropRoute cropRoute, GisRouteRoad routeRoad){
        if(cropRoute.getCropWay().getLength()<=0){
            return null;
        }

        //如果cropWay不包含在计算结果中，则补充该cropWay
        if(routeRoad.getId().equals(cropRoute.getCropWay().getId())==false){
            return cropRoute.getCropWay();
        }

        if(GisCropRouteEnums.LOCATION_SOURCE.equals(cropRoute.getCropType()))
            return cropRouteRoad(routeRoad,cropRoute.getFraction(),GisCropRouteEnums.LOCATION_TARGET);
        else if(GisCropRouteEnums.LOCATION_TARGET.equals(cropRoute.getCropType()))
            return cropRouteRoad(routeRoad,cropRoute.getFraction(),GisCropRouteEnums.LOCATION_SOURCE);
        else if(GisCropRouteEnums.SOURCE_LOCATION.equals(cropRoute.getCropType()))
            return cropRouteRoad(routeRoad,cropRoute.getFraction(),GisCropRouteEnums.TARGET_LOCATION);
        else
            return cropRouteRoad(routeRoad,cropRoute.getFraction(),GisCropRouteEnums.SOURCE_LOCATION);
    }


    /***
     * @Description: 判断两条路能否合并
     * @Author: monkjavaer
     * @Data: 17:39 2018/8/29
     * @Param: [before, after]
     * @Throws
     * @Return boolean
     */
    public static boolean canMerge(GisRouteRoad before,GisRouteRoad after){
        //如果有一条路径为null，可以合并
        if(before==null||after==null)
            return true;

        GisManeuverModifierEnum modifier = GisManeuverModifierEnum.getModifier(before.getEndAzimuth(),after.getStartAzimuth());

        //两条路名字不一致时，不能合并
        if(StringUtils.isNotBlank(before.getName())&&StringUtils.isNotBlank(after.getName())){
            if(false==before.getName().equalsIgnoreCase(after.getName()))
                return false;
        }
        //两条路仅有一条路有名字，不能合并
        else if((StringUtils.isNotBlank(before.getName())&&StringUtils.isBlank(after.getName()))
                ||(StringUtils.isBlank(before.getName())&&StringUtils.isNotBlank(after.getName())))
            return false;

        //不是直行、偏向的两条路段不能合并，如直角拐弯、急转弯、调头
        if(false==(modifier.equals(GisManeuverModifierEnum.STRAIGHT)||modifier.equals(GisManeuverModifierEnum.SLIGHT_LEFT)||modifier.equals(GisManeuverModifierEnum.SLIGHT_RIGHT)))
            return false;
        //两条路通行方向不一致，不能合并
        if(before.getCost()*after.getCost()<0)
            return false;
        //最大限速不一致，不能合并
        if(before.getMaxspeed()!=after.getMaxspeed())
            return false;
        //两条线的不在端点处重合，不能合并
        try {
            LineString beforeLine  = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(before.getGeom());
            LineString afterLine  = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(after.getGeom());
            GisCoordinate beforePoint = new GisCoordinate(beforeLine.getEndPoint().getX(),beforeLine.getEndPoint().getY());
            GisCoordinate afterPoint = new GisCoordinate(afterLine.getStartPoint().getX(),afterLine.getStartPoint().getY());
            if(false == beforePoint.equals(afterPoint))
                return false;
        } catch (GisParseGeometryBaseThrowableException e) {
            throw new GisRouteProcessorException(String.format("GisCropRouteUtil canMerge function errors, illegal wkt {%s} in GisRouteRoad."));
        }
        return true;
    }


    /***
     * @Description: 将mergeList中的路径合并为一条路径，注意需要保证mergeList中的路径都是可合并的
     * @Author: monkjavaer
     * @Data: 18:12 2018/8/29
     * @Param: [mergeList]
     * @Throws
     * @Return com.m.gis.springboot.po.GisRouteRoad
     */
    public static GisRouteRoad mergeRoute(List<GisRouteRoad> mergeList){
        if(CollectionUtils.isEmpty(mergeList)){
            return null;
        }

        GisRouteRoad gisRouteRoad = new GisRouteRoad();
        List<LineString> lineStrings = new ArrayList<>();
        Double totalLength = 0.0;
        Double totalCost = 0.0;
        for(GisRouteRoad item:mergeList){
            LineString lineString = null;
            try {
                lineString  = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(item.getGeom());
            } catch (GisParseGeometryBaseThrowableException e) {
                throw new GisRouteProcessorException(String.format("GisCropRouteUtil mergeRoute function errors, illegal wkt {%s} in GisRouteRoad.",item.getGeom()));
            }
            lineStrings.add(lineString);
            totalLength += (item.getLength()==null)?0:item.getLength();
            totalCost += (item.getCost()==null)?0:item.getCost();
        }

        List<LineString> mergeLines = GisLineStringUtil.lineMerge(lineStrings,GisRouteConstants.GIS_MERGE_PATH_TOLERANCE);
        if(CollectionUtils.isEmpty(mergeLines)||mergeLines.size()>1){
            //路径合并不应该出现多条线段的结果，如果有，说明中间是有断开的
            throw new GisRouteProcessorException(String.format("GisCropRouteUtil mergeRoute function errors, merge result is empty or more than one path."));
        }

        LineString mergeLine = (LineString)mergeLines.toArray()[0];

        gisRouteRoad.setRouteSegementId(mergeList.get(0).getRouteSegementId());
        gisRouteRoad.setName(mergeList.get(0).getName());
        gisRouteRoad.setMaxspeed(mergeList.get(0).getMaxspeed());
        gisRouteRoad.setBridge(mergeList.get(0).getBridge());
        gisRouteRoad.setTunnel(mergeList.get(0).getTunnel());
        gisRouteRoad.setDir(mergeList.get(0).getDir());
        gisRouteRoad.setSource(mergeList.get(0).getSource());
        gisRouteRoad.setTarget(mergeList.get(mergeList.size()-1).getTarget());
        gisRouteRoad.setStartNodeId(mergeList.get(0).getSource());
        gisRouteRoad.setGeom(mergeLine.toString());
        gisRouteRoad.setStartAzimuth(GisLineStringUtil.startAngle(mergeLine));
        gisRouteRoad.setEndAzimuth(GisLineStringUtil.endAngle(mergeLine));
        gisRouteRoad.setLength(totalLength);
        gisRouteRoad.setCost(totalCost);
        gisRouteRoad.setRouteCost(totalCost);

        return gisRouteRoad;
    }

    /***
     * @Description: 将GisRouteRoad数组中的路径合并简化
     * @Author: monkjavaer
     * @Data: 17:14 2018/8/29
     * @Param: [mergeList]
     * @Throws
     * @Return com.m.gis.springboot.po.GisRouteRoad
     */
    public static List<GisRouteRoad> mergeRouteList(List<GisRouteRoad> mergeList){
        if(logger.isDebugEnabled())
            logger.debug("begins to merge route list.");

        GisPreconditions.checkArgument(CollectionUtils.isNotEmpty(mergeList),"GisCropRouteUtil mergeRoute function errors, param mergeList must not be null.");

        List<GisRouteRoad> gisMergeRoads = new ArrayList<>();
        List<GisRouteRoad> needMergedRoads = new ArrayList<>();
        GisRouteRoad pre = null;
        for(GisRouteRoad item:mergeList){
            if(item.isFootWay()){
                //第一条和最后一条footway不合并
                //如果是最后一条footway，则需要先把之前的路径合并了
                if(CollectionUtils.isNotEmpty(needMergedRoads)){
                    GisRouteRoad mergeRoad = mergeRoute(needMergedRoads);
                    if (mergeRoad != null){
                        gisMergeRoads.add(mergeRoad);
                        if(logger.isDebugEnabled())
                            logger.debug(String.format("<==route {%s} after merged with name {%s}, %s",mergeRoad.getId(),mergeRoad.getName(),mergeRoad.getGeom()));
                    }
                    needMergedRoads.clear();
                }

                gisMergeRoads.add(item);
                if(logger.isDebugEnabled())
                    logger.debug(String.format("<==route foot way, %s",item.getGeom()));
                continue;
            }
            if(false==canMerge(pre,item)) {
                if(logger.isDebugEnabled()){
                    for(GisRouteRoad mItem:needMergedRoads){
                        logger.debug(String.format("==>route {%s} need to merge, %s",mItem.getId(),mItem.getGeom()));
                    }
                }
                //处理上一批需要合并的路径
                GisRouteRoad mergeRoad = mergeRoute(needMergedRoads);
                if(logger.isDebugEnabled())
                    logger.debug(String.format("<==route {%s} after merged with name {%s}, %s",mergeRoad.getId(),mergeRoad.getName(),mergeRoad.getGeom()));

                if (mergeRoad != null)
                    gisMergeRoads.add(mergeRoad);
                needMergedRoads.clear();
            }
            needMergedRoads.add(item);
            pre = item;
        }
        if(CollectionUtils.isNotEmpty(needMergedRoads)){
            if(logger.isDebugEnabled()){
                for(GisRouteRoad mItem:needMergedRoads){
                    logger.debug(String.format("==>route {%s} need to merge, %s",mItem.getId(),mItem.getGeom()));
                }
            }
            GisRouteRoad mergeRoad = mergeRoute(needMergedRoads);

            if (mergeRoad != null){
                gisMergeRoads.add(mergeRoad);
                if(logger.isDebugEnabled())
                    logger.debug(String.format("<==route {%s} after merged with name {%s}, %s",mergeRoad.getId(),mergeRoad.getName(),mergeRoad.getGeom()));
            }
        }
        return gisMergeRoads;
    }
}
