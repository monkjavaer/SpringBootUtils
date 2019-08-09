package com.m.gis.springboot.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.utils.PostgisUtil;
import org.postgis.Point;

/**
 * @Title: GisAddressNameVO
 * @Package: com.m.gis.springboot.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
public class GisAddressNameVO extends GisCoordinate {
    private Integer gid;
    private String name;
    private String districtCode;
    private String type;

    @JSONField(serialize=false)
    private Point geom;

    /**
     * 地址表中address字段
     */
    private String address;

    /**
     * 拼接：中国-四川省-成都市-青羊区
     */
    private String district;

    public GisAddressNameVO() {
    }

    public GisAddressNameVO(Integer gid, String name, String districtCode, String type, Point geom) {
        super();
        this.gid = gid;
        this.name = name;
        this.districtCode = districtCode;
        this.type = type;
        this.geom = geom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
        this.setLongitude(PostgisUtil.fromPoint(geom,true).getLongitude());
        this.setLatitude(PostgisUtil.fromPoint(geom,true).getLatitude());
    }
}

