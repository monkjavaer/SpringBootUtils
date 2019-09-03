package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;
import com.car.orbit.orbitservice.service.IVehicleBrandService;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.BrandVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: VehicleBrandController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 车辆品牌Controller
 * @Author: monkjavaer
 * @Date: 2019/03/22 14:27
 * @Version: V1.0
 */
@RestController
@RequestMapping("/brand")
public class VehicleBrandController {

    @Autowired
    private IVehicleBrandService brandService;

    /**
     * 查询车辆品牌列表
     *
     * @return
     */
    @RequestMapping(value = "/queryBrandList", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryVehicleBrandList() {
        List<OrbitVehicleBrand> list = brandService.getBrand();

        OrbitSysUser currentUser = OrbitSysUserRedis.getCurrentLoginUser();
        String language = currentUser.getLanguage().toUpperCase();

        List<BrandVO> resultList = new ArrayList<>();
        for (OrbitVehicleBrand item : list) {
            BrandVO brandVO = new BrandVO();
            brandVO.setCode(item.getCode());

            if (language.equals("ZH-CN")) {
                brandVO.setName(item.getZhName());
            } else if (language.equals("EN")) {
                brandVO.setName(item.getEnName());
            } else if (language.equals("ES")) {
                brandVO.setName(item.getEsName());
            } else {
                brandVO.setName(item.getZhName());
            }

            resultList.add(brandVO);
        }

        return ResultUtil.success(resultList);
    }

    /**
     * 查询车辆子品牌列表
     *
     * @param brandChild
     * @return
     */
    @RequestMapping(value = "/queryBrandChildList", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryVehicleBrandChildList(@RequestBody OrbitVehicleBrandChild brandChild) {
        List<OrbitVehicleBrandChild> list = brandService.getChildBrand(brandChild.getParentCode());

        OrbitSysUser currentUser = OrbitSysUserRedis.getCurrentLoginUser();
        String language = currentUser.getLanguage().toUpperCase();

        List<BrandVO> resultList = new ArrayList<>();
        for (OrbitVehicleBrandChild item : list) {
            BrandVO brandVO = new BrandVO();
            brandVO.setCode(item.getCode());

            if (language.equals("ZH-CN")) {
                brandVO.setName(item.getZhName());
            } else if (language.equals("EN")) {
                brandVO.setName(item.getEnName());
            } else if (language.equals("ES")) {
                brandVO.setName(item.getEsName());
            } else {
                brandVO.setName(item.getZhName());
            }

            brandVO.setParentCode(item.getParentCode());
            resultList.add(brandVO);
        }

        return ResultUtil.success(resultList);
    }
}
