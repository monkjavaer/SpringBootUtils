package com.m.gis.springboot.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @Title: GisPoiQueryQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 定义包含坐标参数的查询QO
 * @author monkjavaer
 * @date 2017年11月28日 上午11:25:43
 * @version V1.0
 */
@ApiModel(value = "GisCoordinateQO", description = "")
public class GisCoordinatePageQO extends GisPageQO{
    @ApiModelProperty(value = "查询点id标识")
    @NotBlank(message = "id can not be null or empty string. ")
    private String id;

    @ApiModelProperty(value = "查询点经度", notes = "")
    @Range(min=-180, max=180, message="longtitude must between -180 and 180. ")
    private double longitude;
    
    @ApiModelProperty(value = "查询点纬度", notes = "")
    @Range(min=-90, max=90, message="latitude must between -90 and 90. ")
    private double latitude;
    
	public GisCoordinatePageQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GisCoordinatePageQO(String id, double longitude, double latitude) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}

