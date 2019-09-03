package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @Title: FootholdStatisticsBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 落脚点统计结果
 * @Author: monkjavaer
 * @Date: 2019/04/02 15:53
 * @Version: V1.0
 */
public class FootholdStatisticsBO {

    private List<FootholdBO> lastWeek;
    private List<FootholdBO> lastMonth;
    private List<FootholdBO> lastThreeMonths;

    public FootholdStatisticsBO() {

    }

    public FootholdStatisticsBO(List<FootholdBO> lastWeek, List<FootholdBO> lastMonth, List<FootholdBO> lastThreeMonths) {
        this.lastWeek = lastWeek;
        this.lastMonth = lastMonth;
        this.lastThreeMonths = lastThreeMonths;
    }

    public List<FootholdBO> getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(List<FootholdBO> lastWeek) {
        this.lastWeek = lastWeek;
    }

    public List<FootholdBO> getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(List<FootholdBO> lastMonth) {
        this.lastMonth = lastMonth;
    }

    public List<FootholdBO> getLastThreeMonths() {
        return lastThreeMonths;
    }

    public void setLastThreeMonths(List<FootholdBO> lastThreeMonths) {
        this.lastThreeMonths = lastThreeMonths;
    }
}
