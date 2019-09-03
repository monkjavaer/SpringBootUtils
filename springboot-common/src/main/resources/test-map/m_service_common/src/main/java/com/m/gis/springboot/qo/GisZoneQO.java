package com.m.gis.springboot.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Title: GisCircleQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 定义区域空间查询QO
 * @author monkjavaer
 * @date 2017年11月30日 下午9:19:16
 * @version V1.0
 */
@ApiModel(value = "GisZoneQO", description = "区域查询")
public class GisZoneQO extends GisPageQO {
	
	@ApiModelProperty(value = "WKT格式的空间几何 【POLYGON((-70 -20, -70 -15, -60 -15, -60 -20, -70 -20))】")
//	@NotBlank(message = "poiZone can not be null or empty string. ")
	private String poiZone;
    
    public GisZoneQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GisZoneQO(String poiZone) {
		super();
		this.poiZone = poiZone;
	}
	
	public GisZoneQO(String poiZone, Integer pageIndex, Integer pageSize) {
		super(pageIndex, pageSize);
		this.poiZone = poiZone;
		// TODO Auto-generated constructor stub
	}

	public String getPoiZone() {
		return poiZone;
	}

	public void setPoiZone(String poiZone) {
		this.poiZone = poiZone;
	}

}

