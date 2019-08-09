package com.car.trunk.controller.api;

import com.car.trunk.extranet.dto.ETrafficPoliceAlarmInfo;
import com.car.trunk.extranet.service.UnifiedAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/alarm")
public class ApiAlarmController {

    private static Logger logger = LoggerFactory.getLogger(ApiAlarmController.class);

    @Autowired
    private UnifiedAlarmService unifiedAlarmService;

    /**
     * 统计（只统计假警情）
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/getAlarmInfo/{startTime}/{endTime}", method = RequestMethod.GET)
    @ResponseBody
    public List<ETrafficPoliceAlarmInfo> getAlarmInfo(@PathVariable("startTime") String startTime,
            @PathVariable("endTime") String endTime) {
        List<ETrafficPoliceAlarmInfo> result = unifiedAlarmService.getAlarmInfo(startTime, endTime);
        return result;
    }
}
