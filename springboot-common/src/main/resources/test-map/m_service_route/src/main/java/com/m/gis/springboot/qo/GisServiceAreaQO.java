package com.m.gis.springboot.qo;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Title: GisShortestPathQO
 * @Package: com.m.gis.springboot.qo
 * @Description: 服务范围查询体
 * @Author: monkjavaer
 * @Data: 2018/7/24
 * @Version: V1.0
 */
@ApiModel(value = "GisServiceAreaQO", description = "最短路径查询QO")
public class GisServiceAreaQO {
    @ApiModelProperty(value = "起始点集合")
    @Valid // 嵌套验证必须用@Valid
    @NotNull(message = "{startPoints} params must not be null.")
    @Size(min = 1, max = 3,message = "{startPoints} params must be at least 1 elements and less than 4.")
    private List<GisCoordinate> startPoints;

    @ApiModelProperty(value = "服务成本")
    @Positive(message = "{cost} params must be positive.")
    private Double cost;

    @ApiModelProperty(value = "服务成本类型,0距离,1时间")
    private Integer type;

    @ApiModelProperty(value = "交通方式")
    private String transType;

    public GisServiceAreaQO() {
    }

    public List<GisCoordinate> getStartPoints() {
        return startPoints;
    }

    public void setStartPoints(List<GisCoordinate> startPoints) {
        this.startPoints = startPoints;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
