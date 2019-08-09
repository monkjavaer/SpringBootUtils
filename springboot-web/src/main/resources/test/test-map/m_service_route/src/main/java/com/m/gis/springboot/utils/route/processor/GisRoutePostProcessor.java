package com.m.gis.springboot.utils.route.processor;

import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.GisCropRouteUtil;
import com.m.gis.springboot.utils.route.GisNodePairQueryExecutor;
import com.m.gis.springboot.utils.route.bo.GisCropRoute;
import com.m.gis.springboot.utils.route.bo.GisNodePair;
import com.m.gis.springboot.utils.route.formatter.GisRouteFormatter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisRoutePostProcessor
 * @Package: com.m.gis.springboot.utils.route
 * @Description: 模版模式，定义查询后的路径结果处理
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public abstract class GisRoutePostProcessor<T> {
    private static final Logger logger = LoggerFactory.getLogger(GisRoutePostProcessor.class);

    /**
     * 路径查询器
     */
    private GisNodePairQueryExecutor executor;

    /**
     * 结果格式输出器
     */
    private GisRouteFormatter<T> gisRouteFormatter;

    /**
     * 处理后的路径结果集
     */
    private List<List<GisRouteRoad>> gisRouteRoadGroupsAfterProcess;


    public GisRoutePostProcessor(GisNodePairQueryExecutor executor, GisRouteFormatter<T> gisRouteFormatter){
        this.executor = executor;
        this.gisRouteFormatter = gisRouteFormatter;
    }

    public GisNodePairQueryExecutor getExecutor() {
        return executor;
    }

    public List<List<GisRouteRoad>> getGisRouteRoadGroupsAfterProcess() {
        return gisRouteRoadGroupsAfterProcess;
    }

    public void setGisRouteRoadGroupsAfterProcess(List<List<GisRouteRoad>> gisRouteRoadGroupsAfterProcess) {
        this.gisRouteRoadGroupsAfterProcess = gisRouteRoadGroupsAfterProcess;
    }

    /***
     * @Description: 首尾端的路段处理
     * @Author: monkjavaer
     * @Data: 10:46 2018/8/10
     * @Param: []
     * @Throws
     * @Return void
     */
    protected abstract void startAndDestinationProcessor();

    /***
     * @Description: 路径结果处理的模版方法
     * @Author: monkjavaer
     * @Data: 17:08 2018/8/9
     * @Param: [gisRouteRoads]
     * @Throws
     * @Return java.util.List<T>
     */
    public T process(){
        //首尾端的路径处理
        startAndDestinationProcessor();
        //格式化输出
        return gisRouteFormatter.format(gisRouteRoadGroupsAfterProcess);
    }


    /***
     * @Description: 对路径结果集根据routeid进行分组
     * @Author: monkjavaer
     * @Data: 10:42 2018/8/10
     * @Param: [gisRouteRoads]
     * @Throws
     * @Return java.util.List<java.util.List<com.m.gis.springboot.po.GisRouteRoad>>
     */
    public static List<List<GisRouteRoad>> groupProcess(List<GisRouteRoad> gisRouteRoads){
        //对路径结果分组
        //因为对于每一对起始终止点，db查询的路径是有序的
        int lastRouteId = -1;
        List<List<GisRouteRoad>> gisRouteRoadGroups = new ArrayList<>();

        if(CollectionUtils.isEmpty(gisRouteRoads))
            return gisRouteRoadGroups;

        //根据routeId对结果集分组，不同组对应不同的起始终止点NodePair
        for(GisRouteRoad item:gisRouteRoads){
            if(item.getRouteId()!=lastRouteId){
                //对应新的路径结果集
                lastRouteId = item.getRouteId();
                gisRouteRoadGroups.add(new ArrayList<>());
                gisRouteRoadGroups.get(gisRouteRoadGroups.size()-1).add(item);
            }
            else{
                gisRouteRoadGroups.get(gisRouteRoadGroups.size()-1).add(item);
            }
        }

        return gisRouteRoadGroups;
    }


    /***
     * @Description: 处理起始和终止点的路段，按导航方向调整路段方向，合并同一条路段
     * @Author: monkjavaer
     * @Data: 17:28 2018/8/9
     * @Param: [gisNodePairs, resultRouteRoads]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisRouteRoad>
     */
    protected List<GisRouteRoad> startAndDestinationRouteCrop(GisNodePair gisNodePairs, List<GisRouteRoad> resultRouteRoads){
        long startTime = System.currentTimeMillis();
        if(logger.isDebugEnabled())
            logger.debug(String.format("startAndDestinationRouteCrop begins to process, total {%s} route roads need to be process.",resultRouteRoads.size()));

        List<GisRouteRoad> gisRouteRoads = new ArrayList<>();
        GisCropRoute startClosestRoute = gisNodePairs.getStartCropRoute();
        GisCropRoute endClosestRoute = gisNodePairs.getEndCropRoute();

        if (CollectionUtils.isEmpty(resultRouteRoads)) {
            if(gisNodePairs.isOneAttachedNode()){
                if(logger.isDebugEnabled())
                    logger.debug("start and end node shares one attached node, so returns route roads that are created manually.");
                return gisNodePairs.getOneAttachedNodeProcessRouteList();
            }

            if(gisNodePairs.isOnewayOrderThrough()){
                if(logger.isDebugEnabled())
                    logger.debug("start and end node pass through one way orderly, so returns route roads that are created manually.");
                return gisNodePairs.getOnewayOrderThroughProcessRouteList();
            }

            //如果路网节点间无路径结果
            if (gisNodePairs.getStart().equals(gisNodePairs.getEnd()) == false) {
                if (logger.isDebugEnabled())
                    logger.debug("no route roads to be proceed, so returns empty route roads collections.");
                return resultRouteRoads;
            }
        }

        GisRouteRoad lastRouteRoad = null;
        boolean start = false;
        for(GisRouteRoad item:resultRouteRoads){
            if(logger.isDebugEnabled())
                logger.debug(String.format("==> %s origin route :%s",item.getRouteId(),JsonUtils.toJSONString(item)));
            if(start==false){
                //开始处理第一条边和上一条routeId路径的终点边
                if(logger.isDebugEnabled())
                    logger.debug(item.getRouteId() + " route begins to process...");
                start = true;
                //假设起始Node点为A点，O为实际起点，AB为O的最近路段，C为AB上到O的最近距离点，添加步行路段OC
                if(startClosestRoute.getFootWay().getLength()>0){
                    gisRouteRoads.add(startClosestRoute.getFootWay());
                    if(logger.isDebugEnabled())
                        logger.debug("add foot way to start location. ++ route id " + startClosestRoute.getFootWay().getId() + "," + startClosestRoute.getFootWay().getGeom());
                }

                //修剪路段CB或者CA
                GisRouteRoad cropWay = GisCropRouteUtil.cropOrAppendRoute(startClosestRoute,item);

                if(cropWay!=null&&cropWay.getLength()>0){
                    gisRouteRoads.add(cropWay);
                    if(logger.isDebugEnabled())
                        logger.debug("crop way from start is appended. ++ route id " + cropWay.getId() + "," + cropWay.getGeom());
                    //如果不是同一条路段，则除了补充的cutWay路段，还需要添加当前的item路段
                    if(cropWay.getId().equals(item.getId())==false){
                        item = GisCropRouteUtil.calculateRouteAzimuth(item);
                        gisRouteRoads.add(item);
                        if(logger.isDebugEnabled())
                            logger.debug("first way is not the same with crop way, so be appended. ++ route id " + item.getId() + "," + item.getGeom());
                    }
                }
                else{
                    //调整边的方向，并计算角度
                    item = GisCropRouteUtil.calculateRouteAzimuth(item);
                    gisRouteRoads.add(item);
                    if(logger.isDebugEnabled())
                        logger.debug("first way is not the same with crop way, so be appended. ++ route id " + item.getId() + "," + item.getGeom());
                }
            }
            else{
                //整理中间边
                //调整边的方向，计算方向角
                GisRouteRoad adjustRoads = GisCropRouteUtil.calculateRouteAzimuth(item);
                if(adjustRoads.getLength()>0){
                    gisRouteRoads.add(adjustRoads);
                    if(logger.isDebugEnabled())
                        logger.debug("middle way is appended. ++ route id " + adjustRoads.getId() + "," + adjustRoads.getGeom());
                }
            }
            lastRouteRoad = item;
        }

        //更新最后一条边
        if(resultRouteRoads.size()==1&&endClosestRoute.getCropWay().getId().equals(lastRouteRoad.getId())){
            //如果路径只有一条边，并且该边与终点的裁剪边是一条边，意味着该边需要删除并在终点裁剪一遍
            GisRouteRoad routeRoad = gisRouteRoads.remove(gisRouteRoads.size()-1);
            double startFraction = 0;
            double endFraction = (routeRoad.getLength()-endClosestRoute.getCropWay().getLength())/routeRoad.getLength();

            GisRouteRoad singleRouteRoad = GisCropRouteUtil.subRouteRoad(routeRoad,startFraction,endFraction);
            if(singleRouteRoad!=null&&singleRouteRoad.getLength()>0){
                gisRouteRoads.add(singleRouteRoad);
                if(logger.isDebugEnabled())
                    logger.debug("only one road in this route, so remove and crop. ++ route id " + singleRouteRoad.getId() + "," + singleRouteRoad.getGeom());
            }
        }
        else{
            GisRouteRoad cropWay = GisCropRouteUtil.cropOrAppendRoute(endClosestRoute,lastRouteRoad);
            if(cropWay!=null&&cropWay.getLength()>0){
                if(cropWay.getId().equals(lastRouteRoad.getId())){
                    //最后一条边与cutWay是同一条边，移除该边
                    GisRouteRoad duplicateRouteRoad = gisRouteRoads.remove(gisRouteRoads.size()-1);
                    if(logger.isDebugEnabled())
                        logger.debug("last way is the same with crop way, so be removed. -- route id " + duplicateRouteRoad.getId() + "," + duplicateRouteRoad.getGeom());
                }
                gisRouteRoads.add(cropWay);
                if(logger.isDebugEnabled())
                    logger.debug("crop way to destination is appended. ++ route id " + cropWay.getId() + "," + cropWay.getGeom());
            }
        }

        //添加步行路段CO
        if(endClosestRoute.getFootWay().getLength()>0){
            gisRouteRoads.add(endClosestRoute.getFootWay());
            if(logger.isDebugEnabled())
                logger.debug("add foot way to destination location. ++ route id " + endClosestRoute.getFootWay().getId() + "," + endClosestRoute.getFootWay().getGeom());
        }

        if(logger.isDebugEnabled())
            logger.debug(String.format("finished shortest path post handle. route size {%s}, total cost {%s} ms.",gisRouteRoads.size(),System.currentTimeMillis()-startTime));
        return gisRouteRoads;
    }




}
