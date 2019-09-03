package com.m.gis.springboot.qo;

import com.m.gis.springboot.enums.GisAddressTypeEnums;
import com.m.gis.springboot.validator.GisAddressTypeConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Title: GisAddressNameElementQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/14
 * @Version: V1.0
 */
@ApiModel(value = "GisAddressNameElementQO", description = "")
public class GisAddressNameElementQO extends GisCoordinateQO {
    @ApiModelProperty(value = "地名名称")
    @NotBlank(message = "name can not be null or empty string. ")
    private String name;
    @ApiModelProperty(value = "地名类型")
    @GisAddressTypeConstraint(message = "gis address type is illegal. ")
    private String type = GisAddressTypeEnums.USER.getValue();

    public GisAddressNameElementQO() {
        super();
    }

    public GisAddressNameElementQO(String name, double longitude, double latitude) {
        super(longitude, latitude);
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}