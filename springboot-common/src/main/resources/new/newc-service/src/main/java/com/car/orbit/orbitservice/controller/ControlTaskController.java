package com.car.orbit.orbitservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.exception.OverControlTaskException;
import com.car.orbit.orbitservice.qo.ApproverTaskQO;
import com.car.orbit.orbitservice.qo.ControlTaskQO;
import com.car.orbit.orbitservice.service.IControlTaskService;
import com.car.orbit.orbitservice.vo.ControlTaskVO;
import com.car.orbit.orbitutil.exception.OrbitException;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.controller
 * @ClassName: ControlTaskController
 * @Description: 布控任务controller
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 15:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 15:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RestController
@RequestMapping("/controlTask")
public class ControlTaskController {

    /** 任务名称重复返回码 */
    private final int duplicateParamCode = 1001;

    /** 存在无效的设备返回码 */
    private final int invalidDeviceCode = 1002;

    /** 存在无效的接收用户返回码 */
    private final int invalidReceiverCode = 1003;

    /** 状态已变动返回码 */
    private final int needRefreshCode = 1004;

    @Autowired
    IControlTaskService controlTaskService;

    /**
     * @param qo，查询条件
     * @return
     * @description 查询
     * @author: zks
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult search(@RequestBody ControlTaskQO qo) {
        PageUtil<ControlTaskVO> list = controlTaskService.search(qo);
        return ResultUtil.success(list);
    }

    /**
     * @param taskId，布控任务详情
     * @return
     * @description 详情
     * @author: zks
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult getTask(@RequestParam String taskId, boolean isEdit) {
        ControlTaskBO bo = controlTaskService.getTask(taskId, isEdit);
        return ResultUtil.success(bo);
    }

    /**
     * @return
     * @description 添加任务前判断当前用户正在执行任务是否达到上限，能否允许添加
     * @author: zks
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult check() {
        //增加判断，单个用户最多
        boolean flag = controlTaskService.check();
        return ResultUtil.success(flag);
    }

    /**
     * @return
     * @description 添加任务前判断当前用户正在执行任务是否达到上限，能否允许添加
     * @author: zks
     */
    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult checkName(@RequestParam String newName, String taskId) {
        //增加判断，单个用户最多
        boolean flag = controlTaskService.checkName(newName, taskId);
        return ResultUtil.success(flag);
    }

    /**
     * @param bo，添加布控任务
     * @return
     * @description 添加【添加后直接进入待审批状态】
     * @author: zks
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addTask(@RequestBody ControlTaskBO bo) {
        if (controlTaskService.checkName(bo.getTask().getTaskName(), null)) {
            return ResultUtil.error(duplicateParamCode, "Name already exist");
        }
        if (!controlTaskService.validateDevice(bo.getTask().getDevices())) {
            return ResultUtil.error(invalidDeviceCode, "Exist invalid device");
        }
        if (!controlTaskService.validateReceiver(bo.getTask().getReceivorId())) {
            return ResultUtil.error(invalidReceiverCode, "Exist invalid receive user");
        }
        controlTaskService.addTask(bo);
        return ResultUtil.success();
    }

    /**
     * @param bo，修改布控任务
     * @return
     * @description 修改【只有审批不通过才会修改】
     * @author: zks
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult edit(@RequestBody ControlTaskBO bo) {
        if (!controlTaskService.validateDevice(bo.getTask().getDevices())) {
            return ResultUtil.error(invalidDeviceCode, "Exist invalid device");
        }
        if (!controlTaskService.validateReceiver(bo.getTask().getReceivorId())) {
            return ResultUtil.error(invalidReceiverCode, "Exist invalid receive user");
        }
        controlTaskService.editTask(bo);
        return ResultUtil.success();
    }

    /**
     * @param taskId，结束布控任务
     * @return
     * @description 结束布控【布控中才能结束布控】
     * @author: zks
     */
    @RequestMapping(value = "/overTask", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult overTask(@RequestParam String taskId) {
        // 组装成json object格式，方便日志切面操作
        JSONObject taskIdObject = new JSONObject();
        taskIdObject.put("id", taskId);
        try {
            controlTaskService.overTask(taskIdObject);
        } catch (OverControlTaskException e) {
            return ResultUtil.error(ResponseType.UNKNOWN.getCode(), e.getMessage());
        }
        return ResultUtil.success();
    }

    /**
     * @param taskId，删除布控任务
     * @return
     * @description 删除布控【结束后才能删除】
     * @author: zks
     */
    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult deleteTask(@RequestParam String taskId) {
        // 组装成json object格式，方便日志切面操作
        JSONObject taskIdObject = new JSONObject();
        taskIdObject.put("id", taskId);
        controlTaskService.deleteTask(taskIdObject);
        return ResultUtil.success();
    }

    /**
     * @param qo，查询条件
     * @return
     * @description 审批查询
     * @author: zks
     */
    @RequestMapping(value = "/approverSearch", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult approverSearch(@RequestBody ControlTaskQO qo) {
        PageUtil<ControlTaskVO> list = controlTaskService.search(qo);
        return ResultUtil.success(list);
    }

    /**
     * @param qo
     * @return
     * @description 布控审批
     * @author: zks
     */
    @RequestMapping(value = "/approveTask", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult approveTask(@RequestBody ApproverTaskQO qo) {
        try {
            boolean flag = controlTaskService.approvalTask(qo);
            return ResultUtil.success(flag);
        } catch (OrbitException e) {
            return ResultUtil.error(needRefreshCode, "The task status is changed, please refresh");
        }
    }
}
