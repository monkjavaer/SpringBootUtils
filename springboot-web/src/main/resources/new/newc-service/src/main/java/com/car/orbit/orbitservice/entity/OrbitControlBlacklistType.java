package com.car.orbit.orbitservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "orbit_control_blacklist_type")
public class OrbitControlBlacklistType {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 类型名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 报警级别（1：严重，2高，3中，4低）
     */
    @Column(name = "LEVEL")
    private Integer level;

    /**
     * 报警铃声(1.明亮，2，高昂，3.平和，4.低层)
     */
    @Column(name = "BELL")
    private Integer bell;

    /**
     * 类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 是否已删除
     */
    @Column(name = "DELETED")
    private Integer deleted;

    /**
     * 是否可删除
     */
    @Transient
    private boolean deletable;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取类型名称
     *
     * @return NAME - 类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类型名称
     *
     * @param name 类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取报警级别（1：严重，2高，3中，4低）
     *
     * @return LEVEL - 报警级别（1：严重，2高，3中，4低）
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置报警级别（1：严重，2高，3中，4低）
     *
     * @param level 报警级别（1：严重，2高，3中，4低）
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取报警铃声(1.明亮，2，高昂，3.平和，4.低层)
     *
     * @return BELL - 报警铃声(1.明亮，2，高昂，3.平和，4.低层)
     */
    public Integer getBell() {
        return bell;
    }

    /**
     * 设置报警铃声(1.明亮，2，高昂，3.平和，4.低层)
     *
     * @param bell 报警铃声(1.明亮，2，高昂，3.平和，4.低层)
     */
    public void setBell(Integer bell) {
        this.bell = bell;
    }

    /**
     * 获取类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)
     *
     * @return TYPE - 类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)
     *
     * @param type 类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)
     */
    public void setType(String type) {
        this.type = type;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}