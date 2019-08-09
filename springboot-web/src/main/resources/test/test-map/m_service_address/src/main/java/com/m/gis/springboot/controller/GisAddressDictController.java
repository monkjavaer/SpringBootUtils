package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.properties.GisAddressDictProperties;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: GisAddressDictController
 * @Package: com.m.gis.springboot
 * @Description: 用于提供地名相关的字典服务
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/dict")
public class GisAddressDictController extends GisBaseController {

    @Autowired
    private GisAddressDictProperties gisAddressDictProperties;

    @ApiOperation(value="获取地名类型字典表", notes="")
    @RequestMapping(value="/getAddressType.do", method=RequestMethod.GET)
    public GisResult getAddressTypeList() {
        return ResultUtil.success(gisDictService.getDictVO(gisAddressDictProperties.getAddressType()));
    }

}
