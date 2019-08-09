package com.m.gis.springboot.service;

import com.m.gis.springboot.common.GisRouteConstants;
import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.CostTypeEnums;
import com.m.gis.springboot.exception.GisNoNearestRoadServiceException;
import com.m.gis.springboot.exception.GisNoRouteRoadResultThrowableException;
import com.m.gis.springboot.exception.GisNoServiceAreaServiceException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeodesyUtil;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisOperationsUtil;
import com.m.gis.springboot.mapper.GisRoadMapper;
import com.m.gis.springboot.po.GisNearestNode;
import com.m.gis.springboot.po.GisNearestRoad;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.qo.GisKShortestPathQO;
import com.m.gis.springboot.qo.GisNearPoiQO;
import com.m.gis.springboot.qo.GisServiceAreaQO;
import com.m.gis.springboot.qo.GisShortestPathQO;
import com.m.gis.springboot.utils.LocaleUtil;
import com.m.gis.springboot.utils.route.GisAbstractRouteFilter;
import com.m.gis.springboot.utils.route.GisLinearRouteFilter;
import com.m.gis.springboot.utils.route.GisNodePairQueryExecutor;
import com.m.gis.springboot.utils.route.bo.GisNodePair;
import com.m.gis.springboot.utils.route.formatter.*;
import com.m.gis.springboot.utils.route.processor.GisKRoutePostProcessor;
import com.m.gis.springboot.utils.route.processor.GisPassingRoutePostProcessor;
import com.m.gis.springboot.utils.route.processor.GisRoutePostProcessor;
import com.m.gis.springboot.vo.GisNearPoiVO;
import com.m.gis.springboot.vo.GisShortestPathVO;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Title: GisRouteServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
@Service
public class GisRouteServiceImpl implements GisRouteService {
    private static final Logger logger = LoggerFactory.getLogger(GisRouteServiceImpl.class);

    private String tablename = "gis_routing_roads_l";

    @Autowired
    private GisRoadMapper gisRoadMapper;

    /***
     * @Description: 用于返回最短路径处理器，qo为查询体，gisRouteFormatter格式化输出结果
     * @Author: monkjavaer
     * @Data: 14:08 2018/9/6
     * @Param: [qo, gisRouteFormatter]
     * @Throws
     * @Return T
     */
    public <T> T getShortestPathWithTrspFromProcessor(GisShortestPathQO qo, GisRouteFormatter<T> gisRouteFormatter) {
        //检查障碍参数是否为合法的wkt格式
        if (CollectionUtils.isNotEmpty(qo.getBarriers())) {
            for (String item : qo.getBarriers()) {
                GisPreconditions.checkGeometry(item, String.format("barrier param {%s} can not be parsed as a geometry object.", item));
            }
        }

        //查询给定点的最邻近路段
        List<GisNearestRoad> gisNearestRoads = gisRoadMapper.getNearestRoad(tablename, qo.getPassPoints(), GisRouteConstants.GIS_NEAREST_EDGE_TOLERANCE);

        //起始终止点生成器，用于根据位置点，转换生成适合于数据库查找的node节点
        GisNodePairQueryExecutor gisNodePairQueryExecutor = null;
        List<GisNodePair> gisNodePairs = null;
        try {
            gisNodePairQueryExecutor = new GisNodePairQueryExecutor(qo.getPassPoints(), gisNearestRoads, qo.getBarriers());
            //获取需要数据库查询的附着路网节点
            gisNodePairs = gisNodePairQueryExecutor.getQueryGisAttachedNodePairs();
        } catch (GisNoRouteRoadResultThrowableException e) {
            //查询无结果，返回null
            logger.info("no suitable node pair be found, so return none path.");
            return null;
        }

        //如果需要查库
        List<List<GisRouteRoad>> gisRouteRoadAfterGroup = null;
        if (gisNodePairs.size() != 0) {
            GisAbstractRouteFilter filter = new GisLinearRouteFilter();
            String filterWkt = filter.getRouteFilterWkt(gisNodePairQueryExecutor.getAttachedNodesPosition());
            //查询最短路径集合
            List<GisRouteRoad> gisRouteRoadsFromDb = null;
            try {
                gisRouteRoadsFromDb = gisRoadMapper.getTrsp(tablename, gisNodePairs, filterWkt, qo.getBarriers());
            } catch (UncategorizedSQLException ex) {
                logger.error(ex.toString());
            }

            //对查询结果路径根据routeid进行分组，每个分组对应每个节点对的结果
            gisRouteRoadAfterGroup = GisRoutePostProcessor.groupProcess(gisRouteRoadsFromDb);
            if (gisNodePairs.size() != gisRouteRoadAfterGroup.size()) {
                if (logger.isDebugEnabled())
                    logger.debug("some node pair don't have shortest path from db, so returns null.");
                return null;
            }
        }

        //处理路径结果
        GisRoutePostProcessor<T> gisRoutePostProcessor = new GisPassingRoutePostProcessor<>(gisRouteRoadAfterGroup, gisNodePairQueryExecutor, gisRouteFormatter);
        return gisRoutePostProcessor.process();
    }

    /***
     * @Description: 用于返回K最短路径的处理器，qo为查询体，gisRouteFormatter为格式化输出结果
     * @Author: monkjavaer
     * @Data: 14:07 2018/9/6
     * @Param: [qo, gisRouteFormatter]
     * @Throws
     * @Return T
     */
    public <T> T getKShortestPathFromProcessor(GisKShortestPathQO qo, GisRouteFormatter<T> gisRouteFormatter) {
        //检查障碍参数是否为合法的wkt格式
        if (CollectionUtils.isNotEmpty(qo.getBarriers())) {
            for (String item : qo.getBarriers()) {
                GisPreconditions.checkGeometry(item, String.format("barrier param {%s} can not be parsed as a geometry object.", item));
            }
        }

        //查询给定点的最邻近路段
        List<GisCoordinate> passPoints = new ArrayList<>(Arrays.asList(
                new GisCoordinate(qo.getStartLongitude(), qo.getStartLatitude()),
                new GisCoordinate(qo.getEndLongitude(), qo.getEndLatitude())
        ));
        List<GisNearestRoad> gisNearestRoads = gisRoadMapper.getNearestRoad(tablename, passPoints, GisRouteConstants.GIS_NEAREST_EDGE_TOLERANCE);

        //起始终止点生成器，用于根据位置点，转换生成适合于数据库查找的node节点
        GisNodePairQueryExecutor gisNodePairQueryExecutor = null;
        List<GisNodePair> gisNodePairs = null;
        try {
            gisNodePairQueryExecutor = new GisNodePairQueryExecutor(passPoints, gisNearestRoads, qo.getBarriers());
            //获取最近的node节点列表
            gisNodePairs = gisNodePairQueryExecutor.getQueryGisAttachedNodePairs();
        } catch (GisNoRouteRoadResultThrowableException e) {
            //查询无结果，返回null
            logger.info("no suitable node pair be found, so return none path.");
            return null;
        }

        //如果需要查库
        List<List<GisRouteRoad>> gisRouteRoadAfterGroup = null;
        if (gisNodePairs.size() != 0) {
            GisLinearRouteFilter filter = new GisLinearRouteFilter();
            String filterWkt = filter.getRouteFilterWkt(gisNodePairQueryExecutor.getAttachedNodesPosition());

            List<GisRouteRoad> gisRouteRoadsFromDb = null;
            try {
                gisRouteRoadsFromDb = gisRoadMapper.getKShortestPath(tablename, gisNodePairs.get(0).getStart(), gisNodePairs.get(0).getEnd(), qo.getPathCount(), filterWkt, qo.getBarriers());
            } catch (UncategorizedSQLException ex) {
                logger.error(ex.toString());
            }

            //对查询结果路径根据routeid进行分组，每个分组对应每个节点对的结果
            gisRouteRoadAfterGroup = GisRoutePostProcessor.groupProcess(gisRouteRoadsFromDb);
            if (CollectionUtils.isEmpty(gisRouteRoadAfterGroup)) {
                if (logger.isDebugEnabled())
                    logger.debug("node pair don't have k shortest path from db, so returns null.");
                return null;
            }
        }

        //处理路径结果
        GisRoutePostProcessor<T> gisRoutePostProcessor = new GisKRoutePostProcessor<>(gisRouteRoadAfterGroup, gisNodePairQueryExecutor, gisRouteFormatter);
        return gisRoutePostProcessor.process();
    }


    @Override
    public GisShortestPathVO getShortestPathInstrunctionViewWithTrsp(GisShortestPathQO qo) {
        //处理路径结果，返回路径的指南和相关信息
        Locale locale;
        if (StringUtils.isEmpty(qo.getLocale())) {
            locale = LocaleUtil.getLocale();
        } else {
            locale = LocaleUtil.getLocaleByString(qo.getLocale());
        }
        return getShortestPathWithTrspFromProcessor(qo, new GisPassingPathInstructionFormatter(locale));
    }

    @Override
    public String getShortestPathGeometryViewWithTrsp(GisShortestPathQO qo) {
        //处理路径结果，只返回路径的几何形状
        return getShortestPathWithTrspFromProcessor(qo, new GisPassingSingleLineMergeFormatter());
    }

    @Override
    public List<String> getKShortestPathGeometryView(GisKShortestPathQO qo) {
        //处理路径结果，只返回路径的几何形状
        return getKShortestPathFromProcessor(qo, new GisKSingleLineMergeFormatter());
    }

    @Override
    public GisShortestPathVO getKShortestPathInstructionView(GisKShortestPathQO qo) {
        //处理路径结果，返回路径的指南和相关信息
        Locale locale;
        if (StringUtils.isEmpty(qo.getLocale())) {
            locale = LocaleUtil.getLocale();
        } else {
            locale = LocaleUtil.getLocaleByString(qo.getLocale());
        }
        return getKShortestPathFromProcessor(qo, new GisKPathInstructionFormatter(locale));
    }

    @Override
    public String getServiceArea(GisServiceAreaQO qo) {
        //查询给定点的最邻近路段
        List<GisNearestNode> gisNearestNodes = gisRoadMapper.getNearestNode(tablename, qo.getStartPoints(), GisRouteConstants.GIS_NEAREST_EDGE_TOLERANCE);

        //如果有定位点距离路段太远，无法找到最临近的路段，则认为无最短路径
        if (gisNearestNodes == null || gisNearestNodes.size() != qo.getStartPoints().size()) {
            throw new GisNoNearestRoadServiceException();
        }

        List<Integer> gisNearestNodeIds = new ArrayList<>();
        List<GisCoordinate> gisNearestNodeLocations = new ArrayList<>();
        for (GisNearestNode item : gisNearestNodes) {
            gisNearestNodeIds.add(item.getNodeId());
            gisNearestNodeLocations.add(new GisCoordinate(item.getLongitude(), item.getLatitude()));
        }

        //前台传入成本类型,0距离,1时间
        Double cost = qo.getCost();
        if (qo.getType() != null) {
            if (CostTypeEnums.DISTANCE.getValue().equals(qo.getType())) {
                cost = qo.getCost();
            }
            if (CostTypeEnums.TIME.getValue().equals(qo.getType())) {
                //现在默认速度为1000m/min
                cost = qo.getCost() * GisRouteConstants.MAX_SPEED;
            }
        }

        //根据服务成本参数cost筛选参与计算的路网数据
        // 由于直线距离最短，计算cost时间成本能到达的最短直线距离tolerance，以startNodes为圆心、tolerance为半径画圆，服务范围的路径一定包含在该圆内，这样筛选能极大减小参与计算的路径数量
        GisCoordinate fastestPoint = GisGeodesyUtil.moveInDirection(gisNearestNodes.get(0), 0, cost);
        double tolerance = fastestPoint.distance(gisNearestNodes.get(0));

        List<String> wkts = new ArrayList<>();
        try {
            wkts = gisRoadMapper.getDrivingDistance(tablename, gisNearestNodes, cost, tolerance);
        } catch (UncategorizedSQLException ex) {
            logger.error(ex.toString());
        }

        List<Geometry> geoms = new ArrayList<>();
        for (String item : wkts) {
            try {
                Geometry geom = GisGeometryFactoryUtil.createGeometryByWKT(item);
                //获取geom的外边界，清除服务范围内部的洞
                geoms.add(GisOperationsUtil.exteriorRingPolygon((Polygon)geom));
            } catch (GisParseGeometryBaseThrowableException e) {
                GisException ex = new GisNoServiceAreaServiceException(String.format("no service area exception, some result can not be convert to geometry.", item));
                logger.error(ex.toString());
                throw ex;
            }
        }
        Geometry geom = GisOperationsUtil.union(geoms);
        return geom == null ? null : geom.toString();
    }

    @Override
    public List<GisNearPoiVO> getNearPoi(GisNearPoiQO gisNearPoiQO) {
        //临近设施点个数偏移量
        Integer offset = 3;
        Integer oldNumber = gisNearPoiQO.getNumber();
        gisNearPoiQO.setNumber(oldNumber+offset);
        //先查询出临近设施点个数+偏移量个直线距离最近的结果集
        List<GisNearPoiVO> gisNearPoiVOS = gisRoadMapper.getNearPoi(gisNearPoiQO);
        if (gisNearPoiVOS.size() ==0){
            logger.info("no close facility.");
            return null;
        }
        GisCoordinate center = new GisCoordinate();
        center.setLatitude(gisNearPoiQO.getLatitude());
        center.setLongitude(gisNearPoiQO.getLongitude());

        //数据处理
        for (GisNearPoiVO gisNearPoiVO : gisNearPoiVOS) {
            List<GisCoordinate> gisCoordinates = new ArrayList<>();
            GisCoordinate poi = new GisCoordinate();
            poi.setLongitude(gisNearPoiVO.getLongitude());
            poi.setLatitude(gisNearPoiVO.getLatitude());
            gisCoordinates.add(center);
            gisCoordinates.add(poi);
            GisShortestPathQO qo = new GisShortestPathQO();
            qo.setPassPoints(gisCoordinates);
            //查询路径的几何形状
            String path = getShortestPathWithTrspFromProcessor(qo, new GisPassingSingleLineMergeFormatter());
            gisNearPoiVO.setPath(path);
            //查询中心点到临近设施的路段距离
            GisShortestPathVO gisShortestPathVO = getShortestPathInstrunctionViewWithTrsp(qo);
            if (gisShortestPathVO != null && gisShortestPathVO.getRoutes().size() > 0 ){
                gisNearPoiVO.setDistance(gisShortestPathVO.getRoutes().get(0).getDistance());
            }
        }
        //对查询出的结果根据距离排序
        Collections.sort(gisNearPoiVOS, new Comparator<GisNearPoiVO>() {
            @Override
            public int compare(GisNearPoiVO o1, GisNearPoiVO o2) {
                if (o1.getDistance()>o2.getDistance()){
                    return 1;
                }else if (o1.getDistance() == o2.getDistance()) {
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        if (oldNumber<gisNearPoiVOS.size()) {
            return gisNearPoiVOS.subList(0, oldNumber);
        }else {
            return gisNearPoiVOS;
        }
    }
}
