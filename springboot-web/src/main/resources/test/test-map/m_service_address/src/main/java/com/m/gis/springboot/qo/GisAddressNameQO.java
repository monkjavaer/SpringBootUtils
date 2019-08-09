package com.m.gis.springboot.qo;

import com.m.gis.springboot.utils.ConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @Title: GisAddressNameQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
@ApiModel(value = "GisAddressNameQO", description = "")
public class GisAddressNameQO extends GisPageQO {

    @ApiModelProperty(value = "模糊匹配的名称")
    @NotBlank(message = "name can not be null or empty string. ")
    private String name;

    @ApiModelProperty(value = "行政区域编码，只查询该行政区域内的地名，若为空，则默认查所有")
    private String districtCode;

    @ApiModelProperty(value = "地名类型，支持01,02的拼接方式")
    private String type;

    /** 分词临时查询参数,内部service使用,用于传递至mapper,避免beanCopy */
    @ApiModelProperty(hidden = true)
    private String searchParam;

    public GisAddressNameQO() {
        super();
    }

    public GisAddressNameQO(String name, String districtCode, String type, Integer pageIndex, Integer pageSize) {
        super(pageIndex, pageSize);
        this.name = name;
        this.districtCode = districtCode;
        this.type = type;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
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

    public Set<String> getType() {
        return ConvertUtil.string2Set(String.class, this.type);
    }

    public void setType(String type) {
        this.type = type;
    }
}
