package com.car.orbit.orbitservice.controller;


import com.car.orbit.orbitservice.qo.RequestPrameterQo;
import com.car.orbit.orbitservice.service.IVehicleTraitService;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * CreateDate：2019/3/5 <br/>
 * Author：monkjavaer <br/>
 * Description:
 **/
@RestController
public class VehicleTraitController {

    @Resource
    private IVehicleTraitService vehicleTraitService ;



    @RequestMapping("/testTrait")
    @ResponseBody
    public List<VehicleTraitVo> toVehicle(HttpServletRequest request, Model model){
        List<VehicleTraitVo> vehicleTraitVoList = this.vehicleTraitService.getTraitByImgAll( );
        return vehicleTraitVoList;
    }

    @RequestMapping(value="/traitExtract", method = RequestMethod.POST)
    @ResponseBody
    public List<VehicleTraitVo> toVehicle(@RequestBody RequestPrameterQo requestPrameter){
//        System.out.println(requestPrameter.toString());
        List<VehicleTraitVo> vehicleTraitVoList = this.vehicleTraitService.getTraitByCondition(requestPrameter );
        return vehicleTraitVoList;
    }


}