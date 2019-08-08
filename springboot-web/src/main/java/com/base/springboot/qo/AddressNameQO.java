package com.base.springboot.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Title: AddressNameQO
 * @Package: com.base.springboot.qo
 * @Description: 名字查询体
 * @Author: monkjavaer
 * @Date: 2019/8/7 9:35
 * @Version: V1.0
 */
@ApiModel(value = "AddressNameQO", description = "地址名查询QO")
public class AddressNameQO extends BasePageQO {
    @ApiModelProperty(value = "模糊匹配的名称")
    @NotBlank(message = "name can not be null or empty string. ")
    private String name;

    @ApiModelProperty(value = "行政区域编码，若为空，则默认查所有")
    private String districtCode;

    public AddressNameQO() {
        super();
    }

    public AddressNameQO(Integer pageIndex, Integer pageSize, String name, String districtCode) {
        super(pageIndex, pageSize);
        this.name = name;
        this.districtCode = districtCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
