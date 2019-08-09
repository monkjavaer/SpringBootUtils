package com.m.gis.springboot.vo;

import java.util.List;

/**
 * @Title: GisPathVO
 * @Package: com.m.gis.springboot.vo
 * @Description: 表示单条最短路径的查询结果对象
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
public class GisPathVO {
    /**
     * 单条最短路径的总距离
     */
    private Double distance;

    /**
     * 单条最短路径的总时间
     */
    private Double time;

    /**
     * 单条最短路径的路段集合
     */
    private List<GisPathStepVO> steps;

    public GisPathVO() {
    }

    public GisPathVO(Double distance, List<GisPathStepVO> steps,Double time) {
        this.distance = distance;
        this.steps = steps;
        this.time = time;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<GisPathStepVO> getSteps() {
        return steps;
    }

    public void setSteps(List<GisPathStepVO> steps) {
        this.steps = steps;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
