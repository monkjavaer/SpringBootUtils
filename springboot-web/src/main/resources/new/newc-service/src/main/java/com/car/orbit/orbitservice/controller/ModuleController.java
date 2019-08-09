package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitSysConfig;
import com.car.orbit.orbitservice.service.IModuleService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: SysConfigController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 首页模块设置Controller
 * @Author: monkjavaer
 * @Date: 2019/03/08 19:18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    /**
     * 查询首页模块设置
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryModule", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryModuleList(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        return ResultUtil.success(moduleService.queryModuleList(userId));
    }

    /**
     * 编辑首页模块设置
     *
     * @param request
     * @param orbitSysConfig
     * @return
     */
    @RequestMapping(value = "/editModule", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult editModule(HttpServletRequest request, @RequestBody OrbitSysConfig orbitSysConfig) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        orbitSysConfig.setUserId(userId);
        moduleService.editModule(orbitSysConfig);
        return ResultUtil.success();
    }
}
