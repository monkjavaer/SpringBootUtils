package com.m.gis.springboot.controller;

import com.m.gis.springboot.annotation.GisLog;
import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.service.GisBolDistrictService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: GisDistrictDictController
 * @Package: com.m.gis.springboot.controller
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/bol")
public class GisBolDistrictController extends GisBaseController {

    @Autowired
    private GisBolDistrictService gisBolDistrictService;

    @ApiOperation(value="获取行政区域字典表", notes="")
    @RequestMapping(value="/getDistrictDict.do", method=RequestMethod.GET)
    public GisResult getDistrictDict() {
        return ResultUtil.success(gisBolDistrictService.getDistrictDict());
    }

    @ApiOperation(value="返回所有的行政区域信息", notes="")
    @GisLog
    @RequestMapping(value="/getDistrictAll.do", method=RequestMethod.GET)
    public GisResult getDistrictAll() {
        return ResultUtil.success(gisBolDistrictService.getDistrictAll());
    }

    @ApiOperation(value="返回指定类型和父行政区域编码的行政区域对象", notes="")
    @GisLog
    @RequestMapping(value="/getDistrictByType.do", method=RequestMethod.GET)
    public GisResult getDistrictAll(@RequestParam String districtType, @RequestParam(required = false) String parentCode) {
        return ResultUtil.success(gisBolDistrictService.getDistrictByType(districtType,parentCode));
    }

}