package com.m.gis.springboot.qo;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: GisPoiQueryQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 兴趣点空间查询QO
 * @author monkjavaer
 * @date 2017年11月28日 上午11:25:43
 * @version V1.0
 */
@ApiModel(value = "GisCoordinateQO", description = "")
public class GisCoordinateQO extends GisBaseQO {
    @ApiModelProperty(value = "查询点经度", notes = "")
    @Range(min=-180, max=180, message="longtitude must between -180 and 180. ")
    private double longitude;
    
    @ApiModelProperty(value = "查询点纬度", notes = "")
    @Range(min=-90, max=90, message="latitude must between -90 and 90. ")
    private double latitude;
    
	public GisCoordinateQO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GisCoordinateQO(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
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

	public GisCoordinate toGisCoordinate(){
		return new GisCoordinate(longitude,latitude);
	}
}

