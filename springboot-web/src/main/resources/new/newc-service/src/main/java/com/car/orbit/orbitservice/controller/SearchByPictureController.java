package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.ExportConstant;
import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.StructureException;
import com.car.orbit.orbitservice.qo.ExportQO;
import com.car.orbit.orbitservice.qo.SearchByPictureQO;
import com.car.orbit.orbitservice.service.ISearchByPictureService;
import com.car.orbit.orbitservice.util.ExportExcelUtil;
import com.car.orbit.orbitservice.util.UrlUtil;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.util.redis.CityRedis;
import com.car.orbit.orbitservice.util.redis.ExportHeaderRedis;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehiclePositionVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import com.car.orbit.orbitutil.exception.CommonExceptionEnums;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Title: SearchByPictureController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 以图搜图Controller
 * @Author: @author zks
 * @Date: 2019/03/18 15:51
 * @Version: V1.0
 */
@RestController
@RequestMapping("/searchByPicture")
public class SearchByPictureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchByPictureController.class);

    @Autowired
    private ISearchByPictureService searchByPictureService;


    /**
     * @return 返回车辆位置，返回一系列位置坐标列表【List组坐标】
     */
    @RequestMapping(value = "/obtainVehiclePosition", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult obtainVehiclePosition(@RequestParam String pathUrl) {
        try {
            List<VehiclePositionVO> listPosition = searchByPictureService.obtainVehiclePosition(pathUrl);
            return ResultUtil.success(listPosition);
        } catch (IllegalParamException e) {
            return ResultUtil.error(1003, e.getMessage());
        } catch (IOException e) {
            return ResultUtil.error(1001, "请求结构化服务器失败！");
        } catch (StructureException e) {
            return ResultUtil.error(1002, e.getMessage());
        }
    }

    /**
     * 以图搜图===按照条件查询以图搜图的记录信息【最底层的过车信息】
     *
     * @param condition
     * @return 返回过车记录
     */
    @RequestMapping(value = "/imageSearch", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult imageSearch(@RequestBody SearchByPictureQO condition) {
        VehicleSearchBO<VehicleTraitVo> records = null;
        try {
            records = searchByPictureService.imageSearch(condition);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(CommonExceptionEnums.UndefinedRuntimeException.getCode(), CommonExceptionEnums.UndefinedRuntimeException.getMessage());
        }
        return ResultUtil.success(records);
    }

    /**
     * 以图搜图===按照条件查询以图搜图的记录信息【最底层的过车信息】==导出
     *
     * @param condition
     * @return 返回过车记录
     */
    @RequestMapping(value = "/imageSearchExport", method = RequestMethod.POST)
    public OrbitResult imageSearchExport(@RequestBody ExportQO<SearchByPictureQO> condition, HttpServletResponse response) {
        SearchByPictureQO qo = condition.getQueryObject();
        qo.setPageNo(condition.getStart() / qo.getPageSize() + 1);
        qo.setPageSize(condition.getEnd() - condition.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<VehicleTraitVo> records = searchByPictureService.imageSearch(qo);
        List<VehicleTraitVo> pageList = records.getPageList();
        String[] headers;
        String[] exportFields;
        if (condition.getPictureFlg() && pageList != null) {
            for (VehicleTraitVo vo : pageList) {
                try {
                    vo.setPicture(UrlUtil.getByteDataByURL(vo.getPhotoUrl()));
                } catch (IllegalParamException | IOException e) {
                    LOGGER.error("export file error, for get picture from url fail. vid: {}, picUrl: {}",
                            vo.getVid(), vo.getPhotoUrl());
                }
            }
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH_WITHPICTURE);
            //根据数据库配置的表头传入相应的业务数据
            exportFields = new String[]{"captureTime", "plateNumber", "vehicleTypeName", "deviceName", "picture"};
        } else {
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH);
            //根据数据库配置的表头传入相应的业务数据
            exportFields = new String[]{"captureTime", "plateNumber", "vehicleTypeName", "deviceName"};
        }
        try {
            String url = ExportExcelUtil.exportExcel("imageSearchExport.xls", headers, exportFields, pageList);
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 以图搜图===按照条件返回各种情况下的一车一档数据信息
     *
     * @param condition
     * @return 返回聚合记录/聚合数量
     */
    @RequestMapping(value = "/imageSearchByOneFile", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult imageSearchByOneFile(@RequestBody SearchByPictureQO condition) {
        VehicleSearchBO<OneVehicleFileVO> records;
        try {
            records = searchByPictureService.imageSearchByOneFile(condition);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(CommonExceptionEnums.UndefinedRuntimeException.getCode(), CommonExceptionEnums.UndefinedRuntimeException.getMessage());
        }
        return ResultUtil.success(records);
    }

    /**
     * 以图搜图===按照条件返回各种情况下的一车一档数据信息==导出
     *
     * @param condition
     * @return 返回聚合记录/聚合数量
     */
    @RequestMapping(value = "/imageSearchByOneFileExport", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult imageSearchByOneFileExport(@RequestBody ExportQO<SearchByPictureQO> condition, HttpServletResponse response) {
        SearchByPictureQO qo = condition.getQueryObject();
        qo.setPageNo(condition.getStart() / qo.getPageSize() + 1);
        qo.setPageSize(condition.getEnd() - condition.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<OneVehicleFileVO> records = searchByPictureService.imageSearchByOneFile(qo);
        List<OneVehicleFileVO> pageList = records.getPageList();
        String[] headers;
        String[] exportFields;
        if (condition.getPictureFlg() && pageList != null) {
            for (OneVehicleFileVO vo : pageList) {
                try {
                    vo.setPicture(UrlUtil.getByteDataByURL(vo.getPhotoUrl()));
                } catch (IllegalParamException | IOException e) {
                    LOGGER.error("export file error, for get picture from url fail. vid: {}, picUrl: {}",
                            vo.getVid(), vo.getPhotoUrl());
                }
            }
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH_ONEFILE_WITHPICTURE);
            //根据数据库配置的表头传入相应的业务数据
            exportFields = new String[]{"plateNumber", "count", "picture"};
        } else {
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH_ONEFILE);
            //根据数据库配置的表头传入相应的业务数据
            exportFields = new String[]{"plateNumber", "count"};
        }
        try {
            String url = ExportExcelUtil.exportExcel("imageSearchByOneFileExport.xls", headers, exportFields, pageList);
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 以图搜图===按照条件返回各种情况下的聚合信息【包含点位聚合/车辆品牌聚合】
     *
     * @param condition
     * @return 返回聚合记录/聚合数量
     */
    @RequestMapping(value = "/imageSearchByGroup", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult imageSearchByGroup(@RequestBody SearchByPictureQO condition) {
        VehicleSearchBO<VehicleSearchVO> records;
        try {
            records = searchByPictureService.imageSearchByGroup(condition);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(CommonExceptionEnums.UndefinedRuntimeException.getCode(), CommonExceptionEnums.UndefinedRuntimeException.getMessage());
        }
        return ResultUtil.success(records);
    }

    /**
     * 以图搜图===按照条件返回各种情况下的聚合信息【包含点位聚合/车辆品牌聚合】==导出
     *
     * @param condition
     */
    @RequestMapping(value = "/imageSearchByGroupExport", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult imageSearchByGroupExport(@RequestBody ExportQO<SearchByPictureQO> condition, HttpServletResponse response) {
        SearchByPictureQO qo = condition.getQueryObject();
        qo.setPageNo(condition.getStart() / qo.getPageSize() + 1);
        qo.setPageSize(condition.getEnd() - condition.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<VehicleSearchVO> list = searchByPictureService.imageSearchByGroup(qo);
        //redis查询表头
        String[] headers = null;
        //根据数据库配置的表头传入相应的业务数据中的字段
        String[] exportFields = null;
        if (qo.getGroupType() == SearchByPictureQO.brand) {
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH_BRAND);
            exportFields = new String[]{"brandInfo", "count"};
        } else if (qo.getGroupType() == SearchByPictureQO.point) {
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_PIC_SEARCH_ROAD);
            exportFields = new String[]{"pointName", "count"};
        } else {
            System.out.println("导出GroupType不符合要求！");
        }
        try {
            String url = ExportExcelUtil.exportExcel("imageSearchByGroupExport.xls", headers, exportFields, list.getPageList());
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * test
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult test() {
        OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
        List<OrbitVehicleBrand> brands = BrandRedis.getAllBrandList();
        for (OrbitVehicleBrand vo : brands) {
            System.out.println("======getAllBrandList=====" + vo.getZhName());
        }
        List<OrbitVehicleBrandChild> childBrandList = BrandRedis.getAllChildBrandList();
        for (OrbitVehicleBrandChild vo : childBrandList) {
            System.out.println("======getAllChildBrandList=====" + vo.getZhName());
        }
        List<OrbitResCity> list = CityRedis.getAllCitys();
        if (list != null) {
            for (OrbitResCity vo : list) {
                System.out.println("=====getAllCitys======" + vo.getName());
            }
        }
        return ResultUtil.success("==============OK=========");
    }
}
