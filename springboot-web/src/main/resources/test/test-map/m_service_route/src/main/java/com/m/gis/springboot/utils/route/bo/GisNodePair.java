package com.m.gis.springboot.utils.route.bo;

import com.m.gis.springboot.common.GisRouteConstants;
import com.m.gis.springboot.exception.GisPointsTooCloseServiceException;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.po.GisRouteRoad;
import com.m.gis.springboot.utils.route.GisCropRouteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisNodePair
 * @Package: com.m.gis.springboot.bo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/3
 * @Version: V1.0
 */
public class GisNodePair {
    private static final Logger logger = LoggerFactory.getLogger(GisNodePair.class);
    /**
     * 起止位置点对id
     */
    private Integer id;
    /**
     * 位置点到路网节点起点的补充路径
     */
    private GisCropRoute startCropRoute;
    /**
     * 位置点到路网节点终点的补充路径
     */
    private GisCropRoute endCropRoute;
    /**
     * 是否需要数据库查询标识
     */
    private boolean isQuery=true;

    public GisNodePair(Integer id, GisCropRoute startCropRoute, GisCropRoute endCropRoute) {
        this.id = id;
        this.startCropRoute = startCropRoute;
        this.endCropRoute = endCropRoute;

        //去掉小于10米验证
/*
        if(isValid()==false){
            if(logger.isDebugEnabled()){
                logger.debug(String.format("start crop location {%s} is too close to end crop location {%s}, so returns exception.",startCropRoute.getIntersectionPosition().toString(),endCropRoute.getIntersectionPosition().toString()));
            }
//            throw new GisPointsTooCloseServiceException(String.format("%s and %s points are too close when position is projected to the same road.", id,id+1));
            throw new GisPointsTooCloseServiceException("points are too close when position is projected to the same road.");
        }
*/

        if(isOneAttachedNode()||isOnewayOrderThrough())
            isQuery = false;
    }

    public Integer getId() {
        return id;
    }

    public GisCropRoute getStartCropRoute() {
        return startCropRoute;
    }

    public GisCropRoute getEndCropRoute() {
        return endCropRoute;
    }

    public boolean isQuery() {
        return isQuery;
    }

    /**
     * 获取起始路网节点的数据库id
     * @return
     */
    public Integer getStart() {
        return startCropRoute.getAttachedNodeId();
    }

    /**
     * 获取终止路网节点的数据库id
     * @return
     */
    public Integer getEnd() {
        return endCropRoute.getAttachedNodeId();
    }

    /**
     * 获取起始路网节点的经纬度位置
     * @return
     */
    public GisCoordinate getStartAttachedNodePosition(){
        return startCropRoute.getAttachedNodePosition();
    }

    /**
     * 获取终止路网节点的经纬度位置
     * @return
     */
    public GisCoordinate getEndAttachedNodePosition(){
        return endCropRoute.getAttachedNodePosition();
    }


    /**
     * 认为无效的位置点对，这里判断起始终止点附着到同一条路上，并且在路上的投影点距离小于10米左右，即认为无效
     * @return
     */
    public boolean isValid(){
        if(startCropRoute.getCropWay().getId().equals(endCropRoute.getCropWay().getId())
                &&startCropRoute.getIntersectionPosition().distance(endCropRoute.getIntersectionPosition())<GisRouteConstants.GIS_MIN_DISTANCE_BETWEEN_POINTS){
            return false;
        }
        return true;
    }

    /**
     * 判断起始和终止点是否附着到同一个路网节点上，数据库以同一个路网节点做为起始点查询，会出现回路，
     * 所以这种情况不需要再去查询数据库，采用手动拼接路径的方式
     * @return
     */
    public boolean isOneAttachedNode(){
        return getStart().equals(getEnd());
    }

    /**
     * 起始和终止点附着到同一个路网节点上时，手动拼接路径的结果
     * @return
     */
    public List<GisRouteRoad> getOneAttachedNodeProcessRouteList(){
        if(isQuery)
            throw new GisRouteProcessorException("this node pair should query db, so it is unnecessary to process manually.");

        if(isOneAttachedNode()==false)
            throw new GisRouteProcessorException("this node pair is not one attached node, so exception be thrown if this method is called.");

        List<GisRouteRoad> gisRouteRoads = new ArrayList<>();

        //添加起点步行路段
        if (startCropRoute.getFootWay().getLength() > 0) {
            gisRouteRoads.add(startCropRoute.getFootWay());
            if (logger.isDebugEnabled())
                logger.debug("add foot way to start location. ++ route id " + startCropRoute.getFootWay().getId() + "," + startCropRoute.getFootWay().getGeom());
        }
        //如果起点裁剪边和终点裁剪边是同一边，则取该裁剪边的交集，否则取两者的并集
        GisRouteRoad startCropWay = startCropRoute.getCropWay();
        GisRouteRoad endCropWay = endCropRoute.getCropWay();

        if (startCropWay.getId().equals(endCropWay.getId())) {
            GisRouteRoad cropBetweenStartEndRoute = null;
            if (startCropWay.getLength() > endCropWay.getLength()) {
                cropBetweenStartEndRoute = GisCropRouteUtil.subRouteRoad(startCropWay, 0, (startCropWay.getLength() - endCropWay.getLength()) / startCropWay.getLength());
            } else {
                cropBetweenStartEndRoute = GisCropRouteUtil.subRouteRoad(endCropWay, startCropWay.getLength() / endCropWay.getLength(), 1);
            }
            gisRouteRoads.add(cropBetweenStartEndRoute);
            if (logger.isDebugEnabled())
                logger.debug(String.format("start crop way and end crop way is the same one, so crop the intersection part. ++ route id {%s},{%s}", cropBetweenStartEndRoute.getRouteId(), cropBetweenStartEndRoute.getGeom()));
        } else {
            gisRouteRoads.add(startCropWay);
            if (logger.isDebugEnabled())
                logger.debug(String.format("start crop way is append. ++ route id {%s},{%s}", startCropWay.getRouteId(), startCropWay.getGeom()));
            gisRouteRoads.add(endCropWay);
            if (logger.isDebugEnabled())
                logger.debug(String.format("start crop way is append. ++ route id {%s},{%s}", endCropWay.getRouteId(), endCropWay.getGeom()));
        }
        //添加终点步行路段
        if (endCropRoute.getFootWay().getLength() > 0) {
            gisRouteRoads.add(endCropRoute.getFootWay());
            if (logger.isDebugEnabled())
                logger.debug("add foot way to destination location. ++ route id " + endCropRoute.getFootWay().getId() + "," + endCropRoute.getFootWay().getGeom());
        }

        return gisRouteRoads;
    }


    /**
     * 判断是否为单行道顺序通过的情况，如AB为单行线，起点S、终点E投影到AB上，且S距离A较近，E距离B较近，那么会出现
     * S附着到B点，E附着到A点的情况，数据库查询B、A节点的路径，导致回路，所以这种情况不应该查库，采用手动拼接路径的方式
     * @return
     */
    public boolean isOnewayOrderThrough(){
        if(startCropRoute.getCropWay().getId().equals(endCropRoute.getCropWay().getId())&&
           startCropRoute.getOriginRoad().getCost()*startCropRoute.getOriginRoad().getReverseCost()<0){
            if(startCropRoute.getOriginRoad().getCost()>0&&startCropRoute.getFraction()<endCropRoute.getFraction())
                return true;
            if(startCropRoute.getOriginRoad().getReverseCost()>0&&startCropRoute.getFraction()>endCropRoute.getFraction())
                return true;
        }
        return false;
    }

    /**
     * 单行道顺序通过的情况时，手动拼接路径的结果
     * @return
     */
    public List<GisRouteRoad> getOnewayOrderThroughProcessRouteList() {
        if(isQuery)
            throw new GisRouteProcessorException("this node pair should query db, so it is unnecessary to process manually.");

        if(isOnewayOrderThrough()==false)
            throw new GisRouteProcessorException("this node pair is not one way pass through orderly, so exception be thrown if this method is called.");

        List<GisRouteRoad> gisRouteRoads = new ArrayList<>();

        //添加起点步行路段
        if (startCropRoute.getFootWay().getLength() > 0) {
            gisRouteRoads.add(startCropRoute.getFootWay());
            if (logger.isDebugEnabled())
                logger.debug("add foot way to start location. ++ route id " + startCropRoute.getFootWay().getId() + "," + startCropRoute.getFootWay().getGeom());
        }
        //裁剪中间路段
        GisRouteRoad cropBetweenStartEndRoute = GisCropRouteUtil.subRouteRoad(startCropRoute.getOriginRoad(), startCropRoute.getFraction(), endCropRoute.getFraction());
        gisRouteRoads.add(cropBetweenStartEndRoute);
        if (logger.isDebugEnabled())
            logger.debug(String.format("start crop way and end crop way is the same one and pass through orderly, so crop the intersection part. ++ route id {%s},{%s}", cropBetweenStartEndRoute.getRouteId(), cropBetweenStartEndRoute.getGeom()));

        //添加终点步行路段
        if (endCropRoute.getFootWay().getLength() > 0) {
            gisRouteRoads.add(endCropRoute.getFootWay());
            if (logger.isDebugEnabled())
                logger.debug("add foot way to destination location. ++ route id " + endCropRoute.getFootWay().getId() + "," + endCropRoute.getFootWay().getGeom());
        }

        return gisRouteRoads;
    }


}
