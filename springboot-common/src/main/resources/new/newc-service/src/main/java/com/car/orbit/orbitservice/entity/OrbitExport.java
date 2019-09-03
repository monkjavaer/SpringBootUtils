package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_export")
public class OrbitExport {
    /**
     * 导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;
     */
    @Id
    @Column(name = "CODE")
    private String code;

    /**
     * 中文表头
     */
    @Column(name = "ZH_NAME")
    private String zhName;

    /**
     * 英文表头
     */
    @Column(name = "EN_NAME")
    private String enName;

    /**
     * 西班牙语表头
     */
    @Column(name = "ES_NAME")
    private String esName;

    /**
     * 获取导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;
     *
     * @return CODE - 导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;
     *
     * @param code 导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取中文表头
     *
     * @return ZH_NAME - 中文表头
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * 设置中文表头
     *
     * @param zhName 中文表头
     */
    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    /**
     * 获取英文表头
     *
     * @return EN_NAME - 英文表头
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 设置英文表头
     *
     * @param enName 英文表头
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 获取西班牙语表头
     *
     * @return ES_NAME - 西班牙语表头
     */
    public String getEsName() {
        return esName;
    }

    /**
     * 设置西班牙语表头
     *
     * @param esName 西班牙语表头
     */
    public void setEsName(String esName) {
        this.esName = esName;
    }
}