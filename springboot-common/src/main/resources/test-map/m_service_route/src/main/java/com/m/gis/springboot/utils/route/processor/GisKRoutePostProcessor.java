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
public class GisKRoutePostProcessor<T> extends GisRoutePostProcessor<T> {
    private static final Logger logger = LoggerFactory.getLogger(GisKRoutePostProcessor.class);

    private Map<Integer,List<GisRouteRoad>> queryMap;

    public GisKRoutePostProcessor(List<List<GisRouteRoad>> gisRouteRoadGroups, GisNodePairQueryExecutor executor, GisRouteFormatter<T> gisRouteFormatter){
        super(executor,gisRouteFormatter);

        List<GisNodePair> gisNodePairs = executor.getAllAttachedNodePairs();
        if(CollectionUtils.isEmpty(gisNodePairs)||gisNodePairs.size()!=1)
            throw new GisRouteProcessorException("k route node pairs is null or size is more than 1.");

        queryMap = new LinkedHashMap<>();
        //将查询节点对与数据库的查询路径结果关联起来，用于后续处理
        GisNodePair gisNodePair = gisNodePairs.get(0);
        int index=0;
        if(gisNodePair.isQuery()==false){
            //如果是不需要从数据库查询的情况，这里直接设置一个空的结果，在GisRoutePostProcessor的startAndDestinationRouteCrop处理时，会对空结果进行处理
            queryMap.put(index++,new ArrayList<>());
        }
        else{
            if(CollectionUtils.isEmpty(gisRouteRoadGroups)){
                String message = String.format("node pair don't have shortest paths, so throw exception. node pair: {id:{%s}, position:{%s}} --> {id:{%s}, position:{%s}}",
                        gisNodePair.getStart(),
                        gisNodePair.getStartAttachedNodePosition(),
                        gisNodePair.getEnd(),
                        gisNodePair.getEndAttachedNodePosition());
                if(logger.isDebugEnabled())
                    logger.debug(message);
                throw new GisRouteProcessorException(message);
            }

            for(List<GisRouteRoad> item:gisRouteRoadGroups){
                queryMap.put(index++,item);
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
    public void startAndDestinationProcessor() {
        //处理首尾两端的路径
        List<List<GisRouteRoad>> gisRouteRoadAfterCropGroups = new ArrayList<>();

        //对节点对进行首尾两端的路径处理
        GisNodePair gisNodePair = getExecutor().getAllAttachedNodePairs().get(0);
        for(Map.Entry<Integer,List<GisRouteRoad>> entry:queryMap.entrySet()){
            List<GisRouteRoad> routesAfterProcess = startAndDestinationRouteCrop(gisNodePair,entry.getValue());
            gisRouteRoadAfterCropGroups.add(routesAfterProcess);
        }

        //保存添加了首尾路段的预处理结果
        setGisRouteRoadGroupsAfterProcess(gisRouteRoadAfterCropGroups);
    }

}
