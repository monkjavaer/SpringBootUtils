package com.m.gis.springboot.qo;

import com.m.gis.springboot.annotation.StringSetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: GisNearPoiQO
 * @Package: com.m.gis.springboot.qo
 * @Description: 临近设施查询体
 * @Author: monkjavaer
 * @Data: 2018/11/8 10:00
 * @Version: V1.0
 */
@ApiModel(value = "GisNearPoiQO", description = "临近设施查询QO")
public class GisNearPoiQO {
    @ApiModelProperty(value = "中心点经度")
    @Range(min=-180, max=180, message="longitude must between -180 and 180. ")
    private Double longitude;

    @ApiModelProperty(value = "中心点纬度")
    @Range(min=-90, max=90, message="latitude must between -90 and 90. ")
    private Double latitude;

    @ApiModelProperty(value = "设施点类型")
    @StringSetType(String.class)
    private String poiType;

    @ApiModelProperty(value = "设施点数量,最多支持查询10个临近设施点")
    @Range(min=1, max=10, message="number  should be between 1 and 10 . ")
    private Integer number;

    @ApiModelProperty(value = "交通方式")
    private String transType;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPoiType() {
        return poiType;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
