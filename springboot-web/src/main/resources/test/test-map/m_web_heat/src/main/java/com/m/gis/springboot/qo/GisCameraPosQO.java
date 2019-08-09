package com.m.gis.springboot.qo;

import com.m.gis.springboot.qo.GisCoordinateQO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 * @Title: GetAddressNameListQO.java
 * @Package com.m.gis.web.address.qo
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年9月9日 下午10:23:07
 * @version V1.0
 */
public class GisCameraPosQO extends GisCoordinateQO {

	@ApiModelProperty(value = "查询点id标识")
	@NotBlank(message = "id can not be null or empty string. ")
	private String id;

    @ApiModelProperty(value = "视频点的统计流量", notes = "")
    @Min(value=0, message="count must be large than 0. ")
	private Long count;

	public GisCameraPosQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GisCameraPosQO(String id, double longitude, double latitude, Long count) {
		super(longitude, latitude);
		this.id = id;
		this.count = count;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}

