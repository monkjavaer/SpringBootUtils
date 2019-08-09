package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.properties.GisPoiDictProperties;
import com.m.gis.springboot.utils.LocaleUtil;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: GisPoiDictController
 * @Package: com.m.gis.springboot
 * @Description: 用于提供兴趣点相关的字典服务
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/dict")
public class GisPoiDictController extends GisBaseController {

    @Autowired
    private GisPoiDictProperties GisPoiDictProperties;

    /**
     *
     * @param queryEnable 启用查询 ：传入t表示true，查询字段query_enable = t 的信息。
     * @return
     */
    @ApiOperation(value="获取可查询的兴趣点类型字典表", notes="")
    @RequestMapping(value="/getPoiType.do", method=RequestMethod.GET)
    public GisResult getDistrictTypeList(@RequestParam(required = false) Boolean queryEnable) {
        if (queryEnable == null){
            queryEnable = true;
        }
        return ResultUtil.success(gisDictService.getDictVO(GisPoiDictProperties.getQueryPoiType() , queryEnable));
    }

}
