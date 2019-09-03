package com.m.gis.springboot.qo;

import com.m.gis.springboot.annotation.StringSetType;
import com.m.gis.springboot.utils.ConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @Title: GisPoiQueryQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 兴趣点空间查询QO
 * @author monkjavaer
 * @date 2017年11月28日 上午11:25:43
 * @version V1.0
 */
@ApiModel(value = "GisPoiBufferQO", description = "兴趣点缓冲范围查询结构体")
public class GisPoiBufferQO extends GisPageQO {

	@ApiModelProperty(value = "兴趣点名称，支持模糊匹配")
	private String poiName;

    @ApiModelProperty(value = "兴趣点类型，支持01,02形式的字符串拼接查询")
	@StringSetType(String.class)
	private String poiTypes;

	@ApiModelProperty(value = "几何图形，WKT格式，如POINT(-16 -68)")
	@NotBlank(message = "geom can not be null or empty string. ")
	private String geom;

	@ApiModelProperty(value = "缓冲半径，范围为100至100000，单位米")
	@Range(min=100, max=100000, message="buffer radius should be between 100 and 100000 meters. ")
	private Integer radius;

	public GisPoiBufferQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GisPoiBufferQO(Integer pageIndex, Integer pageSize, String poiName, String poiTypes, String geom, Integer radius) {
		super(pageIndex, pageSize);
		this.poiName = poiName;
		this.poiTypes = poiTypes;
		this.geom = geom;
		this.radius = radius;
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

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public GisPoiZoneQO toGisPoiZoneQO(){
		GisPoiZoneQO zoneQO = new GisPoiZoneQO();
		zoneQO.setPoiName(this.poiName);
		zoneQO.setPoiTypes(this.poiTypes);
		zoneQO.setPageIndex(this.getPageIndex());
		zoneQO.setPageSize(this.getPageSize());
		return zoneQO;
	}

/*	public Set<String> getPoiTypesSet(){
		return ConvertUtil.string2Set(String.class, this.poiTypes);
	}*/
}

