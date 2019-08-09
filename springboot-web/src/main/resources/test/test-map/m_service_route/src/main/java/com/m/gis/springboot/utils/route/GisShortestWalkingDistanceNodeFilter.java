package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.utils.route.bo.GisCropRoute;
import com.m.gis.springboot.enums.GisCropRouteEnums;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisLineStringUtil;
import com.m.gis.springboot.po.GisNearestRoad;
import com.m.gis.springboot.po.GisRouteRoad;
import com.vividsolutions.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @Title: GisNodeFilter
 * @Package: com.m.gis.springboot.utils
 * @Description: 根据最短步行距离来计算最近的node节点
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public class GisShortestWalkingDistanceNodeFilter {
    private static final Logger logger = LoggerFactory.getLogger(GisShortestWalkingDistanceNodeFilter.class);

    private GisBarrierCheckUtil gisBarrierCheckUtil;

    public GisShortestWalkingDistanceNodeFilter(List<String> barries){
        gisBarrierCheckUtil = new GisBarrierCheckUtil(barries);
    }

    /***
     * @Description: 换算最短路径的成本
     * @Author: monkjavaer
     * @Data: 21:34 2018/8/2
     * @Param: [roadLength]
     * @Throws
     * @Return java.lang.Double
     */
    private Double calculateCost(Double roadLength){
        return roadLength;
    }


    /***
     * @Description: 给定路段，判断该路段是否通行有效
     * @Author: monkjavaer
     * @Data: 16:36 2018/8/7
     * @Param: [gisRouteRoad]
     * @Throws
     * @Return java.lang.Double
     */
    public boolean isPassable(GisRouteRoad gisRouteRoad){
        //如果cost小于0表示是不通行的
        if(gisRouteRoad.getCost()<0)
            return false;

        LineString lineString = null;
        try {
            lineString = (LineString) GisGeometryFactoryUtil.createGeometryByWKT(gisRouteRoad.getGeom());
            return !gisBarrierCheckUtil.isInBarries(lineString);
        } catch (GisParseGeometryBaseThrowableException e) {
            logger.error(e.toString());
            throw new GisRouteProcessorException("GisNearestRoadNodeFilter isValid function errors, illegal geometry." + gisRouteRoad.getGeom());
        }
    }



    /***
     * @Description: 获取从location位置点/到location位置点步行距离最小的Node节点选择方案
     * @Author: monkjavaer
     * @Data: 14:15 2018/8/9
     * @Param: []
     * @Throws
     * @Return com.m.gis.springboot.utils.route.bo.GisCropRoute
     */
    private GisCropRoute getShortestWalkingDistarnceRoad(GisCoordinate location, List<GisNearestRoad> gisNearestRoads,boolean toLocation){
        GisCropRoute shortestRoute = new GisCropRoute();

        for(GisNearestRoad item:gisNearestRoads){
            if(item.getBridge()||item.getTunnel()){
                //如果路段是桥梁或隧道，不能直接步行上去
                continue;
            }

            //路段AB上的最近点C到目的位置O
            GisCoordinate closestPoint = new GisCoordinate(item.getClosestPointLongitude(),item.getClosestPointLatitude());
            //CO线段或者OC线段
            LineString footWay = toLocation ? GisGeometryFactoryUtil.createLine(Arrays.asList(closestPoint,location)):GisGeometryFactoryUtil.createLine(Arrays.asList(location,closestPoint));

            //如果步行路径CO/OC在barries中
            if(gisBarrierCheckUtil.isInBarries(footWay))
                continue;

            //如果步行路径CO/OC不在barries中
            Double footWayDistanceInMeters = GisLineStringUtil.geographyLength(footWay);
            Double footCost = calculateCost(footWayDistanceInMeters);

            Double minCost = Double.MAX_VALUE;

            //计算从路径两个Node节点到线段上location最近点的路段
            GisRouteRoad sourceRoad = toLocation ? GisCropRouteUtil.cropRouteRoad(item,item.getFraction(),GisCropRouteEnums.SOURCE_LOCATION):GisCropRouteUtil.cropRouteRoad(item,item.getFraction(),GisCropRouteEnums.LOCATION_SOURCE);
            GisRouteRoad targetRoad = toLocation ? GisCropRouteUtil.cropRouteRoad(item,item.getFraction(),GisCropRouteEnums.TARGET_LOCATION):GisCropRouteUtil.cropRouteRoad(item,item.getFraction(),GisCropRouteEnums.LOCATION_TARGET);

            if(isPassable(sourceRoad)&&minCost>sourceRoad.getCost()){
                minCost = sourceRoad.getCost();
            }
            if(isPassable(targetRoad)&&minCost>targetRoad.getCost()){
                minCost = targetRoad.getCost();
            }

            if(minCost<shortestRoute.getCost()){
                //如果有符合条件的Node节点
                //生成步行CO的路段
                GisRouteRoad footWayRoute = new GisRouteRoad();
                footWayRoute.setRouteCost(footCost);
                footWayRoute.setGeom(footWay.toString());
                footWayRoute.setLength(footWayDistanceInMeters);
                footWayRoute.setStartAzimuth(GisLineStringUtil.startAngle(footWay));
                footWayRoute.setEndAzimuth(GisLineStringUtil.endAngle(footWay));
                footWayRoute.setFootWay(true);
                shortestRoute.setFootWay(footWayRoute);
                shortestRoute.setOriginRoad(item);

                if(minCost.equals(sourceRoad.getCost())){
                    //如果选择了AC/CA线段
                    shortestRoute.setCost(sourceRoad.getCost()+footCost);
                    shortestRoute.setFraction(item.getFraction());
                    shortestRoute.setCropWay(sourceRoad);
                    shortestRoute.setCropType(toLocation ? GisCropRouteEnums.SOURCE_LOCATION : GisCropRouteEnums.LOCATION_SOURCE);
                }
                else if(minCost.equals(targetRoad.getCost())){
                    //如果选择了BC/CB线段
                    shortestRoute.setCost(targetRoad.getCost()+footCost);
                    shortestRoute.setFraction(item.getFraction());
                    shortestRoute.setCropWay(targetRoad);
                    shortestRoute.setCropType(toLocation ? GisCropRouteEnums.TARGET_LOCATION : GisCropRouteEnums.LOCATION_TARGET);
                }
                else
                    throw new GisRouteProcessorException("GisRouteProcessorException getClosestNodeToLocation errors, if this happens, code must be some logical errors.");

                //因为从db查询的路径就是按照步行距离排序的，所以只要找到一条符合条件的，则肯定是步行距离最短的路径
                break;
            }
        }

        return (shortestRoute.getCost()==Double.MAX_VALUE) ? null:shortestRoute;
    }

    public GisCropRoute getCropRoute(GisCoordinate location, List<GisNearestRoad> gisNearestRoads, boolean toLocation){
        return getShortestWalkingDistarnceRoad(location,gisNearestRoads,toLocation);
    }
}
