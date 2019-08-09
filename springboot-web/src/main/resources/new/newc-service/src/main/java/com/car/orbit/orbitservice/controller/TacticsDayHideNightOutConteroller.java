package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.DayHideNightOutQO;
import com.car.orbit.orbitservice.service.ITacticsDayHideNightOutService;
import com.car.orbit.orbitservice.vo.DayHideNightOutVO;
import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: TacticsDayHideNightOutConteroller
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 战法研判==之==昼伏夜出+夜间频出Controller
 * @Author: @author zks
 * @Date: 2019/03/26 15:51
 * @Version: V1.0
 */
@RestController
@RequestMapping("/dayHideNightOut")
public class TacticsDayHideNightOutConteroller {
    @Autowired
    private ITacticsDayHideNightOutService tacticsDayHideNightOutService;
    /**
     * 查询
     * 包含昼伏夜出+夜间频出两个战法逻辑的查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult list(@RequestBody DayHideNightOutQO qo) {
        VehicleSearchBO<DayHideNightOutVO> records = null;
        records = tacticsDayHideNightOutService.queryPageList(qo);
        if(null != records){
            return ResultUtil.success(records);
        }else{
            return ResultUtil.error(1001,"获取失败！");
        }
    }
}
