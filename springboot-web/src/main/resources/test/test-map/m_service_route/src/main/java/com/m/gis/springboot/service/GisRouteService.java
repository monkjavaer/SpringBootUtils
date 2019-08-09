package com.m.gis.springboot.service;

import com.m.gis.springboot.qo.GisKShortestPathQO;
import com.m.gis.springboot.qo.GisNearPoiQO;
import com.m.gis.springboot.qo.GisServiceAreaQO;
import com.m.gis.springboot.qo.GisShortestPathQO;
import com.m.gis.springboot.vo.GisNearPoiVO;
import com.m.gis.springboot.vo.GisShortestPathVO;

import java.util.List;
import java.util.Locale;

/**
 * @Title: GisRouteService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
public interface GisRouteService {

    /***
     * @Description: 根据指定类型、经过点的经纬度和障碍几何，查询最短路径，仅返回路径的几何形状
     * @Author: monkjavaer
     * @Data: 17:07 2018/9/3
     * @Param: [qo]
     * @Throws
     * @Return String
     */
    public String getShortestPathGeometryViewWithTrsp(GisShortestPathQO qo);

    /***
     * @Description: 根据指定类型、经过点的经纬度和障碍几何，查询最短路径，返回路径指南信息
     * @Author: monkjavaer
     * @Data: 17:51 2018/9/18
     * @Param: [qo, locale]
     * @Throws
     * @Return com.m.gis.springboot.vo.GisShortestPathVO
     */
    public GisShortestPathVO getShortestPathInstrunctionViewWithTrsp(GisShortestPathQO qo);


    /***
     * @Description: 根据指定起始点的经纬度和障碍几何，查询最近的K条路径，返回几何
     * @Author: monkjavaer
     * @Data: 11:29 2018/8/1
     * @Param: [qo]
     * @Throws
     * @Return java.util.List<java.lang.String>
     */
    public List<String> getKShortestPathGeometryView(GisKShortestPathQO qo);

    /***
     * @Description: 根据指定起始点的经纬度和障碍几何，查询最近的K条路径，返回路径指南
     * @Author: monkjavaer
     * @Data: 17:51 2018/9/18
     * @Param: [qo, locale]
     * @Throws
     * @Return com.m.gis.springboot.vo.GisShortestPathVO
     */
    public GisShortestPathVO getKShortestPathInstructionView(GisKShortestPathQO qo);

    /***
     * @Description: 根据指定起点列表和服务成本，查询从这些点出发的服务范围
     * @Author: monkjavaer
     * @Data: 13:02 2018/7/30
     * @Param: [qo]
     * @Throws
     * @Return java.lang.String
     */
    public String getServiceArea(GisServiceAreaQO qo);

    /**
    * @Description: 根据指定中心点的经纬度、要查询的设施点数量（最多10个）和类型，查询临近设施点
    * @Author: monkjavaer
    * @Date: 2018/11/8 11:24
    * @param : [qo]
    * @Return: java.util.List<com.m.gis.springboot.vo.GisNearPoiVO>
    * @Throws
    */
    public List<GisNearPoiVO> getNearPoi(GisNearPoiQO qo);
}

