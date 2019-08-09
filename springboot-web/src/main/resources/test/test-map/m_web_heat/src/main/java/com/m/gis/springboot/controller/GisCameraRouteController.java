package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.qo.GisCameraRouteQO;
import com.m.gis.springboot.service.GisRouteHeatMapService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Title: GisCameraRouteController.java
 * @Package com.m.gis.springboot.controller
 * @Description: 用于
 * @author monkjavaer
 * @date 2017年11月24日 下午4:13:10
 * @version V1.0
 */
@RestController
@RequestMapping(value="/gis/camera_route/")
public class GisCameraRouteController {
	
	@Autowired
	private GisRouteHeatMapService gisRouteHeatMapService;
	
	
	 @ApiOperation(value="根据视频点位和流量获取交通流量分布点", notes="")
	    @RequestMapping(value="/heatmap.do", method=RequestMethod.POST)
	    public GisResult getVehicleHeapmap(@RequestBody @Valid GisCameraRouteQO qo) {
		 	List<GisCoordinate> result = gisRouteHeatMapService.getHeatMapPoints(qo);
		 	return ResultUtil.success(JsonUtils.toBeanToArrayJSONString(result));
	    } 
}

