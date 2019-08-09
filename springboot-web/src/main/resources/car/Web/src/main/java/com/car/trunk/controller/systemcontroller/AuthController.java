package com.car.trunk.controller.systemcontroller;

import com.car.base.common.constant.AppConstant;
import com.car.base.common.utilities.ApiError;
import com.car.base.common.utilities.ApiResponseBody;
import com.car.base.common.utilities.PropertiesUtil;
import com.car.base.message.MQMessage;
import com.car.base.message.MessageType;
import com.car.base.rabbit.MQFanoutSender;
import com.car.base.rabbit.QueueUtil;
import com.car.base.redis.RedisHelper;
import com.car.base.webservice.UserLoginMsg;
import com.car.trunk.auth.UserInfoCache;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.center.system.service.UserService;
import com.car.trunk.controller.systemcontroller.vo.LoginParam;
import com.car.trunk.controller.systemcontroller.vo.LogoutParam;
import com.car.trunk.dal.model.UserEntity;
import com.google.common.collect.ImmutableMap;
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
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description: 用户验证
 * Original Author: monkjavaer, 2017/12/20
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody login(@RequestBody LoginParam param, HttpSession session,HttpServletRequest request) {

        try {
            if (null == param.getUsername() || param.getUsername().isEmpty()){
                return new ApiResponseBody(ApiError.LOGIN_ERROR);
            }
            param.setUsername(param.getUsername().toLowerCase());
            PropertiesUtil propertiesUtil = new PropertiesUtil("/auth.properties");
            String userName = propertiesUtil.getPropertieValue("Auth.CA.userName");

            //初始密码是陈安分配,只加一次密，
            boolean superAdminOriginalpwd = true;
            if (userName.toLowerCase().equals(param.getUsername().toLowerCase())) {
                List<UserEntity>  superAdmin  = userService.getUserByName(userName);
                //如果被修改过就需要再加一次密
                if (superAdmin.get(0).getUpdateTime() != null){
                    superAdminOriginalpwd = false;
                }
            }

            UserInfoBO user = userService.login(param.getUsername(), param.getPassword(),superAdminOriginalpwd);
            if (null != user) {
                String token = String.valueOf(user.getUserId());
                String usercache = UserInfoCache.getInstance().get(param.getUsername());
                if (StringUtils.isNotBlank(usercache)) {

                    try {
                        MQFanoutSender mqFanoutSender = QueueUtil.getSystemWebMqSender();
                        //消息推送到MQ队列
                        MQMessage mqMessage = new MQMessage();
                        mqMessage.setMessageType(MessageType.REPEAT_LOGIN);
                        UserLoginMsg userLoginMsg = new UserLoginMsg();
                        userLoginMsg.setUserName(param.getUsername());
                        mqMessage.setBody(userLoginMsg);
                        mqFanoutSender.sendMessage(mqMessage);
                    } catch (Exception e) {
                        logger.error(e.getLocalizedMessage(), e);
                    }

                    Thread.sleep(100);
                }
                //add the cache for the user
                UserInfoCache.getInstance().add(user.getUserName(), token);
                RedisHelper.saveObject(token, user);
                session.setAttribute(AppConstant.SESSION_USER_KEY, user);
                return new ApiResponseBody(ImmutableMap.of("token", token, "user", user));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(), e);
        }

        return new ApiResponseBody(ApiError.LOGIN_ERROR);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody logout(@RequestBody LogoutParam params, HttpServletRequest request) {
        String token = params.getToken();
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);
        RedisHelper.del(token);
        request.getSession().removeAttribute(AppConstant.SESSION_USER_KEY);
        UserInfoCache.getInstance().remove(userInfoBO.getUserName().toLowerCase());

        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponseBody message(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
         String jsonStr = RedisHelper.get(token);
        if (null == jsonStr || jsonStr.isEmpty()) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO user = (UserInfoBO) JSONObject.toBean(JSONObject.fromObject(jsonStr), UserInfoBO.class);
        return new ApiResponseBody(user);
    }
}
