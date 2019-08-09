package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.qo.LogQO;
import com.car.orbit.orbitservice.service.ILogService;
import com.car.orbit.orbitservice.vo.LogVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: LogController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 日志
 * @Author: monkjavaer
 * @Data: 2019/3/13 20:19
 * @Version: V1.0
 */
@RestController
@RequestMapping("/log")
public class LogController {

    /**
     * logService
     */
    @Autowired
    private ILogService logService;

    /**
     * 日志分页列表
     * @param logQO
     * @return
     */
    @RequestMapping(value = "/queryPageList", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestBody LogQO logQO) {
        if (StringUtils.isNotBlank(logQO.getUsername())){
            logQO.setUsername(logQO.getUsername().trim());
        }
        PageUtil<LogVO> list = logService.queryPageList(logQO);
        return ResultUtil.success(list);
    }
}
