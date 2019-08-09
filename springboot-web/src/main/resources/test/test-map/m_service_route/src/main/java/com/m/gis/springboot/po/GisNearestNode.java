package com.m.gis.springboot.po;

import com.m.gis.springboot.geo.base.common.GisCoordinate;

/**
 * @Title: GisNearestNode
 * @Package: com.m.gis.springboot.po
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/30
 * @Version: V1.0
 */
public class GisNearestNode extends GisCoordinate {
    /**
     * 对应查询点的查询id，如ABC三个点，查找三个点的最近路段时，每个点对应一个查询id
     */
    private Integer queryId;

    /**
     * 对应查询的最临近node节点
     */
    private Integer nodeId;

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
