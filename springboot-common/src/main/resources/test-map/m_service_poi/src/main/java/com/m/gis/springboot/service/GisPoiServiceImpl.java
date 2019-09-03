package com.m.gis.springboot.service;

import com.github.pagehelper.PageHelper;
import com.m.gis.springboot.common.GisPage;
import com.m.gis.springboot.exception.GisPoiServiceException;
import com.m.gis.springboot.geo.base.common.GisBaseConstants;
import com.m.gis.springboot.geo.base.exception.GisParseGeometryBaseThrowableException;
import com.m.gis.springboot.geo.base.utils.GisGeometryFactoryUtil;
import com.m.gis.springboot.mapper.GisPoiTMapper;
import com.m.gis.springboot.qo.GisPoiBufferQO;
import com.m.gis.springboot.qo.GisPoiZoneQO;
import com.m.gis.springboot.vo.BufferPoiPageVO;
import com.m.gis.springboot.vo.PoiVO;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: GisPoiServiceImpl.java
 * @Package com.m.gis.springboot.service.impls
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月30日 上午9:25:37
 * @version V1.0
 */
@Service
public class GisPoiServiceImpl implements GisPoiService{

	@Autowired
	private GisPoiTMapper gisPoiMapper;

	@Autowired
	private GisGeometryService gisGeometryService;
	
	@Override
	public GisPage<PoiVO> getPoiListByZone(GisPoiZoneQO qo) {
		// TODO Auto-generated method stub
	    PageHelper.startPage(qo.getPageIndex(), qo.getPageSize());
	    //geometry EPSG:4326
	    qo.setPoiZone(GisBaseConstants.DEFAULT_SRID_STRING + qo.getPoiZone());
	    List<PoiVO> list = gisPoiMapper.selectByZone(qo);
		return new GisPage(list);
	}

	@Override
	public BufferPoiPageVO<PoiVO> getPoiListByBufferZone(GisPoiBufferQO qo) {
		String geomWkt = qo.getGeom();
		try {
			//生成缓冲区图形
			Geometry geom = GisGeometryFactoryUtil.createGeometryByWKT(geomWkt);
			Geometry bufferGeom = gisGeometryService.getGeodesyBuffer(geom,qo.getRadius());

			//转换为空间范围进行查询
			GisPoiZoneQO zoneQO = qo.toGisPoiZoneQO();
			zoneQO.setPoiZone(bufferGeom.toString());

			GisPage<PoiVO> poiPage = getPoiListByZone(zoneQO);

			return new BufferPoiPageVO<PoiVO>(poiPage,bufferGeom.toString());
		} catch (GisParseGeometryBaseThrowableException e) {
			throw new GisPoiServiceException("parse geometry errors, geom params can not be cast to geometry.");
		}
	}
}

