/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/12/5
 */
package com.car.trunk.controller.systemcontroller;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.*;
import com.car.base.message.MQMessage;
import com.car.base.message.MessageType;
import com.car.base.rabbit.MQFanoutSender;
import com.car.base.rabbit.QueueUtil;
import com.car.base.redis.RedisHelper;
import com.car.base.webservice.UserLoginMsg;
import com.car.trunk.auth.UserInfoCache;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.center.system.service.UserLogService;
import com.car.trunk.center.system.service.UserService;
import com.car.trunk.controller.systemcontroller.vo.UpdateUserInfoVO;
import com.car.trunk.dal.dictionary.LogActionType;
import com.car.trunk.dal.dictionary.LogDataType;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.UserBO;
import com.car.trunk.dal.system.vo.UserVO;
import com.car.trunk.util.StringFormatUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/api/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserLogService userLogService;

    /**
     * 用户分页列表
     *
     * @param userVO
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryList(@RequestBody UserVO userVO, HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);
        PageList<UserBO> list = null;
        if (StringUtils.isNotEmpty(userVO.getUser())) {
            userVO.setUser(StringFormatUtil.formatRetrieveString(userVO.getUser()));
        }
        try {
            UserEntity user = userService.get(userInfoBO.getUserId());
            //如果不是超级管理员只能查看本机构的数据
            if (!EnvConstant.SUPERADMIN.equals(userInfoBO.getUserName())) {
                userVO.setMonitorCenterId(user.getMonitorCenterId());
            }
            list = userService.queryList(userVO);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(list);
    }

    /**
     * 新增用户
     *
     * @param updateUserInfoVO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody addUser(@RequestBody UpdateUserInfoVO updateUserInfoVO, HttpServletRequest request) {
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
        UserEntity userEntity = new UserEntity();
        try {

            userEntity.setUsername(updateUserInfoVO.getUsername().toLowerCase());
            userEntity.setPassword(updateUserInfoVO.getPassword());
            userEntity.setName(updateUserInfoVO.getName());
            userEntity.setGender(updateUserInfoVO.getGender());
            userEntity.setPhone(updateUserInfoVO.getPhone());
            userEntity.setEmail(updateUserInfoVO.getEmail());
            userEntity.setMonitorCenterId(updateUserInfoVO.getMonitorCenterId());
            userEntity.setRoleId(updateUserInfoVO.getRoleId());

            PropertiesUtil propertiesUtil = new PropertiesUtil("/auth.properties");
            String userName = propertiesUtil.getPropertieValue("Auth.CA.userName");
            List<UserEntity> superAdmin = userService.getUserByName(userName);
            userService.addUser(userEntity);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }

        // 记录操作日志
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                    LogActionType.ADD.value, "Add User : " + String.valueOf(userEntity.getId()));
        } catch (EntityOperateException | ValidateException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 修改用户
     *
     * @param updateUserInfoVO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody updateUser(@RequestBody UpdateUserInfoVO updateUserInfoVO, HttpServletRequest request) {
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

        UserEntity userEntity = new UserEntity();
        userEntity.setId(updateUserInfoVO.getId());
        userEntity.setUsername(updateUserInfoVO.getUsername());
        userEntity.setPassword(updateUserInfoVO.getPassword());
        userEntity.setName(updateUserInfoVO.getName());
        userEntity.setGender(updateUserInfoVO.getGender());
        userEntity.setPhone(updateUserInfoVO.getPhone());
        userEntity.setEmail(updateUserInfoVO.getEmail());
        userEntity.setMonitorCenterId(updateUserInfoVO.getMonitorCenterId());
        userEntity.setRoleId(updateUserInfoVO.getRoleId());
        userEntity.setDeleted(updateUserInfoVO.getDeleted());

        try {
            String password = userService.getPasswordById(userEntity.getId());

            if (!"".equals(userEntity.getPassword())) {
                userService.modifyUser(userEntity);
            } else {
                userEntity.setPassword(password);
                userService.modifyUser(userEntity);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        // 记录操作日志
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                    LogActionType.UPDATE.value, "Update User : " + String.valueOf(userEntity.getId()));
        } catch (EntityOperateException | ValidateException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 删除用户
     *
     * @param infoVO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody deleteUser(@RequestBody UpdateUserInfoVO infoVO, HttpServletRequest request) {
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

        try {
            UserEntity verifyEntity = userService.get(userInfoBO.getUserId());
            if (null == infoVO.getVerify() || !infoVO.getVerify().equals(verifyEntity.getPassword())) {
                return new ApiResponseBody(ApiError.WRONG_PASSWORD);
            }

            UserEntity userEntity = userService.get(infoVO.getId());

            PropertiesUtil propertiesUtil = new PropertiesUtil("/auth.properties");
            String userName = propertiesUtil.getPropertieValue("Auth.CA.userName");
            List<UserEntity> superAdmin = userService.getUserByName(userName);

            userService.deleteUser(userEntity);
            try {
                MQFanoutSender mqFanoutSender = QueueUtil.getSystemWebMqSender();
                //消息推送到MQ队列
                MQMessage mqMessage = new MQMessage();
                mqMessage.setMessageType(MessageType.DELETE_USER);
                UserLoginMsg userLoginMsg = new UserLoginMsg();
                userLoginMsg.setUserName(userEntity.getUsername());
                mqMessage.setBody(userLoginMsg);
                mqFanoutSender.sendMessage(mqMessage);
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
            /*
             * 原用户logout
             */
            UserInfoCache.getInstance().remove(userEntity.getUsername());

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }

        // 记录操作日志
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                    LogActionType.DELETE.value, "Delete User : " + String.valueOf(infoVO.getId()));
        } catch (EntityOperateException | ValidateException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody resetPassword(@RequestBody UpdateUserInfoVO infoVO, HttpServletRequest request) {
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

        try {
            UserEntity verifyEntity = userService.get(userInfoBO.getUserId());
            if (null == infoVO.getVerify() || !infoVO.getVerify().equals(verifyEntity.getPassword())) {
                return new ApiResponseBody(ApiError.WRONG_PASSWORD);
            }

            UserEntity userEntity = userService.get(infoVO.getId());

            PropertiesUtil propertiesUtil = new PropertiesUtil("/auth.properties");
            String userName = propertiesUtil.getPropertieValue("Auth.CA.userName");
            List<UserEntity> superAdmin = userService.getUserByName(userName);

            userEntity.setPassword(MD5Util.getMD5("123456").toLowerCase());
            userService.modifyUser(userEntity);
            try {
                MQFanoutSender mqFanoutSender = QueueUtil.getSystemWebMqSender();
                //消息推送到MQ队列
                MQMessage mqMessage = new MQMessage();
                mqMessage.setMessageType(MessageType.RESET_PASSWORD);
                UserLoginMsg userLoginMsg = new UserLoginMsg();
                userLoginMsg.setUserName(userEntity.getUsername());
                mqMessage.setBody(userLoginMsg);
                mqFanoutSender.sendMessage(mqMessage);
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
            /*
             * 原用户logout
             */
            UserInfoCache.getInstance().remove(userEntity.getUsername());

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }

        // 记录操作日志
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                    LogActionType.UPDATE.value, "Update User : " + String.valueOf(infoVO.getId()));
        } catch (EntityOperateException | ValidateException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(), e);
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 修改密码
     *
     * @param infoVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody changePassword(@RequestBody UpdateUserInfoVO infoVO, HttpServletRequest request) {
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

        try {
            UserEntity userEntity = userService.get(infoVO.getId());
            if (!infoVO.getOldPassword().equals(userEntity.getPassword())) {
                return new ApiResponseBody(ApiError.WRONG_PASSWORD);
            }
            if (!userInfoBO.getUserId().equals(userEntity.getId())) {
                return new ApiResponseBody(ApiError.WRONG_PASSWORD);
            }
            userEntity.setUpdateTime(new Date());
            userEntity.setPassword(infoVO.getPassword());
            userService.modifyUser(userEntity);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }

        // 记录操作日志
        try {
            userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                    LogActionType.UPDATE.value, "Update User : " + String.valueOf(infoVO.getId()));
        } catch (EntityOperateException | ValidateException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 用户名查重
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/checkUsername", method = {RequestMethod.GET})
    public @ResponseBody
    ApiResponseBody checkName(String username) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            boolean flg = userService.checkUsername(username);
            if (flg) {
                result.put("isUsable", true);
            } else {
                result.put("isUsable", false);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(result);
    }
}
