package com.car.orbit.orbitservice.bo.onecaronegear;

import java.util.List;

/**
 * @Title: TacticsOneCarOneGearTimeStat
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 一车一档时间点结果返回
 * @Author: monkjavaer
 * @Data: 2019/3/29 10:23
 * @Version: V1.0
 */
public class TacticsOneCarOneGearTimeStat {
    //天时间点统计数据信息
    private List<OneCarStatistics> day;

    //周时间点统计数据信息
    private List<OneCarStatistics> week;

    //月时间点统计数据信息
    private List<OneCarStatistics> month;

    //3个月时间点统计数据信息
    private List<OneCarStatistics> threeMonth;

    public TacticsOneCarOneGearTimeStat(){}

    public List<OneCarStatistics> getDay() {
        return day;
    }

    public void setDay(List<OneCarStatistics> day) {
        this.day = day;
    }

    public List<OneCarStatistics> getWeek() {
        return week;
    }

    public void setWeek(List<OneCarStatistics> week) {
        this.week = week;
    }

    public List<OneCarStatistics> getMonth() {
        return month;
    }

    public void setMonth(List<OneCarStatistics> month) {
        this.month = month;
    }

    public List<OneCarStatistics> getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(List<OneCarStatistics> threeMonth) {
        this.threeMonth = threeMonth;
    }
}
