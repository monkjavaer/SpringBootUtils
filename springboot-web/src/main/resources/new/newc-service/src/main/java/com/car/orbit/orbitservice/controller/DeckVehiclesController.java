package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.PassDeckVehicleDetail;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.qo.DeckVehiclesQO;
import com.car.orbit.orbitservice.qo.HiddenVehiclesQO;
import com.car.orbit.orbitservice.service.IDeckVehiclesService;
import com.car.orbit.orbitservice.service.IHiddenVehiclesService;
import com.car.orbit.orbitservice.vo.DeckVehiclesVO;
import com.car.orbit.orbitservice.vo.HiddenVehiclesVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CreateDate: 2019-3-29 <br/>
 * Author: monkjavaer <br/>
 * Description: 套牌车分析Controller
 * Version: 1.0
 **/

@RestController
@RequestMapping("/deckVehicles")
public class DeckVehiclesController {

    @Autowired
    IDeckVehiclesService iDeckVehiclesService;

    /**
     * @description 查询套牌车辆，分页返回结果
     * @date: 2019-3-27 11:59
     * @author: monkjavaer
     * @param deckVehiclesQO，查询条件
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult search(@RequestBody DeckVehiclesQO deckVehiclesQO){
        VehicleSearchBO<DeckVehiclesVO> data =  iDeckVehiclesService.search(deckVehiclesQO);
        if (deckVehiclesQO.isFlag() && data.getPageList().size() == 0) {
            deckVehiclesQO.setPageNo(1);
            data = iDeckVehiclesService.search(deckVehiclesQO);
            data.setRedirect(true);
        } else {
            data.setRedirect(false);
        }
        return ResultUtil.success(data);
    }

    /**
     * @description 排除套牌车辆
     * @date: 2019-3-27 11:59
     * @author: monkjavaer
     * @param deckVehiclesQO，查询条件
     * @return
     */
    @RequestMapping(value = "/exclude", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult exclude(@RequestBody DeckVehiclesQO deckVehiclesQO){
        return iDeckVehiclesService.exclude(deckVehiclesQO);
    }

    /**
     * 查询套牌车详细
     * @param deckVehiclesQO
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult detail(@RequestBody DeckVehiclesQO deckVehiclesQO){
        VehicleSearchBO<PassDeckVehicleDetail> data = iDeckVehiclesService.queryDeckVehiclesDetail(deckVehiclesQO);
        return ResultUtil.success(data);
    }
}
