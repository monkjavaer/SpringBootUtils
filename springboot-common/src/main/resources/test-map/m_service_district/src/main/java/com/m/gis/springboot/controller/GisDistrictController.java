package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.qo.GisCoordinateQO;
import com.m.gis.springboot.service.GisDistrictService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Title: GisDistrictController
 * @Package: com.m.gis.springboot.controller
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/district")
public class GisDistrictController {

    @Autowired
    private GisDistrictService gisDistrictService;

    @ApiOperation(value="输入行政区域类型，获取该类型的行政区域名字列表（用于select之类的）", notes="")
    @RequestMapping(value = "/getDistrictNameList.do",method = RequestMethod.GET)
    public GisResult getDistrictNameList(@RequestParam String type, @RequestParam(required = false) String parent) {
        return ResultUtil.success(gisDistrictService.getDistrictItem(type,parent));
    }

    @ApiOperation(value="输入位置点，返回该位置点所在行政区域编码", notes="")
    @RequestMapping(value = "/districtCode.do",method = RequestMethod.POST)
    public GisResult getDistrictCode(@RequestBody @Valid GisCoordinateQO gisCoordinateQO) {
        return ResultUtil.success(gisDistrictService.getDistrictCode(gisCoordinateQO.toGisCoordinate()));
    }

}
