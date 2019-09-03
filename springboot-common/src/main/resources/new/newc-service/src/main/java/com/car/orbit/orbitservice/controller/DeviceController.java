package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.HistoryVideoBO;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.GeoPositionDuplicateException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.qo.QueryVideoQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.util.HistoryVideoUtil;
import com.car.orbit.orbitservice.util.IpUtil;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitservice.vo.DeviceSimpleVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: DeviceController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 设备controller
 * @Author: monkjavaer
 * @Data: 2019/3/8 11:05
 * @Version: V1.0
 */
@RestController
@RequestMapping("/deviceManagement")
public class DeviceController {

    /** 无效参数 */
    private final int invalidParamCode = 1001;

    /** 设备名重复 */
    private final int duplicateNameCode = 1002;

    /** 关联布控任务返回码 */
    private final int relationshipCode = 1003;

    /** 经纬度重复 */
    private final int geoPositionDuplicateCode = 1004;

    @Autowired
    private IDeviceService deviceService;

    /**
     * 设备分页列表查询
     *
     * @param deviceQO
     * @return
     */
    @RequestMapping(value = "/queryPageList", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestBody DeviceQO deviceQO) {
        PageUtil<DeviceDetailVO> list = deviceService.queryPageList(deviceQO);
        return ResultUtil.success(list);
    }


    /**
     * 设备信息查询（全部）
     *
     * @param orbitResDevice
     * @return
     */
    @RequestMapping(value = "/queryAllList", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryAllList(@RequestBody OrbitResDevice orbitResDevice) {
        List<DeviceSimpleVO> list = deviceService.queryAllList(orbitResDevice);
        return ResultUtil.success(list);
    }


    /**
     * 获取历史视频rtsp串,现在默认查出抓拍时间前后5分钟视频列表
     *
     * @param queryVideoQO
     * @return
     */
    @RequestMapping(value = "/queryVideoUrl")
    public OrbitResult queryVideoUrl(@RequestBody QueryVideoQO queryVideoQO) {
        //默认查出抓拍时间前后5分钟视频列表
        int offset = 5;
        //计算偏移时间
        String captureTime = queryVideoQO.getCaptureTime();
        String startTime = DateUtils.getBeforeOrAfterMinute(captureTime, -offset);
        String endTime = DateUtils.getBeforeOrAfterMinute(captureTime, offset);

        OrbitResDevice device = deviceService.getDeviceById(queryVideoQO.getDeviceId());

        //sdk根据设备id请求获取rtsp串
        HistoryVideoBO historyVideoBO = null;
        try {
            historyVideoBO = HistoryVideoUtil.getHistoryVideoRTSPs(startTime, endTime, device.getVideoDeviceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(historyVideoBO);
    }


    /**
     * 添加设备
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addDevice(@RequestBody OrbitResDevice device) {
        if (StringUtils.isEmpty(device.getName().trim())
                || StringUtils.isEmpty(device.getIp().trim())
                || StringUtils.isEmpty(device.getUsername().trim())
                || StringUtils.isEmpty(device.getPassword().trim())
                || device.getLatitude() == null
                || device.getLongitude() == null
                || !IpUtil.isIP(device.getIp())) {
            return ResultUtil.error(invalidParamCode, "Invalid param");
        }
        try {
            deviceService.addDevice(device);
        } catch (DuplicateDataException e) {
            return ResultUtil.error(duplicateNameCode, e.getMessage());
        } catch (GeoPositionDuplicateException e) {
            return ResultUtil.error(geoPositionDuplicateCode, e.getMessage());
        }
        return ResultUtil.success();
    }

    /**
     * 更新设备
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/updateDevice", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateDevice(@RequestBody OrbitResDevice device) {
        try {
            deviceService.updateDevice(device);
        } catch (IllegalParamException e) {
            return ResultUtil.error(invalidParamCode, e.getMessage());
        } catch (DuplicateDataException e) {
            return ResultUtil.error(duplicateNameCode, e.getMessage());
        } catch (GeoPositionDuplicateException e) {
            return ResultUtil.error(geoPositionDuplicateCode, e.getMessage());
        }
        return ResultUtil.success();
    }

    /**
     * 删除设备
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteDevice(@RequestBody OrbitResDevice device) {
        try {
            deviceService.deleteDevice(device);
        } catch (RelationshipException e) {
            return ResultUtil.error(relationshipCode, e.getMessage());
        }
        return ResultUtil.success();
    }

    /**
     * 设备类型字典
     *
     * @return
     */
    @RequestMapping(value = "/queryDeviceType", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryDeviceType() {
        return ResultUtil.success(deviceService.queryDeviceType());
    }

    /**
     * 设备厂商列表
     *
     * @return
     */
    @RequestMapping(value = "/queryDeviceManufacturer", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryDeviceManufacturer() {
        return ResultUtil.success(deviceService.queryDeviceManufacturer());
    }
}
