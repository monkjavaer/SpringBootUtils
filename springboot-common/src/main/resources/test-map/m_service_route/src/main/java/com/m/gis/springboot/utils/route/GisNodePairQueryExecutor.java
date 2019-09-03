package com.m.gis.springboot.utils.route;

import com.m.gis.springboot.exception.GisNoNearestRoadServiceException;
import com.m.gis.springboot.exception.GisNoRouteRoadResultThrowableException;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.po.GisNearestRoad;
import com.m.gis.springboot.utils.route.bo.GisCropRoute;
import com.m.gis.springboot.utils.route.bo.GisNodePair;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GisNodePairCreator
 * @Package: com.m.gis.springboot.utils.route
 * @Description: 起止点查询器，根据用户输入的一系列查询坐标点，转换为数据库可查询的路网节点，并按照起止点两两生成NodePair对象
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public class GisNodePairQueryExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GisNodePairQueryExecutor.class);

    private GisShortestWalkingDistanceNodeFilter shortestWalkingDistanceNodeFilter;

    private Map<Integer,List<GisNearestRoad>> locationsMaps = new HashMap<>();

    private List<GisCoordinate> locations;

    /**
     * 记录查询网络节点对参数及其结果
     */
    private List<GisNodePair> gisNodePairs;

    public GisNodePairQueryExecutor(List<GisCoordinate> locations, List<GisNearestRoad> gisNearestRoads, List<String> barries) throws GisNoRouteRoadResultThrowableException {
        //检查位置点，如不符合特定规则，则抛出异常
        if(CollectionUtils.isEmpty(locations)||locations.size()<2)
            throw new GisRouteProcessorException("locations size must be more than 1.");
        this.locations = locations;

        //对查询的最短路径结果按照位置点归类
        for(GisNearestRoad item:gisNearestRoads){
            if(locationsMaps.containsKey(item.getQueryId())){
                locationsMaps.get(item.getQueryId()).add(item);
            }
            else{
                locationsMaps.put(item.getQueryId(),new ArrayList<>());
                locationsMaps.get(item.getQueryId()).add(item);
            }
        }

        if(locationsMaps.keySet().size()!=locations.size()){
            //如果有点在允许容差内没有最近路段，则抛出异常
            logger.debug("some location is too far away from road. throw GisNoNearestRoadServiceException.");
            throw new GisNoNearestRoadServiceException();
        }

        //装配节点筛选工具
        shortestWalkingDistanceNodeFilter = new GisShortestWalkingDistanceNodeFilter(barries);

        //根据定位点生成附着的路网节点对
        createAttachedNodesPair();

        if(CollectionUtils.isEmpty(gisNodePairs))
            throw new GisRouteProcessorException("gisNodePairs is null or empty after initialized.");
    }

    /**
     * 根据id获取节点对
     * @param id
     * @return
     */
    public GisNodePair getNodePairById(int id){
        for(GisNodePair item:gisNodePairs){
            if(item.getId().equals(id))
                return item;
        }
        throw new GisRouteProcessorException(String.format("id {%s} is not found in node pair list.",id));
    }

    /**
     * 获取所有的附着路网节点对
     * @return
     */
    public List<GisNodePair> getAllAttachedNodePairs() {
        return gisNodePairs;
    }

    /**
     * 获取需要数据库查询的附着路网节点对，排除起点终点附着在同一个路网节点的情况
     * @return
     */
    public List<GisNodePair> getQueryGisAttachedNodePairs(){
        List<GisNodePair> queryGisNodePairs = new ArrayList<>();

        for(GisNodePair item: gisNodePairs){
            //如果有起点终点附着在同一个路网节点的情况，数据库查询结果会返回一个环形的路径结果，所以这里过滤掉
            if(item.isQuery()){
                queryGisNodePairs.add(item);
            }
        }
        return queryGisNodePairs;
    }


    /**
     * 获取定位点附着的路网节点的坐标
     * @return
     */
    public List<GisCoordinate> getAttachedNodesPosition(){
        List<GisCoordinate> gisCoordinates = new ArrayList<>();
        for(int i = 0; i< gisNodePairs.size(); i++){
            gisCoordinates.add(gisNodePairs.get(i).getStartAttachedNodePosition());
            if(i== gisNodePairs.size()-1)
                gisCoordinates.add(gisNodePairs.get(i).getEndAttachedNodePosition());
        }
        return gisCoordinates;
    }

    /***
     * @Description: 获取起始、终止点对应的Node节点对
     * @Author: monkjavaer
     * @Data: 14:41 2018/8/9
     * @Param: []
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.utils.route.bo.GisNodePair>
     */
    public void createAttachedNodesPair() throws GisNoRouteRoadResultThrowableException {
        gisNodePairs =  new ArrayList<>();

        GisCropRoute lastCropRoute = null;
        GisCropRoute currentCropRoute = null;
        int id = 0;
        for(Map.Entry<Integer,List<GisNearestRoad>> item :locationsMaps.entrySet()){
            List<GisNearestRoad> queryRoads = item.getValue();

            if(item.getKey()==0){
                //获取起点的最近node
                currentCropRoute = shortestWalkingDistanceNodeFilter.getCropRoute(this.locations.get(item.getKey()),queryRoads,false);
                if(currentCropRoute==null)
                    throw new GisNoRouteRoadResultThrowableException();
                lastCropRoute = currentCropRoute;
            }
            else if(item.getKey().equals(locations.size()-1)){
                //获取终点的最近node
                currentCropRoute = shortestWalkingDistanceNodeFilter.getCropRoute(this.locations.get(item.getKey()),queryRoads,true);
                if(currentCropRoute==null)
                    throw new GisNoRouteRoadResultThrowableException();

                GisNodePair newNodePair = new GisNodePair(id++,lastCropRoute,currentCropRoute);
                gisNodePairs.add(newNodePair);

                if(logger.isDebugEnabled()){
                    logger.debug(String.format("route start node {id: %s, position: %s} ---> destination node {id: %s, position: %s}",
                            newNodePair.getStart(),
                            newNodePair.getStartAttachedNodePosition().toString(),
                            newNodePair.getEnd(),
                            newNodePair.getEndAttachedNodePosition().toString()));
                }
            }
            else{
                currentCropRoute = shortestWalkingDistanceNodeFilter.getCropRoute(this.locations.get(item.getKey()),queryRoads,true);
                if(currentCropRoute==null)
                    throw new GisNoRouteRoadResultThrowableException();

                GisNodePair newNodePair = new GisNodePair(id++,lastCropRoute,currentCropRoute);
                gisNodePairs.add(newNodePair);

                if(logger.isDebugEnabled()){
                    logger.debug(String.format("route start node {id: %s, position: %s} ---> destination node {id: %s, position: %s}",
                            newNodePair.getStart(),
                            newNodePair.getStartAttachedNodePosition().toString(),
                            newNodePair.getEnd(),
                            newNodePair.getEndAttachedNodePosition().toString()));
                }
                lastCropRoute = shortestWalkingDistanceNodeFilter.getCropRoute(this.locations.get(item.getKey()),queryRoads,false);
                if(lastCropRoute==null)
                    throw new GisNoRouteRoadResultThrowableException();
            }
        }
    }

}
