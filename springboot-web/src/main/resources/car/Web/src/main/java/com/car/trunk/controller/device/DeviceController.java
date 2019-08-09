package com.car.trunk.controller.device;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.ApiError;
import com.car.base.common.utilities.ApiResponseBody;
import com.car.base.common.utilities.DateTimeUtils;
import com.car.base.common.utilities.PageList;
import com.car.base.redis.RedisHelper;
import com.car.trunk.center.device.service.*;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.center.system.service.UserLogService;
import com.car.trunk.controller.vo.DeviceLog;
import com.car.trunk.controller.vo.DeviceTerminalVO;
import com.car.trunk.dal.device.bo.*;
import com.car.trunk.dal.device.vo.DeviceLogVO;
import com.car.trunk.dal.device.vo.DeviceSimpleVO;
import com.car.trunk.dal.device.vo.DeviceUserVO;
import com.car.trunk.dal.device.vo.DeviceVO;
import com.car.trunk.dal.dictionary.DeviceType;
import com.car.trunk.dal.dictionary.HasOnline;
import com.car.trunk.dal.dictionary.LogActionType;
import com.car.trunk.dal.dictionary.LogDataType;
import com.car.trunk.util.StringFormatUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;


/**
 * Description:设备管理
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController {

    private static Logger logger = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceStatusLogService deviceStatusLogService;

    @Autowired
    private UserLogService userLogService;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 设备分页列表
     *
     * @param deviceVO
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryList(@RequestBody DeviceVO deviceVO, HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) com.alibaba.fastjson.JSONObject.parseObject(RedisHelper.get(token), UserInfoBO.class);
        if (null == userInfoBO) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        PageList<DeviceBO> list = null;
        try {
            list = deviceService.queryList(deviceVO, userInfoBO.getUserId(), userInfoBO.getUserName());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(list);
    }


    /**
     * 根据名字，路口模糊查询摄像头列表
     *
     * @param deviceSimpleVO
     * @return
     */
    @RequestMapping(value = "/queryDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryDeviceList(@RequestBody DeviceSimpleVO deviceSimpleVO) {
        List<DeviceSimpleBO> list = null;
        if (StringUtils.isNotEmpty(deviceSimpleVO.getName())) {
            deviceSimpleVO.setName(StringFormatUtil.formatRetrieveString(deviceSimpleVO.getName()));
        }
        try {
            list = deviceService.queryDeviceList(deviceSimpleVO);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(list);
    }

    /**
     * 根据名字，路口模糊查询摄像头列表
     *
     * @param deviceSimpleVO
     * @return
     */
    @RequestMapping(value = "/queryDeviceListByUser", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryDeviceListByUser(@RequestBody DeviceSimpleVO deviceSimpleVO, HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);
        if (null == userInfoBO) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        List<DeviceUserVO> listDeviceUser = null;
        if (StringUtils.isNotEmpty(deviceSimpleVO.getName())) {
            deviceSimpleVO.setName(StringFormatUtil.formatRetrieveString(deviceSimpleVO.getName()));
        }
        try {
            listDeviceUser = deviceService.queryDeviceListByUser(deviceSimpleVO, userInfoBO.getUserId());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(listDeviceUser);
    }



    /**
     * 删除智能主机、摄像机
     *
     * @param deviceTerminalVO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody delete(@RequestBody DeviceTerminalVO deviceTerminalVO, HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);

        try {
            deviceService.deleteDevice(deviceTerminalVO.getId(), deviceTerminalVO.getTerminalId(), deviceTerminalVO.getType());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }

        // 记录操作日志
        BigInteger deviceId;
        if (DeviceType.HOST.value.equals(deviceTerminalVO.getType())) {
            deviceId = deviceTerminalVO.getTerminalId();
        } else {
            deviceId = deviceTerminalVO.getId();
        }
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.DEVICE_DATA_CHANGES.value,
                    LogActionType.DELETE.value, "delete device:" + String.valueOf(deviceId));
        } catch (EntityOperateException | ValidateException e) {
            e.printStackTrace();
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 设备状态分页列表
     *
     * @param deviceLogVO
     * @return
     */
    @RequestMapping(value = "/queryDeviceLogList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryDeviceLogList(@RequestBody DeviceLogVO deviceLogVO, HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) com.alibaba.fastjson.JSONObject.parseObject(RedisHelper.get(token), UserInfoBO.class);
        if (null == userInfoBO) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        DeviceLogDTO deviceLogDTO = null;
        DeviceLog deviceLog = new DeviceLog();
        try {
            deviceLogDTO = deviceStatusLogService.queryList(deviceLogVO, userInfoBO.getUserId(), userInfoBO.getUserName());
            deviceLog.setItemCount(deviceLogDTO.getPageList().getItemCount());
            List<DeviceLogBO> deviceLogs = deviceLogDTO.getPageList().getItems();
            for (DeviceLogBO deviceLogBO : deviceLogs) {
                if (deviceLogBO.getOnline() == HasOnline.YES.value) {
                    deviceLogBO.setRecentTime(DateTimeUtils.getTimeByTimestamp(DateTimeUtils.now()));
                }
            }
            deviceLog.setItems(deviceLogs);
            deviceLog.setTotalRate(deviceLogDTO.getTotalRate());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(deviceLog);
    }


    /**
     * 每小时定时同步摄像头设备
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoSynchronizeDevice() {

    }


    /**
     * 每天定时检查智能主机是否在线
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autocheckTerminal() {
        try {
            deviceService.updateTerminalStatus();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

}
