package com.car.orbit.orbitservice.bo.onecaronegear;

import com.car.orbit.orbitservice.bo.FootholdStatisticsBO;

/**
 * @Title: TacticsOneCarOneGearInfo
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 一车一档结果返回
 * @Author: monkjavaer
 * @Data: 2019/3/29 10:23
 * @Version: V1.0
 */
public class TacticsOneCarOneGearInfo {

    //一车一档的车辆基础信息
    private TacticsOneCarOneGearBaseInfo baseInfo;

    //一车一档的车辆可疑点统计信息
    private FootholdStatisticsBO suspectedStat;

    //一车一档的车辆活跃点统计信息
    private TacticsOneCarOneGearActivePointStat activePointStat;

    //一车一档的车辆活跃区域统计信息
    private TacticsOneCarOneGearActiveAreaStat activeAreaStat;

    //一车一档的车辆活跃区域统计信息
    private TacticsOneCarOneGearTimeStat timeStat;

    //一车一档的车辆活跃区域统计信息
    private TacticsOneCarOneGearTimeStat threeOfMonthTimeStat;

    public TacticsOneCarOneGearTimeStat getThreeOfMonthTimeStat() {
        return threeOfMonthTimeStat;
    }

    public void setThreeOfMonthTimeStat(TacticsOneCarOneGearTimeStat threeOfMonthTimeStat) {
        this.threeOfMonthTimeStat = threeOfMonthTimeStat;
    }

    public TacticsOneCarOneGearInfo(){}

    public TacticsOneCarOneGearBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(TacticsOneCarOneGearBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public FootholdStatisticsBO getSuspectedStat() {
        return suspectedStat;
    }

    public void setSuspectedStat(FootholdStatisticsBO suspectedStat) {
        this.suspectedStat = suspectedStat;
    }

    public TacticsOneCarOneGearActivePointStat getActivePointStat() {
        return activePointStat;
    }

    public void setActivePointStat(TacticsOneCarOneGearActivePointStat activePointStat) {
        this.activePointStat = activePointStat;
    }

    public TacticsOneCarOneGearActiveAreaStat getActiveAreaStat() {
        return activeAreaStat;
    }

    public void setActiveAreaStat(TacticsOneCarOneGearActiveAreaStat activeAreaStat) {
        this.activeAreaStat = activeAreaStat;
    }

    public TacticsOneCarOneGearTimeStat getTimeStat() {
        return timeStat;
    }

    public void setTimeStat(TacticsOneCarOneGearTimeStat timeStat) {
        this.timeStat = timeStat;
    }
}
