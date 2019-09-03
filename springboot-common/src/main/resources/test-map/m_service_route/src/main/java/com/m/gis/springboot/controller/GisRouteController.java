package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.qo.GisKShortestPathQO;
import com.m.gis.springboot.qo.GisNearPoiQO;
import com.m.gis.springboot.qo.GisServiceAreaQO;
import com.m.gis.springboot.qo.GisShortestPathQO;
import com.m.gis.springboot.service.GisRouteService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Title: GisShortestPathController
 * @Package: com.m.gis.springboot.controller
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/route")
public class GisRouteController extends GisBaseController{

    @Autowired
    private GisRouteService gisRouteService;

    @ApiOperation(value="根据指定类型、经过点的经纬度和障碍几何，查询最近的路径，返回几何路段视图", notes="")
    @RequestMapping(value="/shortestPathGeometryView.do", method=RequestMethod.POST)
    public GisResult getShortestPathGeometryView(@RequestBody @Valid GisShortestPathQO gisShortestPathQO) {
        return ResultUtil.success(gisRouteService.getShortestPathGeometryViewWithTrsp(gisShortestPathQO));
    }

    @ApiOperation(value="根据指定类型、经过点的经纬度和障碍几何，查询最近的路径，返回路径指南视图", notes="")
    @RequestMapping(value="/shortestPathInstructionView.do", method=RequestMethod.POST,produces = "application/json; charset=utf-8")
    public GisResult getShortestPathInstructionView(@RequestBody @Valid GisShortestPathQO gisShortestPathQO) {
        return ResultUtil.success(gisRouteService.getShortestPathInstrunctionViewWithTrsp(gisShortestPathQO));
    }

    @ApiOperation(value="根据指定起始点的经纬度和障碍几何，查询最近的K条路径，返回几何路段视图", notes="")
    @RequestMapping(value="/kShortestPathGeometryView.do", method=RequestMethod.POST)
    public GisResult getKShortestPathGeometryView(@RequestBody @Valid GisKShortestPathQO gisKShortestPathQO) {
        return ResultUtil.success(gisRouteService.getKShortestPathGeometryView(gisKShortestPathQO));
    }

    @ApiOperation(value="根据指定起始点的经纬度和障碍几何，查询最近的K条路径，返回路径指南视图", notes="")
    @RequestMapping(value="/kShortestPathInstructionView.do", method=RequestMethod.POST,produces = "application/json; charset=utf-8")
    public GisResult getKShortestPathInstructionView(@RequestBody @Valid GisKShortestPathQO gisKShortestPathQO) {
        return ResultUtil.success(gisRouteService.getKShortestPathInstructionView(gisKShortestPathQO));
    }

    @ApiOperation(value="根据指定起点列表和服务成本，查询从这些点出发的服务范围", notes="")
    @RequestMapping(value="/serviceArea.do", method=RequestMethod.POST)
    public GisResult getServiceArea(@RequestBody @Valid GisServiceAreaQO gisServiceAreaQO) {
        return ResultUtil.success(gisRouteService.getServiceArea(gisServiceAreaQO));
    }

    @ApiOperation(value="根据指定中心点的经纬度、要查询的设施点数量（最多10个）和类型，查询临近设施点", notes="")
    @RequestMapping(value="/nearPoi.do", method=RequestMethod.POST)
    public GisResult getNearPoi(@RequestBody @Valid GisNearPoiQO gisNearPoiQO) {
        return ResultUtil.success(gisRouteService.getNearPoi(gisNearPoiQO));
    }
}
