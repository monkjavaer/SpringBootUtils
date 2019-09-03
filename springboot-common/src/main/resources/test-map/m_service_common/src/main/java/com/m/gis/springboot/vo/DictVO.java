package com.m.gis.springboot.vo;
/**
 * @Title: PoiTypeVO.java
 * @Package com.m.gis.springboot.vo
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月24日 上午11:35:57
 * @version V1.0
 */
public class DictVO {
	public String type;
	public String name;
	public DictVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DictVO(String poiType, String poiName) {
		super();
		this.type = poiType;
		this.name = poiName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

