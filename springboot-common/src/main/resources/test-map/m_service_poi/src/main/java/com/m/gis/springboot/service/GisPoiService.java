package com.m.gis.springboot.service;

import com.m.gis.springboot.common.GisPage;
import com.m.gis.springboot.qo.GisPoiBufferQO;
import com.m.gis.springboot.qo.GisPoiZoneQO;
import com.m.gis.springboot.vo.BufferPoiPageVO;
import com.m.gis.springboot.vo.PoiVO;

/**
 * @Title: GisPoiService.java
 * @Package com.m.gis.springboot.service
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月24日 下午5:56:35
 * @version V1.0
 */
public interface GisPoiService {

	/***
	 * @Description: 查询空间范围内的兴趣点
	 * @Author: monkjavaer
	 * @Data: 14:26 2018/6/14
	 * @Param: [qo]
	 * @Throws
	 * @Return com.m.gis.springboot.common.GisPage<com.m.gis.springboot.vo.PoiVO>
	 */
	GisPage<PoiVO> getPoiListByZone(GisPoiZoneQO qo);

	/***
	 * @Description: 查询缓冲区范围内的兴趣点
	 * @Author: monkjavaer
	 * @Data: 13:31 2018/6/15
	 * @Param: [qo]
	 * @Throws
	 * @Return com.m.gis.springboot.vo.BufferPoiPageVO<com.m.gis.springboot.vo.PoiVO>
	 */
	BufferPoiPageVO<PoiVO> getPoiListByBufferZone(GisPoiBufferQO qo);

}

