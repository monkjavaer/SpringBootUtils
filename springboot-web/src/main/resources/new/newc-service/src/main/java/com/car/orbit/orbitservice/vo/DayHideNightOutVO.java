package com.car.orbit.orbitservice.vo;

/**
 * @Title: DayHideNightOutVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 昼伏夜出 + 夜间频出
 * @Author: zks
 * @Date: 2019/03/25 15:55
 * @Version: V1.0
 */
public class DayHideNightOutVO extends TacticsVehicleBaseInfo {

    /**
     * 总数量
     */
    private String allCount;
    /**
     * 白天数量
     */
    private String dayCount;
    /**
     * 夜间数量
     */
    private String nightCount;

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getNightCount() {
        return nightCount;
    }

    public void setNightCount(String nightCount) {
        this.nightCount = nightCount;
    }
}
