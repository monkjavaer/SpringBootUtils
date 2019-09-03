package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @Title: FootholdMapBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/04/04 15:04
 * @Version: V1.0
 */
public class FootholdMapBO {

    /**
     * 总落脚时长排行
     */
    private List<FootholdRankBO> totalHourRank;
    /**
     * 日间落脚时长排行
     */
    private List<FootholdRankBO> daytimeRank;
    /**
     * 夜间落脚时长排行
     */
    private List<FootholdRankBO> nightRank;

    public List<FootholdRankBO> getTotalHourRank() {
        return totalHourRank;
    }

    public void setTotalHourRank(List<FootholdRankBO> totalHourRank) {
        this.totalHourRank = totalHourRank;
    }

    public List<FootholdRankBO> getDaytimeRank() {
        return daytimeRank;
    }

    public void setDaytimeRank(List<FootholdRankBO> daytimeRank) {
        this.daytimeRank = daytimeRank;
    }

    public List<FootholdRankBO> getNightRank() {
        return nightRank;
    }

    public void setNightRank(List<FootholdRankBO> nightRank) {
        this.nightRank = nightRank;
    }
}
