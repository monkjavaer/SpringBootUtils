package com.m.gis.springboot.vo;

import com.m.gis.springboot.common.GisPage;

/**
 * @Title: PoiVO.java
 * @Package com.m.gis.springboot.vo
 * @Description: 返回给前端的缓冲区查询poi vo
 * @author monkjavaer
 * @date 2017年11月30日 上午11:54:20
 * @version V1.0
 */
public class BufferPoiPageVO<T> extends GisPage<T> {
	private String bufferGeom;

	public BufferPoiPageVO(String bufferGeom) {
		this.bufferGeom = bufferGeom;
	}

	public BufferPoiPageVO(GisPage page, String bufferGeom) {
		super(page);
		this.bufferGeom = bufferGeom;
	}

	public String getBufferGeom() {
		return bufferGeom;
	}

	public void setBufferGeom(String bufferGeom) {
		this.bufferGeom = bufferGeom;
	}
}

