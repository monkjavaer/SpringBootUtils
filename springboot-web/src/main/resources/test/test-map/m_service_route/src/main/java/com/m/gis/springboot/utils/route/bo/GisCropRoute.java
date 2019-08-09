package com.m.gis.springboot.utils.route.bo;

import com.m.gis.springboot.enums.GisCropRouteEnums;
import com.m.gis.springboot.exception.GisRouteProcessorException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.po.GisBaseRoad;
import com.m.gis.springboot.po.GisRouteRoad;
import com.vividsolutions.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisCropRoute
 * @Package: com.m.gis.springboot.bo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/7
 * @Version: V1.0
 */
public class GisCropRoute{
    private static final Logger logger = LoggerFactory.getLogger(GisCropRoute.class);

    /**
     * 裁剪后成本
     */
    private Double cost = Double.MAX_VALUE;
    /**
     * 步行路段
     */
    private GisRouteRoad footWay;
    /**
     * 裁剪的路段
     */
    private GisRouteRoad cropWay;
    /**
     * 裁剪位置点在原路段上的比例
     */
    private Double fraction;
    /**
     * 裁剪类型
     */
    private GisCropRouteEnums cropType;
    /**
     * 被裁剪的原路段
     */
    private GisBaseRoad originRoad;

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public GisRouteRoad getFootWay() {
        return footWay;
    }

    public void setFootWay(GisRouteRoad footWay) {
        this.footWay = footWay;
    }

    public GisRouteRoad getCropWay() {
        return cropWay;
    }

    public void setCropWay(GisRouteRoad cropWay) {
        this.cropWay = cropWay;
    }

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }

    public GisCropRouteEnums getCropType() {
        return cropType;
    }

    public void setCropType(GisCropRouteEnums cropType) {
        this.cropType = cropType;
    }

    public GisBaseRoad getOriginRoad() {
        return originRoad;
    }

    public void setOriginRoad(GisBaseRoad originRoad) {
        this.originRoad = originRoad;
    }

    /**
     * 获取该补充路径附着的路网节点id
     * @return
     */
    public Integer getAttachedNodeId() {
        if(getCropWay()==null)
            throw new GisRouteProcessorException("getAttachedNodeId errors, crop way is null.");

        if(this.cropType.equals(GisCropRouteEnums.LOCATION_SOURCE)||this.cropType.equals(GisCropRouteEnums.LOCATION_TARGET))
            return this.getCropWay().getTarget();
        else
            return this.getCropWay().getSource();
    }


    /**
     * 获取该补充路径附着的路网节点的经纬度位置
     * @return
     */
    public GisCoordinate getAttachedNodePosition(){
        if(getCropWay()==null)
            throw new GisRouteProcessorException("getAttachedNodePosition errors, crop way is null.");

        LineString lineString = null;
        try {
            lineString = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(getCropWay().getGeom());
            if(this.cropType.equals(GisCropRouteEnums.LOCATION_SOURCE)||this.cropType.equals(GisCropRouteEnums.LOCATION_TARGET))
                return new GisCoordinate(lineString.getEndPoint().getX(),lineString.getEndPoint().getY());
            else
                return new GisCoordinate(lineString.getStartPoint().getX(),lineString.getStartPoint().getY());
        } catch (GisParseGeometryBaseThrowableException e) {
            logger.error(e.toString());
            throw new GisRouteProcessorException(String.format("getAttachedNodePosition errors, node position parse occurs some exception, {%s}",getCropWay().getGeom()));
        }
    }

    /**
     * 获取定位点步行路段与路网路段相交的经纬度位置
     * @return
     */
    public GisCoordinate getIntersectionPosition(){
        if(getCropWay()==null)
            throw new GisRouteProcessorException("getIntersectionPosition errors, crop way is null.");

        LineString lineString = null;
        try {
            lineString = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(getCropWay().getGeom());
            if(this.cropType.equals(GisCropRouteEnums.LOCATION_SOURCE)||this.cropType.equals(GisCropRouteEnums.LOCATION_TARGET))
                return new GisCoordinate(lineString.getStartPoint().getX(),lineString.getStartPoint().getY());
            else
                return new GisCoordinate(lineString.getEndPoint().getX(),lineString.getEndPoint().getY());
        } catch (GisParseGeometryBaseThrowableException e) {
            logger.error(e.toString());
            throw new GisRouteProcessorException(String.format("getIntersectionPosition errors, intersection position parse occurs some exception, {%s}",getCropWay().getGeom()));
        }
    }

}