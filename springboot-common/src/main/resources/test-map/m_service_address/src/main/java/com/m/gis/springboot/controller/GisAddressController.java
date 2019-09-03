package com.m.gis.springboot.controller;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.qo.GisAddressNameElementQO;
import com.m.gis.springboot.qo.GisAddressNameLonLatQO;
import com.m.gis.springboot.qo.GisAddressNameQO;
import com.m.gis.springboot.qo.GisAddressNameSaveQO;
import com.m.gis.springboot.service.GisAddressService;
import com.m.gis.springboot.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Title: GisAddressDictController
 * @Package: com.m.gis.springboot
 * @Description: 用于提供地名相关的字典服务
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/gis/address")
public class GisAddressController extends GisBaseController {

    @Autowired
    private GisAddressService gisAddressService;

    @ApiOperation(value="模糊查询地名", notes="")
    @RequestMapping(value="/addressNameList.do", method=RequestMethod.POST)
    public GisResult getAddressTypeList(@RequestBody @Valid GisAddressNameQO gisAddressNameQO) {
        return ResultUtil.success(gisAddressService.getAddressNamePages(gisAddressNameQO));
    }

    @ApiOperation(value="根据经纬度查询最邻近地名", notes="")
    @RequestMapping(value="/addressNameByLonLat.do", method=RequestMethod.POST)
    public GisResult getAddressTypeList(@RequestBody @Valid GisAddressNameLonLatQO gisAddressNameLonLatQO) {
        return ResultUtil.success(gisAddressService.getAddressNameByLonLat(gisAddressNameLonLatQO));
    }

    @ApiOperation(value="查询地名详细信息", notes="")
    @RequestMapping(value="/getAddressNameInfo.do", method=RequestMethod.GET)
    public GisResult getAddressNameInfo(@RequestParam String type, @RequestParam Integer gid) {
        return ResultUtil.success(gisAddressService.getAddressNameInformation(gid,type));
    }

    @ApiOperation(value="批量添加地名，无去重判断", notes="")
    @RequestMapping(value="/saveAddressNameList.do", method=RequestMethod.POST)
    public GisResult saveAddressNameList(@RequestBody @Valid GisAddressNameSaveQO gisAddressNameSaveQO) {
        return ResultUtil.success(gisAddressService.saveAddressNameList(gisAddressNameSaveQO));
    }

    @ApiOperation(value="批量添加或更新地名", notes="")
    @RequestMapping(value="/saveOrUpdateAddressNameList.do", method=RequestMethod.POST)
    public GisResult saveOrUpdateAddressNameList(@RequestBody @Valid GisAddressNameSaveQO gisAddressNameSaveQO) {
        return ResultUtil.success(gisAddressService.saveOrUpdateAddressNameList(gisAddressNameSaveQO));
    }

    @ApiOperation(value="判断地名是否重复，依据为地名是否与已存在的地名经纬度在一定容差内重叠（保留接口）", notes="")
    @RequestMapping(value="/getAddressNameIfDuplicated.do", method=RequestMethod.POST)
    @Deprecated
    public GisResult getAddressNameIfDuplicated(@RequestBody @Valid GisAddressNameElementQO gisAddressNameElementQO) {
        return ResultUtil.success(gisAddressService.getAddressNameIfDuplicated(gisAddressNameElementQO));
    }

    @ApiOperation(value="判断地名是否重复，依据为地名是否与已存在的地名经纬度和名字相等", notes="")
    @RequestMapping(value="/getAddressNameIfDuplicatedByNameAndLonLat.do", method=RequestMethod.POST)
    public GisResult getAddressNameIfDuplicatedByNameAndLonLat(@RequestBody @Valid GisAddressNameElementQO gisAddressNameElementQO) {
        return ResultUtil.success(gisAddressService.getAddressNameIfDuplicatedByNameAndLonLat(gisAddressNameElementQO));
    }

}
