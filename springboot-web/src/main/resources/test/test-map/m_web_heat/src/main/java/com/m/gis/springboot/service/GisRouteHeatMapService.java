package com.m.gis.springboot.service;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.qo.GisCameraRouteQO;

import java.util.List;

/**
 * @Title: GisRouteHeatMapService.java
 * @Package com.m.gis.springboot.service.interfaces
 * @Description: 通过摄像头点位计算热力图分布
 * @author monkjavaer
 * @date 2018年1月3日 下午4:09:08
 * @version V1.0
 */
public interface GisRouteHeatMapService {

	/**
	 * 
	 * @name getHeatMapPoints
	 * @description  根据视频摄像头位置及流量，以及地图的视图范围获取热力图分布点
	 * @param qo
	 * @return 
	 * @author monkjavaer
	 * @date 2018年1月3日
	 */
	List<GisCoordinate> getHeatMapPoints(GisCameraRouteQO qo);
	
}

