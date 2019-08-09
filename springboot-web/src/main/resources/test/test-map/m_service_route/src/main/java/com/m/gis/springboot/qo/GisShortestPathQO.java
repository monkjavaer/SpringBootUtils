package com.m.gis.springboot.qo;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Title: GisShortestPathQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
@ApiModel(value = "GisShortestPathQO", description = "最短路径查询QO")
public class GisShortestPathQO {
    @ApiModelProperty(value = "经过点集合，至少包含两个点")
    @Valid // 嵌套验证必须用@Valid
    @NotNull(message = "passPoints params must not be null.")
    @Size(min = 2, message = "passPoints params must be at least 2 elements.")
    private List<GisCoordinate> passPoints;

    @ApiModelProperty(value = "障碍几何集合，wkt格式")
    private List<String> barriers;

    @ApiModelProperty(value = "出行模式，预留接口，暂未实现")
    private Integer transType;

    @ApiModelProperty(value = "语言标志：zh-CN中文,en-US英语,es-ES西班牙语")
    private String locale;

    public GisShortestPathQO() {
    }

    public GisShortestPathQO(@Valid @NotNull(message = "passPoints params must not be null.") @Size(min = 2, message = "passPoints params must be at least 2 elements.") List<GisCoordinate> passPoints, List<String> barriers, Integer transType, String locale) {
        this.passPoints = passPoints;
        this.barriers = barriers;
        this.transType = transType;
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<GisCoordinate> getPassPoints() {
        return passPoints;
    }

    public void setPassPoints(List<GisCoordinate> passPoints) {
        this.passPoints = passPoints;
    }

    public List<String> getBarriers() {
        return barriers;
    }

    public void setBarriers(List<String> barriers) {
        this.barriers = barriers;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }
}
