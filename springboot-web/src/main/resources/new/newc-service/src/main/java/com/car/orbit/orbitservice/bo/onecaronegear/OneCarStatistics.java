package com.car.orbit.orbitservice.bo.onecaronegear;

import java.util.List;

//活跃点统计类
public class OneCarStatistics {
        //统计是哪一天
        private String whatDay;

        //统计的名称
        private List<OneCarStatisticsNameAndTime> stats;

    public OneCarStatistics(){}


    public String getWhatDay() {
        return whatDay;
    }

    public void setWhatDay(String whatDay) {
        this.whatDay = whatDay;
    }

    public List<OneCarStatisticsNameAndTime> getStats() {
        return stats;
    }

    public void setStats(List<OneCarStatisticsNameAndTime> stats) {
        this.stats = stats;
    }
}
