package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.qo.GisPoiBufferQO;
import com.m.gis.springboot.qo.GisPoiZoneQO;
import com.m.gis.springboot.service.GisPoiService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Title: GisPoiController
 * @Package: com.m.gis.springboot.controller
 * @Description: 用于提供兴趣点相关服务
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/poi")
public class GisPoiController extends GisBaseController{

    @Autowired
    private GisPoiService gisPoiService;

    @ApiOperation(value="获取区域内的兴趣点分页列表", notes="")
    @RequestMapping(value="/zoneList.do", method=RequestMethod.POST)
    public GisResult getZonePoiList(@RequestBody @Valid GisPoiZoneQO gisPoiZoneQO) {
        return ResultUtil.success(gisPoiService.getPoiListByZone(gisPoiZoneQO));
    }

    @ApiOperation(value="获取缓冲区域内的兴趣点分页列表", notes="")
    @RequestMapping(value="/bufferList.do", method=RequestMethod.POST)
    public GisResult getBufferPoiList(@RequestBody @Valid GisPoiBufferQO gisPoiBufferQO) {
        return ResultUtil.success(gisPoiService.getPoiListByBufferZone(gisPoiBufferQO));
    }



}
