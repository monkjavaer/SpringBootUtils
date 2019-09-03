package com.car.orbit.orbitservice.bo.onecaronegear;

public class OneCarStatisticsNameAndTime {

    //统计的名称
    private String statKey;

    //出现的次数
    private String times;

    //出现的范围
    private String statRange;

    public OneCarStatisticsNameAndTime(){}

    public String getStatKey() {
        return statKey;
    }

    public void setStatKey(String statKey) {
        this.statKey = statKey;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStatRange() {
        return statRange;
    }

    public void setStatRange(String statRange) {
        this.statRange = statRange;
    }
}
