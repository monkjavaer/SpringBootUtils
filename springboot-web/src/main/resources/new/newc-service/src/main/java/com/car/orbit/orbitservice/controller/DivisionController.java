package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitservice.entity.OrbitResRoadcrossPoint;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.service.IDivisionService;
import com.car.orbit.orbitservice.util.DeviceTreeNode;
import com.car.orbit.orbitservice.vo.AreaVO;
import com.car.orbit.orbitservice.vo.CityVO;
import com.car.orbit.orbitservice.vo.RoadVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: CityController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 城市
 * @Author: monkjavaer
 * @Data: 2019/3/14 11:21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/division")
public class DivisionController {

    @Autowired
    private IDivisionService divisionService;


    /**
     * 获取树形组织机构
     *
     * @return
     */
    @GetMapping(value = "/getDevieTree")
    @ResponseBody
    public OrbitResult getDeviceTree() {
        List<DeviceTreeNode> node = divisionService.getDeviceTree();
        return ResultUtil.success(node);
    }

    /**
     * 查询所有未删除城市
     *
     * @return
     */
    @GetMapping(value = "/queryCityList")
    @ResponseBody
    public OrbitResult queryAllList() {
        List<CityVO> list = divisionService.quaryCityList();
        return ResultUtil.success(list);
    }

    /**
     * 查询城市下面所有区域
     *
     * @param cityId 城市主键
     * @return
     */
    @GetMapping(value = "/queryAreaList")
    @ResponseBody
    public OrbitResult queryAreaList(String cityId) {
        List<AreaVO> list = divisionService.quaryAreaList(cityId);
        return ResultUtil.success(list);
    }

    /**
     * 查询区域下面所有路段
     *
     * @param areaId 区域ID
     * @return
     */
    @GetMapping(value = "/queryRoadList")
    @ResponseBody
    public OrbitResult queryRoadList(String areaId) {
        List<RoadVO> list = divisionService.queryRoadList(areaId);
        return ResultUtil.success(list);
    }

    /**
     * 添加城市
     *
     * @param city
     * @return
     */
    @RequestMapping(value = "/addCity", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addCity(@RequestBody OrbitResCity city) {
        Boolean isUsable = divisionService.hasCityName(city.getName().trim(), city.getId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "城市名已存在");
        }
        isUsable = divisionService.hasCityAdminRegionCode(city.getAdminRegionCode(), city.getId());
        if (isUsable) {
            return ResultUtil.error(1002, "行政区域编码已存在");
        }
        divisionService.addCity(city);
        return ResultUtil.success();
    }

    /**
     * 添加区域
     *
     * @param area
     * @return
     */
    @RequestMapping(value = "/addArea", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addArea(@RequestBody OrbitResArea area) {
        Boolean isUsable = divisionService.hasAreaName(area.getName().trim(), area.getId(), area.getCityId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "区域名已存在");
        }
        isUsable = divisionService.hasAreaAdminRegionCode(area.getAdminRegionCode(), area.getId());
        if (isUsable) {
            return ResultUtil.error(1002, "行政区域编码已存在");
        }
        divisionService.addArea(area);
        Map<String, Object> map = new HashMap<>();
        map.put("id", area.getCityId());
        map.put("canDelete", false);
        return ResultUtil.success(map);
    }


    /**
     * 添加路口
     *
     * @param road
     * @return
     */
    @RequestMapping(value = "/addRoad", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addRoad(@RequestBody OrbitResRoadcrossPoint road) {
        Boolean isUsable = divisionService.hasRoadName(road.getName().trim(), road.getId(), road.getAreaId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "路口名已存在");
        }
        divisionService.addRoad(road);
        Map<String, Object> map = new HashMap<>();
        map.put("id", road.getAreaId());
        map.put("canDelete", false);
        return ResultUtil.success(map);
    }

    /**
     * 修改城市
     *
     * @param city
     * @return
     */
    @RequestMapping(value = "/updateCity", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateCity(@RequestBody OrbitResCity city) {
        Boolean isUsable = divisionService.hasCityName(city.getName().trim(), city.getId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "城市名已存在");
        }
        isUsable = divisionService.hasCityAdminRegionCode(city.getAdminRegionCode(), city.getId());
        if (isUsable) {
            return ResultUtil.error(1002, "行政区域编码已存在");
        }
        divisionService.updateCity(city);
        return ResultUtil.success();
    }

    /**
     * 修改区域
     *
     * @param area
     * @return
     */
    @RequestMapping(value = "/updateArea", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateArea(@RequestBody OrbitResArea area) {
        Boolean isUsable = divisionService.hasAreaName(area.getName().trim(), area.getId(), area.getCityId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "区域名已存在");
        }
        isUsable = divisionService.hasAreaAdminRegionCode(area.getAdminRegionCode(), area.getId());
        if (isUsable) {
            return ResultUtil.error(1002, "行政区域编码已存在");
        }
        divisionService.updateArea(area);
        return ResultUtil.success();
    }


    /**
     * 修改路口
     *
     * @param road
     * @return
     */
    @RequestMapping(value = "/updateRoad", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateRoad(@RequestBody OrbitResRoadcrossPoint road) {
        Boolean isUsable = divisionService.hasRoadName(road.getName().trim(), road.getId(), road.getAreaId());
        if (isUsable) {
            return ResultUtil.error(ResponseType.NAME_EXIST.getCode(), "路口名已存在");
        }
        divisionService.updateRoad(road);
        return ResultUtil.success();
    }

    /**
     * 删除城市
     *
     * @param city
     * @return
     */
    @RequestMapping(value = "/deleteCity", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteCity(@RequestBody OrbitResCity city) {
        List<AreaVO> areaVOS = divisionService.quaryAreaList(city.getId());
        if (areaVOS.size() > 0) {
            return ResultUtil.error(1101, "can't delete city!");
        }
        try {
            divisionService.deleteCity(city);
        }catch (RelationshipException e) {
            return ResultUtil.error(1101, "can't delete city!");
        }
        return ResultUtil.success();
    }

    /**
     * 删除区域
     *
     * @param area
     * @return
     */
    @RequestMapping(value = "/deleteArea", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteArea(@RequestBody OrbitResArea area) {
        Map<String, Object> map;
        try {
            map = divisionService.deleteArea(area);
        } catch (RelationshipException e) {
            return ResultUtil.error(1102, "can't delete area!");
        }
        return ResultUtil.success(map);
    }


    /**
     * 删除路口
     *
     * @param road
     * @return
     */
    @RequestMapping(value = "/deleteRoad", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteRoad(@RequestBody OrbitResRoadcrossPoint road) {
        Map<String, Object> map;
        try {
            map = divisionService.deleteRoad(road);
        } catch (RelationshipException e) {
            return ResultUtil.error(1102, e.getMessage());
        }
        return ResultUtil.success(map);
    }

}
