package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.bo.*;
import com.car.orbit.orbitservice.qo.FootholdQO;

/**
 * @Title: IFootholdAnalysisService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 落脚点分析服务接口
 * @Author: monkjavaer
 * @Date: 2019/03/28 11:13
 * @Version: V1.0
 */
public interface IFootholdAnalysisService {

    /**
     * 落脚点分析
     *
     * @param footholdQO
     * @return
     */
    VehicleSearchBO<FootholdBO> queryPageList(FootholdQO footholdQO);

    /**
     * 查询落脚点详细
     *
     * @param footholdQO
     * @return
     */
    VehicleSearchBO<FootholdDetail> queryFootholdDetail(FootholdQO footholdQO);

    /**
     * 对给定车辆最近7天、最近一个月、最近三个月的落脚点情况进行统计，并根据落脚总时长取Top10
     *
     * @param footholdQO
     * @return
     */
    FootholdStatisticsBO footholdStatistics(FootholdQO footholdQO);

    /**
     * 获取指定车辆在一定时间范围内的落脚时长排行，包括总落脚时长排行，日间落脚时长排行，夜间落脚时长排行，并分别取Top10
     *
     * @param footholdQO
     * @return
     */
    FootholdMapBO footholdRank(FootholdQO footholdQO);
}
