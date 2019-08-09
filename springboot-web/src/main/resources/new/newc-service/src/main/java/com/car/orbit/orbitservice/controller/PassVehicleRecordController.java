package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.NearbyPassVehicleRecordBO;
import com.car.orbit.orbitservice.bo.VehicleDetailBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.ExportConstant;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.qo.ExportQO;
import com.car.orbit.orbitservice.qo.PassVehicleRecordQO;
import com.car.orbit.orbitservice.service.IPassVehicleRecordService;
import com.car.orbit.orbitservice.util.ExportExcelUtil;
import com.car.orbit.orbitservice.util.UrlUtil;
import com.car.orbit.orbitservice.util.redis.ExportHeaderRedis;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Title: PassVehicleRecordController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 过车记录Controller
 * @Author: monkjavaer
 * @Date: 2019/03/16 11:55
 * @Version: V1.0
 */
@RestController
@RequestMapping("/passVehicleRecord")
public class PassVehicleRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassVehicleRecordController.class);

    @Autowired
    private IPassVehicleRecordService passVehicleRecordService;

    /**
     * 查询过车记录
     *
     * @param passVehicleRecordQO
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryRecordList(@RequestBody PassVehicleRecordQO passVehicleRecordQO) {
        VehicleSearchBO<OrbitPassVehicleRecord> list = passVehicleRecordService.queryPassVehicleRecordList(passVehicleRecordQO);
        return ResultUtil.success(list);
    }

    /**
     * 查询过车记录（用户设置一车一档）
     *
     * @param passVehicleRecordQO
     * @return
     */
    @RequestMapping(value = "/searchByCar", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryRecordByCar(@RequestBody PassVehicleRecordQO passVehicleRecordQO) {
        VehicleSearchBO<OneVehicleFileVO> list = passVehicleRecordService.queryPassVehicleRecordByCar(passVehicleRecordQO);
        return ResultUtil.success(list);
    }

    /**
     * 综合搜车，根据设备ID聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    @RequestMapping(value = "/queryPageByDevice", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageByDevice(@RequestBody PassVehicleRecordQO passVehicleRecordQO) {
        VehicleSearchBO<VehicleSearchVO> vehicleSearchBO = passVehicleRecordService.queryQueryPageByDevice(passVehicleRecordQO);
        return ResultUtil.success(vehicleSearchBO);
    }

    /**
     * 综合搜车，根据车辆子品牌聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    @RequestMapping(value = "/queryPageByBrandChild", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageByBrandChild(@RequestBody PassVehicleRecordQO passVehicleRecordQO) {
        VehicleSearchBO<VehicleSearchVO> vehicleSearchBO = passVehicleRecordService.queryPageByBrandChild(passVehicleRecordQO);
        return ResultUtil.success(vehicleSearchBO);
    }

    /**
     * 根据过车记录id查询记录详情
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/queryPassVehicleRecordById", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPassVehicleRecordById(@RequestBody OrbitPassVehicleRecord record) {
        return ResultUtil.success(passVehicleRecordService.queryPassVehicleRecordById(record.getId(),
                DateUtils.getDate(record.getCaptureTime())));
    }

    /**
     * 开启一车一档后，点击分页车辆详情
     *
     * @param passVehicleRecordQO
     * @return
     */
    @RequestMapping(value = "/queryDetailPage", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryDetailPage(@RequestBody PassVehicleRecordQO passVehicleRecordQO) {
        VehicleDetailBO vehicleDetailBO = passVehicleRecordService.queryDetailPage(passVehicleRecordQO);
        return ResultUtil.success(vehicleDetailBO);
    }

    /**
     * 导出，综合搜车，根据路口ID聚合
     *
     * @param exportQO
     */
    @RequestMapping(value = "/exportQueryPageByRoad", method = RequestMethod.POST)
    public OrbitResult exportQueryPageByRoad(@RequestBody ExportQO<PassVehicleRecordQO> exportQO) {
        PassVehicleRecordQO passVehicleRecordQO = exportQO.getQueryObject();
        passVehicleRecordQO.setPageNo(exportQO.getStart() / passVehicleRecordQO.getPageSize() + 1);
        passVehicleRecordQO.setPageSize(exportQO.getEnd() - exportQO.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<VehicleSearchVO> vehicleSearchBO = passVehicleRecordService.queryQueryPageByDevice(passVehicleRecordQO);
        //redis查询表头
        String[] headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH_ROAD);
        //根据数据库配置的表头传入相应的业务数据(VehicleSearchVO)中的字段
        String[] exportFields = {"pointName", "count"};
        try {
            String url = ExportExcelUtil.exportExcel("exportQueryPageByRoad.xls", headers, exportFields, vehicleSearchBO.getPageList());
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 导出，综合搜车，根据车辆子品牌聚合
     *
     * @param exportQO
     */
    @RequestMapping(value = "/exportQueryPageByBrandChild", method = RequestMethod.POST)
    public OrbitResult exportQueryPageByBrandChild(@RequestBody ExportQO<PassVehicleRecordQO> exportQO) {
        PassVehicleRecordQO passVehicleRecordQO = exportQO.getQueryObject();
        passVehicleRecordQO.setPageNo(exportQO.getStart() / passVehicleRecordQO.getPageSize() + 1);
        passVehicleRecordQO.setPageSize(exportQO.getEnd() - exportQO.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<VehicleSearchVO> vehicleSearchBO = passVehicleRecordService.queryPageByBrandChild(passVehicleRecordQO);
        //redis查询表头
        String[] headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH_BRAND);
        //根据数据库配置的表头传入相应的业务数据(VehicleSearchVO)中的字段
        String[] exportFields = {"brandInfo", "count"};
        try {
            String url = ExportExcelUtil.exportExcel("exportQueryPageByBrandChild.xls", headers, exportFields, vehicleSearchBO.getPageList());
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 导出，综合搜车
     *
     * @param exportQO
     */
    @RequestMapping(value = "/exportSearch", method = RequestMethod.POST)
    public OrbitResult exportSearch(@RequestBody ExportQO<PassVehicleRecordQO> exportQO) {
        PassVehicleRecordQO passVehicleRecordQO = exportQO.getQueryObject();
        passVehicleRecordQO.setPageNo(exportQO.getStart() / passVehicleRecordQO.getPageSize() + 1);
        passVehicleRecordQO.setPageSize(exportQO.getEnd() - exportQO.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<OrbitPassVehicleRecord> list = passVehicleRecordService.queryPassVehicleRecordList(passVehicleRecordQO);
        List<OrbitPassVehicleRecord> exportList = list.getPageList();
        OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
        String language = user.getLanguage().split("_")[0].toUpperCase();
        String[] headers;
        String[] exportFields;
        //是否导出带图片的Excel
        if (exportQO.getPictureFlg()) {
            for (OrbitPassVehicleRecord orbitPassVehicleRecord : exportList) {
                orbitPassVehicleRecord.setVehicleType(OrbitServiceConstant.getOrbitVehicleType(orbitPassVehicleRecord.getVehicleType()).getInternationalName(language));
                try {
                    orbitPassVehicleRecord.setPicture(UrlUtil.getByteDataByURL(orbitPassVehicleRecord.getPhotoFastdfsUrl()));
                } catch (IllegalParamException | IOException e) {
                    LOGGER.error("export file error, for get picture from url fail. id: {}, vid: {}, picUrl: {}",
                            orbitPassVehicleRecord.getId(), orbitPassVehicleRecord.getVid(), orbitPassVehicleRecord.getPhotoFastdfsUrl());
                }
            }
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH_WITHPICTURE);
            //根据数据库配置的表头传入相应的业务数据(OrbitPassVehicleRecord)中的字段
            exportFields = new String[]{"captureTime", "plateNumber", "vehicleType", "deviceName", "picture"};
        } else {
            for (OrbitPassVehicleRecord orbitPassVehicleRecord : exportList) {
                orbitPassVehicleRecord.setVehicleType(OrbitServiceConstant.getOrbitVehicleType(orbitPassVehicleRecord.getVehicleType()).getInternationalName(language));
            }
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH);
            //根据数据库配置的表头传入相应的业务数据(OrbitPassVehicleRecord)中的字段
            exportFields = new String[]{"captureTime", "plateNumber", "vehicleType", "deviceName"};
        }

        try {
            String url = ExportExcelUtil.exportExcel("exportSearch.xls", headers, exportFields, exportList);
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 导出，综合搜车（开启一车一档）
     *
     * @param exportQO
     */
    @RequestMapping(value = "/exportSearchByCar", method = RequestMethod.POST)
    public OrbitResult exportSearchByCar(@RequestBody ExportQO<PassVehicleRecordQO> exportQO) {
        PassVehicleRecordQO passVehicleRecordQO = exportQO.getQueryObject();
        passVehicleRecordQO.setPageNo(exportQO.getStart() / passVehicleRecordQO.getPageSize() + 1);
        passVehicleRecordQO.setPageSize(exportQO.getEnd() - exportQO.getStart() + 1);
        //业务数据查询
        VehicleSearchBO<OneVehicleFileVO> list = passVehicleRecordService.queryPassVehicleRecordByCar(passVehicleRecordQO);
        List<OneVehicleFileVO> exportList = list.getPageList();
        String[] headers;
        String[] exportFields;
        //是否导出带图片的Excel
        if (exportQO.getPictureFlg()) {
            for (OneVehicleFileVO oneVehicleFileVO : exportList) {
                try {
                    oneVehicleFileVO.setPicture(UrlUtil.getByteDataByURL(oneVehicleFileVO.getPhotoUrl()));
                } catch (IllegalParamException | IOException e) {
                    LOGGER.error("export file error, for get picture from url fail. vid: {}, picUrl: {}",
                            oneVehicleFileVO.getVid(), oneVehicleFileVO.getPhotoUrl());
                }
            }
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH_ONLY_WITHPICTURE);
            //根据数据库配置的表头传入相应的业务数据(OrbitPassVehicleRecord)中的字段
            exportFields = new String[]{"plateNumber", "count", "picture"};
        } else {
            //redis查询表头
            headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_SEARCH_ONLY);
            //根据数据库配置的表头传入相应的业务数据(OrbitPassVehicleRecord)中的字段
            exportFields = new String[]{"plateNumber", "count"};
        }


        try {
            String url = ExportExcelUtil.exportExcel("exportSearchByCar.xls", headers, exportFields, list.getPageList());
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 查询前、后一条过车记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/nearbyRecord", method = RequestMethod.POST)
    public OrbitResult queryNearbyRecord(@RequestBody OrbitPassVehicleRecord record) {
        NearbyPassVehicleRecordBO nearbyPassVehicleRecordBO = passVehicleRecordService.queryNearbyRecord(record.getId(), record.getCaptureTime());
        return ResultUtil.success(nearbyPassVehicleRecordBO);
    }
}
