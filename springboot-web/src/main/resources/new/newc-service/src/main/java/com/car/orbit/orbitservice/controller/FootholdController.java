package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.*;
import com.car.orbit.orbitservice.qo.FootholdQO;
import com.car.orbit.orbitservice.service.IFootholdAnalysisService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: FootholdController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 落脚点分析Controller
 * @Author: monkjavaer
 * @Date: 2019/03/29 15:24
 * @Version: V1.0
 */
@RestController
@RequestMapping("/foothold")
public class FootholdController {

    @Autowired
    private IFootholdAnalysisService footholdAnalysisService;

    /**
     * 落脚点查询
     *
     * @param footholdQO
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestBody FootholdQO footholdQO) {
        VehicleSearchBO<FootholdBO> list = footholdAnalysisService.queryPageList(footholdQO);
        return ResultUtil.success(list);
    }

    /**
     * 查询落脚点详细
     *
     * @param footholdQO
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryFootholdDetail(@RequestBody FootholdQO footholdQO) {
        VehicleSearchBO<FootholdDetail> list = footholdAnalysisService.queryFootholdDetail(footholdQO);
        return ResultUtil.success(list);
    }

    /**
     * 对给定车辆最近7天、最近一个月、最近三个月的落脚点情况进行统计，并根据落脚总时长取Top10
     *
     * @param footholdQO
     * @return
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult statisticsFoothold(@RequestBody FootholdQO footholdQO) {
        FootholdStatisticsBO statisticsBO = footholdAnalysisService.footholdStatistics(footholdQO);
        return ResultUtil.success(statisticsBO);
    }

    /**
     * 获取指定车辆在一定时间范围内的落脚时长排行，包括总落脚时长排行，日间落脚时长排行，夜间落脚时长排行，并分别取Top10
     *
     * @param footholdQO
     * @return
     */
    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult statisticsRank(@RequestBody FootholdQO footholdQO) {
        FootholdMapBO mapBO = footholdAnalysisService.footholdRank(footholdQO);
        return ResultUtil.success(mapBO);
    }
}
