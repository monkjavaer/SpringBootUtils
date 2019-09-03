package com.m.gis.springboot.qo;

import com.m.gis.springboot.common.utils.JsonUtils;


/**
 * @Title: GisBaseQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 用于定义查询QO的基类
 * @author monkjavaer
 * @date 2017年11月30日 下午10:30:29
 * @version V1.0
 */
public class GisBaseQO {
	public GisBaseQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return JsonUtils.toJSONString(this);
	}
		
}

