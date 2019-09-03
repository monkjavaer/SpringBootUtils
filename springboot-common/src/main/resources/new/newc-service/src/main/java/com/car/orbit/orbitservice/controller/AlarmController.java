package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.bo.AlarmDetailBO;
import com.car.orbit.orbitservice.entity.OrbitControlAlarm;
import com.car.orbit.orbitservice.enums.AlarmStatusEnum;
import com.car.orbit.orbitservice.qo.AlarmQO;
import com.car.orbit.orbitservice.service.IAlarmService;
import com.car.orbit.orbitservice.vo.AlarmOneFileVO;
import com.car.orbit.orbitservice.vo.AlarmVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Title: AlarmController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 报警controller
 * @Author: monkjavaer
 * @Data: 2019/4/1 11:27
 * @Version: V1.0
 */
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    private static Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private IAlarmService alarmService;


    /**
     * 查询实时预警(返回最近七天前十条警情)
     * @return List<AlarmVO>
     */
    @RequestMapping(value = "/queryRealTimeAlarm", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryRealTimeAlarm() {
        String endTime = DateUtils.format(new Date());
        String startTime = DateUtils.format(DateUtils.addDays(endTime, -7));
        List<AlarmVO> list = alarmService.queryRealTimeAlarm(startTime,endTime);
        return ResultUtil.success(list);
    }


    /**
     * 历史警情分页列表
     * @param alarmQO
     * @return
     */
    @RequestMapping(value = "/queryHistoricalAlarm", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryHistoricalAlarm(@RequestBody AlarmQO alarmQO) {
        if (alarmQO.getStatus() != null && alarmQO.getStatus() == AlarmStatusEnum.ALL.getValue()){
            alarmQO.setStatus(null);
        }
        PageUtil<AlarmVO> list = alarmService.queryHistoricalAlarm(alarmQO);
        return ResultUtil.success(list);
    }

    /**
     * 开启一车一档，预警
     * @param alarmQO
     * @return
     */
    @RequestMapping(value = "/queryAlarmOneFile", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryAlarmOneFile(@RequestBody AlarmQO alarmQO) {
        if (alarmQO.getStatus() != null && alarmQO.getStatus() == AlarmStatusEnum.ALL.getValue()){
            alarmQO.setStatus(null);
        }
        PageUtil<AlarmOneFileVO> list = alarmService.queryAlarmOneFile(alarmQO);
        return ResultUtil.success(list);
    }

    /**
     * 开启一车一档，预警，详细信息分页
     * @param alarmQO （传入车牌号和开启一车一档的条件）
     * @return
     */
    @RequestMapping(value = "/queryAlarmOneFileDetail", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryAlarmOneFileDetail(@RequestBody AlarmQO alarmQO) {
        if (alarmQO.getStatus() != null && alarmQO.getStatus() == AlarmStatusEnum.ALL.getValue()){
            alarmQO.setStatus(null);
        }
        PageUtil<AlarmVO> list = alarmService.queryHistoricalAlarmOneFile(alarmQO);
        return ResultUtil.success(list);
    }

    /**
     * 根据主键查询预警对比信息
     * @param alarmId
     * @return
     */
    @GetMapping(value = "/alarmDetail")
    @ResponseBody
    public OrbitResult alarmDetail(String alarmId) {
        AlarmDetailBO alarmDetailBO = alarmService.alarmDetail(alarmId);
        return ResultUtil.success(alarmDetailBO);
    }

    /**
     * 更新警情
     * @param orbitControlAlarm
     * @return
     */
    @RequestMapping(value = "/updateAlarm", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateAlarm(@RequestBody OrbitControlAlarm orbitControlAlarm) {
        if (alarmService.updateAlarm(orbitControlAlarm)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(1001, "该条警情已被处理");
        }
    }
}
