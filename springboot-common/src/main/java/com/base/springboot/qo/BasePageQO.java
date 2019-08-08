package com.base.springboot.qo;

import com.base.springboot.constants.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * @Title: CommonQO
 * @Package: com.base.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Date: 2019/8/7 9:37
 * @Version: V1.0
 */
@ApiModel(value = "BasePageQO", description = "分页公共类")
public class BasePageQO {
    @ApiModelProperty(value = "当前页码，从1开始")
    @Min(value=1,message = "pageSize must equal or greater than 1")
    private Integer pageIndex = CommonConstants.PAGE_INDEX;
    @ApiModelProperty(value = "单页记录数")
    @Min(value=1,message = "pageSize must equal or greater than 1")
    private Integer pageSize = CommonConstants.PAGE_SIZE;

    public BasePageQO() {
        super();
    }

    public BasePageQO(Integer pageIndex, Integer pageSize) {
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
