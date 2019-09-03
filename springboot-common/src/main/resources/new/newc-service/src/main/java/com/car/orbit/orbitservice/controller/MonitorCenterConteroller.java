package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitSysMonitorCenter;
import com.car.orbit.orbitservice.qo.MonitorCenterQO;
import com.car.orbit.orbitservice.service.IMonitorCenterService;
import com.car.orbit.orbitservice.vo.MonitorCenterVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: MonitorCenterConteroller
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 机构管理Conteroller
 * @Author: monkjavaer
 * @Data: 2019/3/7 18:14
 * @Version: V1.0
 */
@RestController
@RequestMapping("/monitorCenter")
public class MonitorCenterConteroller {

    /** 机构名称重复返回码 */
    private final int duplicateNameCode = 1001;

    /** 机构名称/机构id为空返回码 */
    private final int emptyParamCode = 1002;

    /** 机构下有用户，不能删除的返回码 */
    private final int cannotDeleteCode = 1101;

    /**
     * 机构接口
     */
    @Autowired
    private IMonitorCenterService monitorCenterService;

    /**
     * 机构分页列表查询
     *
     * @param monitorCenterQO
     * @return
     */
    @RequestMapping(value = "/queryPageList", method = RequestMethod.POST)
    public OrbitResult queryPageList(@RequestBody MonitorCenterQO monitorCenterQO) {
        if (StringUtils.isNotBlank(monitorCenterQO.getName())) {
            monitorCenterQO.setName(monitorCenterQO.getName().trim());
        }
        if (StringUtils.isNotBlank(monitorCenterQO.getAddress())) {
            monitorCenterQO.setAddress(monitorCenterQO.getAddress().trim());
        }
        PageUtil<OrbitSysMonitorCenter> list = monitorCenterService.queryPageList(monitorCenterQO);
        return ResultUtil.success(list);
    }

    /**
     * 查询所有机构
     *
     * @return
     */
    @GetMapping(value = "/queryAllList")
    public OrbitResult queryAllList() {
        List<MonitorCenterVO> list = monitorCenterService.queryAllList();
        return ResultUtil.success(list);
    }

    /**
     * 添加机构
     *
     * @param orbitSysMonitorCenter 需要添加的机构信息
     * @return 操作结果
     */
    @RequestMapping(value = "/addMonitorCenter", method = RequestMethod.POST)
    public OrbitResult addMonitorCenter(@RequestBody OrbitSysMonitorCenter orbitSysMonitorCenter) {
        if (StringUtils.isBlank(orbitSysMonitorCenter.getName())) {
            return ResultUtil.error(emptyParamCode, "The monitor name is empty");
        }
        if (monitorCenterService.checkNameDuplicate(null, orbitSysMonitorCenter.getName())) {
            return ResultUtil.error(duplicateNameCode, "The monitor name is already exist");
        }
        monitorCenterService.addMonitorCenter(orbitSysMonitorCenter);
        return ResultUtil.success();
    }

    /**
     * 更新机构
     *
     * @param orbitSysMonitorCenter 需要更新的机构信息
     * @return 操作结果
     */
    @RequestMapping(value = "/updateMonitorCenter", method = RequestMethod.POST)
    public OrbitResult updateMonitorCenter(@RequestBody OrbitSysMonitorCenter orbitSysMonitorCenter) {
        if (StringUtils.isBlank(orbitSysMonitorCenter.getName()) || StringUtils.isBlank(orbitSysMonitorCenter.getId())) {
            return ResultUtil.error(emptyParamCode, "The monitor name or id is empty");
        }
        if (monitorCenterService.checkNameDuplicate(orbitSysMonitorCenter.getId(), orbitSysMonitorCenter.getName())) {
            return ResultUtil.error(duplicateNameCode, "The monitor name is already exist");
        }
        monitorCenterService.updateMonitorCenter(orbitSysMonitorCenter);
        return ResultUtil.success();
    }

    /**
     * 删除机构（逻辑删除，delete字段改为2）
     *
     * @param orbitSysMonitorCenter
     * @return
     */
    @RequestMapping(value = "/deleteMonitorCenter", method = RequestMethod.POST)
    public OrbitResult deleteMonitorCenter(@RequestBody OrbitSysMonitorCenter orbitSysMonitorCenter) {
        if (StringUtils.isBlank(orbitSysMonitorCenter.getId())) {
            return ResultUtil.error(emptyParamCode, "The monitor id is empty");
        }
        if (monitorCenterService.hasUsers(orbitSysMonitorCenter.getId())) {
            return ResultUtil.error(cannotDeleteCode, "Can not delete monitor for there are users connected with it");
        }
        monitorCenterService.deleteMonitorCenter(orbitSysMonitorCenter);
        return ResultUtil.success();
    }
}
