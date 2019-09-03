package com.m.gis.springboot.utils.route.processor;

import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.GisNodePairQueryExecutor;
import com.m.gis.springboot.utils.route.bo.GisNodePair;
import com.m.gis.springboot.utils.route.formatter.GisRouteFormatter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GisSingleLineMergeRoutePostProcessor
 * @Package: com.m.gis.springboot.utils.route
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/9
 * @Version: V1.0
 */
public class GisPassingRoutePostProcessor<T> extends GisRoutePostProcessor<T> {
    private static final Logger logger = LoggerFactory.getLogger(GisPassingRoutePostProcessor.class);

    private Map<Integer,List<GisRouteRoad>> queryMap;

    public GisPassingRoutePostProcessor(List<List<GisRouteRoad>> gisRouteRoadGroups, GisNodePairQueryExecutor executor, GisRouteFormatter<T> gisRouteFormatter){
        super(executor,gisRouteFormatter);

        List<GisNodePair> gisNodePairs = executor.getAllAttachedNodePairs();
        queryMap = new LinkedHashMap<>();

        //将查询节点对与数据库的查询路径结果关联起来，用于后续处理
        for(int i=0,j=0;i<gisNodePairs.size();i++){
            GisNodePair gisNodePair = gisNodePairs.get(i);
            if(gisNodePair.isQuery()==false){
                //如果是不需要从数据库查询的情况，这里直接设置一个空的结果，在GisRoutePostProcessor的startAndDestinationRouteCrop处理时，会对空结果进行处理
                queryMap.put(gisNodePair.getId(),new ArrayList<>());
            }
            else{
                if(CollectionUtils.isEmpty(gisRouteRoadGroups)){
                    String message = String.format("some node pair don't have shortest paths, so throw exception. node pair: {id:{%s}, position:{%s}} --> {id:{%s}, position:{%s}}",
                            gisNodePair.getStart(),
                            gisNodePair.getStartAttachedNodePosition(),
                            gisNodePair.getEnd(),
                            gisNodePair.getEndAttachedNodePosition());
                    if(logger.isDebugEnabled())
                        logger.debug(message);
                    throw new GisRouteProcessorException(message);
                }

                Integer routeId = gisRouteRoadGroups.get(j).get(0).getRouteId();
                if(false==routeId.equals(gisNodePair.getId())){
                    String message = String.format("node pair id {%s} is not match with that of route id {%s} from db.}",gisNodePair.getId(),routeId);
                    if(logger.isDebugEnabled())
                        logger.debug(message);
                    throw new GisRouteProcessorException(message);
                }
                queryMap.put(routeId,gisRouteRoadGroups.get(j++));
            }
        }
    }

    /***
     * @Description: 重载首尾端的路段处理方法
     * @Author: monkjavaer
     * @Data: 10:46 2018/8/10
     * @Param: []
     * @Throws
     * @Return void
     */
    @Override
    protected void startAndDestinationProcessor() {
        //处理首尾两端的路径
        List<List<GisRouteRoad>> gisRouteRoadAfterCropGroups = new ArrayList<>();

        //对节点对进行首尾两端的路径处理
        for(Map.Entry<Integer,List<GisRouteRoad>> entry:queryMap.entrySet()){
            GisNodePair gisNodePair = getExecutor().getNodePairById(entry.getKey());
            List<GisRouteRoad> routesAfterProcess = startAndDestinationRouteCrop(gisNodePair,entry.getValue());
            gisRouteRoadAfterCropGroups.add(routesAfterProcess);
        }

        //保存添加了首尾路段的预处理结果
        setGisRouteRoadGroupsAfterProcess(gisRouteRoadAfterCropGroups);
    }

}
