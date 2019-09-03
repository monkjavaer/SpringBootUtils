package com.car.orbit.orbitservice.vo;

public class AreaVO {
    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 行政区域编码
     */
    private String adminRegionCode;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 是否可删除
     */
    private Boolean canDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminRegionCode() {
        return adminRegionCode;
    }

    public void setAdminRegionCode(String adminRegionCode) {
        this.adminRegionCode = adminRegionCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
}