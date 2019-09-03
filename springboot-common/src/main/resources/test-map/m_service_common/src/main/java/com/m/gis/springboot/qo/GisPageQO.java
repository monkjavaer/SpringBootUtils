package com.m.gis.springboot.qo;

import com.m.gis.springboot.common.GisServiceConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * @Title: GisPageQO.java
 * @Package com.m.gis.springboot.qo
 * @Description: 定义分页查询QO
 * @author monkjavaer
 * @date 2017年11月28日 上午11:50:46
 * @version V1.0
 */
@ApiModel(value = "GisPageQO", description = "分页")
public class GisPageQO extends GisBaseQO {
    @ApiModelProperty(value = "当前页码，从1开始")
	@Min(value=1,message = "pageSize must equal or greater than 1")
	private Integer pageIndex = GisServiceConstants.PAGE_INDEX;
    @ApiModelProperty(value = "单页记录数")
	@Min(value=1,message = "pageSize must equal or greater than 1")
	private Integer pageSize = GisServiceConstants.PAGE_SIZE;
	public GisPageQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GisPageQO(Integer pageIndex, Integer pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}

