package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.service.GisDistrictTypeService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value="/gis/dict")
public class GisDistrictDictController extends GisBaseController {

    @Autowired
    private GisDistrictTypeService gisDistrictTypeService;

    @ApiOperation(value="获取行政区域类型字典表", notes="")
    @RequestMapping(value="/getDistrictType.do", method=RequestMethod.GET)
    public GisResult getDistrictType() {
        return ResultUtil.success(gisDistrictTypeService.getDictVO());
    }

}