package com.car.orbit.orbitservice.vo;

import com.car.orbit.orbitservice.util.redis.BrandRedis;

/**
 * @Title: VehicleSearchVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 按卡口和品牌子类型聚合查询共用
 * @Author: monkjavaer
 * @Data: 2019/3/18 15:59
 * @Version: V1.0
 */
public class VehicleSearchVO {
    /**
     * 点位id
     */
    private String pointId;
    /**
     * 点位名称
     */
    private String pointName;
    /**
     * 品牌类型编码
     */
    private String brand;
    /**
     * 品牌子类型编码
     */
    private String brandChild;
    /**
     * 品牌信息,包含品牌类型+品牌子类型
     */
    private String brandInfo;

    private Long count;

    public VehicleSearchVO() {
    }

    public VehicleSearchVO(String pointId, String pointName, Long count) {
        this.pointId = pointId;
        this.pointName = pointName;
        this.count = count;
    }

    public VehicleSearchVO(String childBrandCode, Long count) {
        this.brandChild = childBrandCode;
        this.brandInfo = BrandRedis.getFullBrandNameByCode(childBrandCode);
        this.count = count;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandChild() {
        return brandChild;
    }

    public void setBrandChild(String brandChild) {
        this.brandChild = brandChild;
    }

    public String getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(String brandInfo) {
        this.brandInfo = brandInfo;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
