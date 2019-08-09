package com.car.orbit.orbitservice.entity;

import javax.persistence.*;

@Table(name = "orbit_control_alarm_voice")
public class OrbitControlAlarmVoice {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 报警级别（1、一级，2、二级，3、三级，4、四级
     */
    @Column(name = "LEVEL")
    private Integer level;

    /**
     * 报警铃声（1、明亮，2、高昂，3、平和，4、低沉）
     */
    @Column(name = "BELL")
    private Integer bell;

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
     * 获取报警级别（1、一级，2、二级，3、三级，4、四级
     *
     * @return LEVEL - 报警级别（1、一级，2、二级，3、三级，4、四级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置报警级别（1、一级，2、二级，3、三级，4、四级
     *
     * @param level 报警级别（1、一级，2、二级，3、三级，4、四级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取报警铃声（1、明亮，2、高昂，3、平和，4、低沉）
     *
     * @return BELL - 报警铃声（1、明亮，2、高昂，3、平和，4、低沉）
     */
    public Integer getBell() {
        return bell;
    }

    /**
     * 设置报警铃声（1、明亮，2、高昂，3、平和，4、低沉）
     *
     * @param bell 报警铃声（1、明亮，2、高昂，3、平和，4、低沉）
     */
    public void setBell(Integer bell) {
        this.bell = bell;
    }
}