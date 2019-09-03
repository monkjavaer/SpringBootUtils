package com.m.gis.springboot.vo;

import java.util.List;

/**
 * @Title: GisShortestPathVO
 * @Package: com.m.gis.springboot.vo
 * @Description: 最短路径查询结果体
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
public class GisShortestPathVO {
    private List<GisPathVO> routes;

    public GisShortestPathVO(List<GisPathVO> routes) {
        this.routes = routes;
    }

    public List<GisPathVO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<GisPathVO> routes) {
        this.routes = routes;
    }
}
