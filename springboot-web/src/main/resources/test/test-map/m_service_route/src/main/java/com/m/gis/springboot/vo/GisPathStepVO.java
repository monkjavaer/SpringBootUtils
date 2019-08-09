package com.m.gis.springboot.vo;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.exception.GisRouteLegStepHandleException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.po.GisRouteRoad;
import com.vividsolutions.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: GisPathStepVO
 * @Package: com.m.gis.springboot.vo
 * @Description: 构成路径查询结果的每条路段，如查询结果为A-B-C-D 4条路段，则该VO对应为表示A/B/C/D单条路段的对象
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
public class GisPathStepVO extends GisCoordinate {
    private static final Logger logger = LoggerFactory.getLogger(GisPathStepVO.class);

    /**
     * 路段id
     */
    private Integer id;

    /**
     * 路段几何
     */
    private String geom;

    /**
     * 行驶类型
     */
    private String transType;

    /**
     * 路段距离，单位米
     */
    private Double distance;

    /**
     * 指南
     */
    private String instruction;

    /**
     * 最大速度
     */
    private Short maxSpeed;

    /**
     * 方向（左转、右转、直行、调头）
     */
    private String direction;

    /**
     * 是否为补充的步行路段
     */
    private Boolean footWay;

    public GisPathStepVO() {
    }

    public GisPathStepVO(Integer id, String geom, String transType, Double distance, String direction, String instruction, Short maxSpeed) {
        this.id = id;
        this.geom = geom;
        this.transType = transType;
        this.distance = distance;
        this.instruction = instruction;
        this.maxSpeed = maxSpeed;
        this.direction = direction;
    }

    public GisPathStepVO(Integer id, GisRouteRoad gisRouteRoad,String direction,String instruction){
        this.id = id;
        this.geom = gisRouteRoad.getGeom();
        this.distance = gisRouteRoad.getLength();
        this.maxSpeed = gisRouteRoad.getMaxspeed();
        //this.transType预留字段
        this.instruction = instruction;
        this.direction = direction;
        this.footWay = gisRouteRoad.isFootWay();

        LineString lineString = null;
        try {
            lineString = (LineString)GisGeometryFactoryUtil.createGeometryByWKT(this.geom);
            if(lineString.getStartPoint()==null)
                throw new GisRouteLegStepHandleException("GisPathStepVO constructor error, lineString start point is null.");
            setLongitude(lineString.getStartPoint().getX());
            setLatitude(lineString.getStartPoint().getY());
        } catch (GisParseGeometryBaseThrowableException e) {
            GisException ex = new GisRouteLegStepHandleException("GisPathStepVO constructor error,gis geometry parse to linestring occur errors.");
            logger.error(ex.toString());
            throw ex;
        }


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Short getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Short maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getFootWay() {
        return footWay;
    }

    public void setFootWay(Boolean footWay) {
        this.footWay = footWay;
    }
}
