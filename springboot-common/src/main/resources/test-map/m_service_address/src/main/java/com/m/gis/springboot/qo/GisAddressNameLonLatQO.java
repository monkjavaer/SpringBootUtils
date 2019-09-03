package com.m.gis.springboot.qo;

import com.m.gis.springboot.common.GisAddressConstants;
import com.m.gis.springboot.utils.ConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

/**
 * @Title: GisAddressNameQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
@ApiModel(value = "GisAddressNameLonLatQO", description = "")
public class GisAddressNameLonLatQO extends GisCoordinateQO {

    @ApiModelProperty(value = "行政区域编码，只查询该行政区域内的地名，若为空，则默认查所有")
    private String districtCode;

    @ApiModelProperty(value = "地名类型，支持01,02的拼接方式")
    private String type = GisAddressConstants.DEFAULT_LONLAT_QUERY_ADDRESS_TYPE;

    @ApiModelProperty(value = "限定的行政区域编码，如果经纬度超出该行政区域，则返回的status字段为1，表示地址超出数据范围")
    private String restrictionCode;

    @ApiModelProperty(value = "容差值，单位为米")
    private Double tolerance;

    public GisAddressNameLonLatQO() {
        super();
    }

    public GisAddressNameLonLatQO(double longitude, double latitude, String districtCode, String type, String restrictionCode, Double tolerance) {
        super(longitude, latitude);
        this.districtCode = districtCode;
        this.type = type;
        this.restrictionCode = restrictionCode;
        this.tolerance = tolerance;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Set<String> getType() {
        return ConvertUtil.string2Set(String.class, this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRestrictionCode() {
        return restrictionCode;
    }

    public void setRestrictionCode(String restrictionCode) {
        this.restrictionCode = restrictionCode;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }
}
