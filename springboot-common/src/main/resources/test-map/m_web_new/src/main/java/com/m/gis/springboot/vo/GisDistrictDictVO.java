package com.m.gis.springboot.vo;

/**
 * @Title: GisDistrictDictVO
 * @Package: com.m.gis.springboot.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/29
 * @Version: V1.0
 */
public class GisDistrictDictVO {
    private String districtId;
    private String parentId;
    private String districtShortName;
    private String districtFullName;

    public GisDistrictDictVO() {
    }

    public GisDistrictDictVO(String districtId, String parentId, String districtShortName, String districtFullName) {
        this.districtId = districtId;
        this.parentId = parentId;
        this.districtShortName = districtShortName;
        this.districtFullName = districtFullName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDistrictShortName() {
        return districtShortName;
    }

    public void setDistrictShortName(String districtShortName) {
        this.districtShortName = districtShortName;
    }

    public String getDistrictFullName() {
        return districtFullName;
    }

    public void setDistrictFullName(String districtFullName) {
        this.districtFullName = districtFullName;
    }
}

