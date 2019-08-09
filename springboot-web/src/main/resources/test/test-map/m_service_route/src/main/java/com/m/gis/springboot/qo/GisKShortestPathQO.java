package com.m.gis.springboot.qo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * @Title: GisKShortestPathQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/1
 * @Version: V1.0
 */
public class GisKShortestPathQO {
    @ApiModelProperty(value = "起点经度")
    @Range(min=-180, max=180, message="longitude must between -180 and 180. ")
    private Double startLongitude;

    @ApiModelProperty(value = "起点纬度")
    @Range(min=-90, max=90, message="latitude must between -90 and 90. ")
    private Double startLatitude;

    @ApiModelProperty(value = "终点经度")
    @Range(min=-180, max=180, message="longitude must between -180 and 180. ")
    private Double endLongitude;

    @ApiModelProperty(value = "终点纬度")
    @Range(min=-90, max=90, message="latitude must between -90 and 90. ")
    private Double endLatitude;

    @ApiModelProperty(value = "终点纬度")
    @Range(min=1, max=3, message="pathCount must between 1 and 3. ")
    private Integer pathCount;

    @ApiModelProperty(value = "障碍几何集合，wkt格式")
    private List<String> barriers;

    @ApiModelProperty(value = "交通方式")
    private String transType;

    @ApiModelProperty(value = "语言标志：zh-CN中文,en-US英语,es-ES西班牙语")
    private String locale;

    public GisKShortestPathQO() {
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public List<String> getBarriers() {
        return barriers;
    }

    public void setBarriers(List<String> barriers) {
        this.barriers = barriers;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getPathCount() {
        return pathCount;
    }

    public void setPathCount(Integer pathCount) {
        this.pathCount = pathCount;
    }
}
