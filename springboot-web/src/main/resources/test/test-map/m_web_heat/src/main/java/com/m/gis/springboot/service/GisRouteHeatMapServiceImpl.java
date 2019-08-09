package com.m.gis.springboot.service;

import com.alibaba.fastjson.JSONArray;
import com.m.gis.springboot.bo.RoadAnalysisBO;
import com.m.gis.springboot.bo.RoadResultBO;
import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.geo.base.utils.GisLineStringUtil;
import com.m.gis.springboot.mapper.GisAngolaRoadMapper;
import com.m.gis.springboot.qo.GisCameraPosQO;
import com.m.gis.springboot.qo.GisCameraRouteQO;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GisRouteHeatMapService.java
 * @Package com.m.gis.springboot.service.interfaces
 * @Description: 通过摄像头点位计算热力图分布
 * @author monkjavaer
 * @date 2018年1月3日 下午4:09:08
 * @version V1.0
 */
@Service
public class GisRouteHeatMapServiceImpl implements GisRouteHeatMapService{

	private static final Logger logger = LoggerFactory.getLogger(GisRouteHeatMapServiceImpl.class);

	@Autowired
	private GisAngolaRoadMapper gisAngolaRoadMapper;
	
	/**
	 * 与摄像头相交路网的容差值，单位米
	 */
	private double TOLERANCE = 30;
	
	private long MAX_POINTS_COUNT = 20000;
	
	private double DEFAULT_MIN_STEP = 0.00001;
	private double DEFAULT_MAX_STEP = 3*DEFAULT_MIN_STEP;

	@Override
	public List<GisCoordinate> getHeatMapPoints(GisCameraRouteQO qo) {
		
		List<GisCoordinate> result =  new ArrayList<GisCoordinate>();
		
		if(CollectionUtils.isEmpty(qo.getCameraList()))
			return result;
		
				
		//批量查询摄像头相交的路径数据，并通过json数组格式返回；
		String resultArrayJson = gisAngolaRoadMapper.selectByPointBatch(JsonUtils.toJSONArrayString(qo.getCameraList()), TOLERANCE);
		
		if(resultArrayJson == null)
			return result;
		
		JSONArray array = JsonUtils.toJSONArray(resultArrayJson);
		List<RoadResultBO> roadResultList =  JsonUtils.toBeanArray(resultArrayJson, RoadResultBO.class);
		
		//根据摄像头id聚类所有查询路径结果
		Map<String,RoadAnalysisBO> roadAnalysisMap = new HashMap<String,RoadAnalysisBO>();
		double totalRoadLength = 0;
		for(RoadResultBO item:roadResultList){
			if(roadAnalysisMap.containsKey(item.getId())){
				RoadAnalysisBO roadAnalysisBO = roadAnalysisMap.get(item.getId());

				MultiLineString geometry = null;
				try {
					geometry = (MultiLineString) GisGeometryFactoryUtil.createGeometryByWKT(item.getGeom());
					roadAnalysisBO.setTotalLength(roadAnalysisBO.getTotalLength() + geometry.getLength());
					roadAnalysisBO.getRoadList().add(geometry);

					totalRoadLength += geometry.getLength();
				} catch (GisParseGeometryBaseThrowableException e) {
					logger.error(e.toString());
				}
			}
			else{
				RoadAnalysisBO roadAnalysisBO = new RoadAnalysisBO();
				
				for(GisCameraPosQO cameraItem:qo.getCameraList()){
					if(cameraItem.getId().equals(item.getId())){
						roadAnalysisBO.setCount(cameraItem.getCount());
						break;
					}
				}
								
				List<MultiLineString> lines = new ArrayList<MultiLineString>();
				MultiLineString geometry = null;
				try {
					geometry = (MultiLineString) GisGeometryFactoryUtil.createGeometryByWKT(item.getGeom());
					roadAnalysisBO.setTotalLength(geometry.getLength());
					lines.add(geometry);
					
					totalRoadLength += geometry.getLength(); 
				} catch (GisParseGeometryBaseThrowableException e) {
					logger.error(e.toString());
				}
				roadAnalysisBO.setRoadList(lines);
				
				roadAnalysisMap.put(item.getId(),roadAnalysisBO);
			}
		}
		
		//计算合适的点间距，保证输出到前端的点不超过MAX_POINTS_COUNT
		double minStep = DEFAULT_MIN_STEP;
		double maxStep = DEFAULT_MAX_STEP;
		if(totalRoadLength/DEFAULT_MIN_STEP > MAX_POINTS_COUNT){
			minStep = totalRoadLength/MAX_POINTS_COUNT;
			maxStep = minStep*DEFAULT_MAX_STEP/DEFAULT_MIN_STEP;
		}
		
		
		//遍历得出流量统计的区间
		long minCount = Long.MAX_VALUE;
		long maxCount = Long.MIN_VALUE;
		long cameraCount = qo.getCameraList().size();
		for(Map.Entry<String,RoadAnalysisBO> entry:roadAnalysisMap.entrySet()){
			RoadAnalysisBO roadAnalysisBO = entry.getValue();
			long perCount = Math.round(roadAnalysisBO.getCount()/(double)roadAnalysisBO.getRoadList().size());
			roadAnalysisBO.setAverageCount(perCount);
			if(perCount>maxCount)
				maxCount = perCount;
			if(perCount<minCount)
				minCount = perCount;
		}
		
		
		for(Map.Entry<String,RoadAnalysisBO> entry:roadAnalysisMap.entrySet()){
			RoadAnalysisBO roadAnalysisBO = entry.getValue();
		
			//根据该摄像头流量计算实际的点密度；
			double delta = 0;
			if(maxCount==minCount)
				delta = minStep;
			else
				delta = maxStep - (maxStep-minStep)*(roadAnalysisBO.getAverageCount()-minCount)/(maxCount-minCount);
			logger.debug("-------------------------------------------------------------------------------------");
			logger.debug(entry.getKey()+"="+roadAnalysisBO.toString());
			//对每条路按照delta长度插值
			int count = 0;
			for(MultiLineString item:roadAnalysisBO.getRoadList()){
				for(int i=0;i<item.getNumGeometries();i++){
					LineString line = (LineString) item.getGeometryN(i);
					
					int numPoints = (int)Math.floor(line.getLength()/delta);
					for(int j=1;j<=numPoints;j++){
						//根据比例取路的一部分，为了热力图分布显得随机，加入10%的扰动值
						double fraction = j*delta/line.getLength()*(0.9+0.1*Math.random());					
						LineString seg = GisLineStringUtil.subLineString(line, fraction);
						result.add(new GisCoordinate(seg.getEndPoint().getX(),seg.getEndPoint().getY(),true));
						count++;
					}
				}
			}
			logger.debug("delta:"+delta+",points size:"+count);
		}
		logger.debug("-------------------------------------------------------------------------------------");
		logger.debug("total points size:"+result.size());
		return result;
	}
	
}

