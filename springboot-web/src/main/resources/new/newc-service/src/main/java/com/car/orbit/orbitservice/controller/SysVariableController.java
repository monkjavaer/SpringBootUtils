package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitControlAlarmVoice;
import com.car.orbit.orbitservice.entity.OrbitSysVariable;
import com.car.orbit.orbitservice.service.ISysVariableService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: SysVariableController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 系统变量Controller
 * @Author: monkjavaer
 * @Data: 2019/3/8 8:59
 * @Version: V1.0
 */
@RestController
@RequestMapping("/systemParameter")
public class SysVariableController {
    private static  Logger logger = LoggerFactory.getLogger(SysVariableController.class);

    @Autowired
    private ISysVariableService sysVariableService;

    /**
     * 系统参数保存时间获取
     * @return 所有系统参数列表(数据库默认配置过车记录保存时长，报警信息保存时长两条记录)
     */
    @RequestMapping(value = "/queryVariableList", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryVariableList() {
        List<OrbitSysVariable> list = sysVariableService.queryVariableList();
        return ResultUtil.success(list);
    }


    /**
     * 更新系统变量
     * @param orbitSysVariable
     * @return
     */
    @RequestMapping(value = "/updateSysVariable", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateSysVariable(@RequestBody OrbitSysVariable orbitSysVariable) {
        sysVariableService.updateSysVariable(orbitSysVariable);
        return  ResultUtil.success();
    }

    /**
     * 更新报警铃声
     * @param voice 铃声实体
     */
    @RequestMapping(value = "/updateVoice", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateVoice(@RequestBody OrbitControlAlarmVoice voice) {
        sysVariableService.updateVoice(voice);
        return  ResultUtil.success();
    }

    /**
     * 查询报警铃声列表
     * @return 警情1-4级对应铃声
     */
    @RequestMapping(value = "/queryVoiceList", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryVoiceList() {
        List<OrbitControlAlarmVoice> list = sysVariableService.queryVoiceList();
        return ResultUtil.success(list);
    }
}
