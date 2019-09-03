package com.car.orbit.orbitservice.bo.onecaronegear;

import java.util.List;

/**
 * @Title: TacticsOneCarOneGearActiveAreaStat
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 一车一档活跃区域结果返回
 * @Author: monkjavaer
 * @Data: 2019/3/29 10:23
 * @Version: V1.0
 */
public class TacticsOneCarOneGearActiveAreaStat {

    //周活跃区域统计数据信息
    private List<OneCarActiveStat> week;

    //月活跃区域统计数据信息
    private List<OneCarActiveStat> month;

    //3个月活跃区域统计数据信息
    private List<OneCarActiveStat> threeMonth;
    public TacticsOneCarOneGearActiveAreaStat(){}

    public List<OneCarActiveStat> getWeek() {
        return week;
    }

    public void setWeek(List<OneCarActiveStat> week) {
        this.week = week;
    }

    public List<OneCarActiveStat> getMonth() {
        return month;
    }

    public void setMonth(List<OneCarActiveStat> month) {
        this.month = month;
    }

    public List<OneCarActiveStat> getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(List<OneCarActiveStat> threeMonth) {
        this.threeMonth = threeMonth;
    }


}
