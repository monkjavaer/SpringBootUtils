package com.m.gis.springboot.mapper;

import com.m.gis.springboot.qo.GisNearPoiQO;
import com.m.gis.springboot.utils.route.bo.GisNodePair;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.po.GisNearestNode;
import com.m.gis.springboot.po.GisNearestRoad;
import com.m.gis.springboot.po.GisRoad;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.vo.GisNearPoiVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GisRoadMapper extends Mapper<GisRoad> {

    /***
     * @Description: 查询一组点最邻近的路网节点
     * @Author: monkjavaer
     * @Data: 13:32 2018/7/30
     * @Param: [tablename, locations, tolerance]
     * @Throws 
     * @Return java.util.List<com.m.gis.springboot.po.GisNearestNode>
     */ 
    List<GisNearestNode> getNearestNode(
            @Param("tablename") String tablename,
            @Param("locations") List<GisCoordinate> locations,
            @Param("tolerance") Double tolerance
    );

    /***
     * @Description: 查询一组点最邻近的路段
     * @Author: monkjavaer
     * @Data: 15:03 2018/7/27
     * @Param: [locations]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisNearestRoad>
     */
    List<GisNearestRoad> getNearestRoad(
            @Param("tablename") String tablename,
            @Param("locations") List<GisCoordinate> locations,
            @Param("tolerance") Double tolerance
    );


    /***
     * @Description: 考虑转弯成本的最短路径计算
     * @Author: monkjavaer
     * @Data: 18:17 2018/7/24
     * @Param: [slon, slat, elon, elat, tolerance, barriers]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisBaseRoad>
     */
    List<GisRouteRoad> getTrsp(@Param("tablename") String tablename,
                               @Param("nodes") List<GisNodePair> nodes,
                               @Param("filter") String filter,
                               @Param("barries") List<String> barries
    );

    /***
     * @Description: 获取最短的k条路径
     * @Author: monkjavaer
     * @Data: 11:54 2018/8/1
     * @Param: [tablename, startNode, endNode, pathCount, filter]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisRouteRoad>
     */
    List<GisRouteRoad> getKShortestPath(@Param("tablename") String tablename,
                                        @Param("startNode") Integer startNode,
                                        @Param("endNode") Integer endNode,
                                        @Param("pathCount") Integer pathCount,
                                        @Param("filter") String filter,
                                        @Param("barries") List<String> barries
    );

    /***
     * @Description: 计算服务范围
     * @Author: monkjavaer
     * @Data: 18:38 2018/7/30
     * @Param: [tablename, startNodes, cost]
     * @Throws
     * @Return java.lang.String
     */
    List<String> getDrivingDistance(
            @Param("tablename") String tablename,
            @Param("startNodes") List<GisNearestNode> startNodes,
            @Param("cost") Double cost,
            @Param("tolerance") Double tolerance //以startNodes为圆心的筛选半径，用于筛选计算的路径，一般来说相当于以cost成本计算的最远距离
    );

    /**
    * @Description:  根据指定中心点的经纬度、要查询的设施点数量（最多10个）和类型，查询临近设施点
    * @Author: monkjavaer
    * @Date: 2018/11/8 11:27
    * @Param: [gisNearPoiQO]
    * @Return: java.util.List<com.m.gis.springboot.vo.GisNearPoiVO>
    * @Throws
    */
    List<GisNearPoiVO> getNearPoi(GisNearPoiQO gisNearPoiQO);
}