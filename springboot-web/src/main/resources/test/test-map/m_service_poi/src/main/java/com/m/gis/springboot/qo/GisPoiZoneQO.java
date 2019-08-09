package com.m.gis.springboot.qo;

import com.m.gis.springboot.annotation.StringSetType;
import com.m.gis.springboot.utils.ConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

/**
 * @Title: GisPoiQueryQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 兴趣点空间查询QO
 * @author monkjavaer
 * @date 2017年11月28日 上午11:25:43
 * @version V1.0
 */
@ApiModel(value = "GisPoiZoneQO", description = "兴趣点空间范围查询结构体")
public class GisPoiZoneQO extends GisZoneQO {

	@ApiModelProperty(value = "兴趣点名称，支持模糊匹配")
	private String poiName;

    @ApiModelProperty(value = "兴趣点类型，支持01,02形式的字符串拼接查询")
	@StringSetType(String.class)
	private String poiTypes;

	public GisPoiZoneQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GisPoiZoneQO(String poiName, String poiTypes, String poiZone, Integer pageIndex, Integer pageSize) {
		super(poiZone, pageIndex, pageSize);
		this.poiName = poiName;
		this.poiTypes = poiTypes;
	}

	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public Set<String> getPoiTypes() {
		return ConvertUtil.string2Set(String.class, this.poiTypes);
	}

	public void setPoiTypes(String poiTypes) {
		this.poiTypes = poiTypes;
	}

/*	public Set<String> getPoiTypesSet(){
		return ConvertUtil.string2Set(String.class, this.poiTypes);
	}*/
}

