package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.annotation.IgnoreToken;
import com.car.orbit.orbitservice.bo.MapConfig;
import com.car.orbit.orbitservice.bo.UserBO;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.service.IAlarmService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.vo.PasswordResetVO;
import com.car.orbit.orbitservice.vo.UserInfoVO;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Title: UserController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 用户Controller
 * @Author: monkjavaer
 * @Date: 2019/03/08 15:51
 * @Version: V1.0
 */
@RestController
@RequestMapping("/operator")
public class UserController {

    /** 登录失败返回码 */
    private final int loginFailCode = 1001;

    /** 密码错误返回码 */
    private final int wrongPasswordCode = 1002;

    /** 姓名重复返回码 */
    private final int duplicateParamCode = 1003;

    /** 用户名/密码为空返回码 */
    private final int emptyParamCode = 1004;

    /** 用户登录接口返回的结果名称 */
    private final String loginReturnParamName = "token";

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private IUserService userService;

    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    @Autowired
    private IAlarmService alarmService;


    /**
     * 登录
     *
     * @param orbitSysUser
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @IgnoreToken
    public OrbitResult userLogin(@RequestBody OrbitSysUser orbitSysUser) {
        if (StringUtils.isBlank(orbitSysUser.getUsername()) || StringUtils.isBlank(orbitSysUser.getPassword())) {
            return ResultUtil.error(emptyParamCode, "Username or password is empty");
        }
        try {
            String token = userService.login(orbitSysUser);
            return ResultUtil.success(token, loginReturnParamName);
        } catch (LoginException e) {
            return ResultUtil.error(loginFailCode, e.getMessage());
        }
    }

    /**
     * 查询用户基础信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserSimpleInfo", method = RequestMethod.GET)
    public OrbitResult queryUserSimpleInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        UserInfoVO simpleVO = userService.queryUserSimpleInfo(userId);
        if (simpleVO.getGender() != null) {
            simpleVO.setGender(simpleVO.getGender() - 1);
        }
        return ResultUtil.success(simpleVO);
    }

    /**
     * 查询用户详细信息，用户切换语言设置也调这个接口
     *
     * @return
     */
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.GET)
    public OrbitResult queryUserInfo(HttpServletRequest request, String language) {
        String token = request.getHeader("token");

        try {
            if (!StringUtils.isEmpty(language)) {
                OrbitSysUser userInRedis = redisClient.getValue(token, OrbitSysUser.class);
                Optional.ofNullable(userInRedis).ifPresent(user -> {
                    user.setLanguage(language);
                    redisClient.save(token, JsonUtils.toJSONString(user));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userId = TokenUtils.getUserID(token);
        UserBO userBO = userService.queryUserInfo(userId);

        userBO.setMapConfig(readMapConfig());
        userBO.setSocketIP(PropertyReaderUtils.getProValue("user.socket.ip"));
        userBO.setAlarmNumber(alarmService.queryUntreatedAlarmNumber());

        return ResultUtil.success(userBO);
    }

    /**
     * 编辑用户基础信息
     *
     * @param token 用户token
     * @param orbitSysUser 修改的用户信息
     * @return 操作结果
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public OrbitResult editUser(@RequestHeader String token, @RequestBody OrbitSysUser orbitSysUser) {
        String userId = TokenUtils.getUserID(token);
        // OrbitSysUser oldUser = redisClient.get(BaseBusinessRedis.LOGIN_USER, token, OrbitSysUser.class);
        OrbitSysUser oldUser = redisClient.getValue(token, OrbitSysUser.class);
        orbitSysUser.setMonitorCenterId(oldUser.getMonitorCenterId());
        orbitSysUser.setId(userId);
        if (userService.existDuplicate(orbitSysUser)) {
            return ResultUtil.error(duplicateParamCode, "Name is already exist");
        }
        userService.editUserInfo(orbitSysUser);
        return ResultUtil.success();
    }

    /**
     * 重置密码
     *
     * @param passwordResetVO
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public OrbitResult resetPassword(@RequestBody PasswordResetVO passwordResetVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        if (userService.resetPassword(userId, passwordResetVO.getOldPassword(), passwordResetVO.getNewPassword())) {
            //发websocket消息
            OrbitSysUser orbitSysUser = orbitSysUserMapper.selectByPrimaryKey(userId);
            UserMessageBody userMessageBody = new UserMessageBody();
            userMessageBody.setUserName(orbitSysUser.getUsername());
            WebSocketMsg webSocketMsg = new WebSocketMsg();
            webSocketMsg.setType(MessageType.reset);
            webSocketMsg.setBody(userMessageBody);
            webSocketManager.sendLogoutMessage(webSocketMsg);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(wrongPasswordCode, "原密码错误");
        }
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public OrbitResult logout(@RequestHeader String token) {
        try {
            userService.logout(token);
        } catch (IllegalParamException e) {
            // 参数异常代表用户信息已经不存在于redis了，直接返回前端成功即可
            return ResultUtil.success();
        }
        return ResultUtil.success();
    }

    /**
     * 从配置文件读取全局地图配置
     */
    private MapConfig readMapConfig() {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setRouteServerIp(PropertyReaderUtils.getProValue("map.config.routeServerIp"));
        mapConfig.setMapUrl(PropertyReaderUtils.getProValue("map.config.mapUrl"));
        mapConfig.setLayers(PropertyReaderUtils.getProValue("map.config.layers"));
        mapConfig.setFormat(PropertyReaderUtils.getProValue("map.config.format"));
        mapConfig.setZoom(Integer.valueOf(PropertyReaderUtils.getProValue("map.config.zoom")));
        mapConfig.setMinZoom(Integer.valueOf(PropertyReaderUtils.getProValue("map.config.minZoom")));
        mapConfig.setDoubleClickZoom(Boolean.parseBoolean(PropertyReaderUtils.getProValue("map.config.doubleClickZoom")));
        mapConfig.setAttributionControl(Boolean.parseBoolean(PropertyReaderUtils.getProValue("map.config.attributionControl")));
        mapConfig.setZoomControl(Boolean.parseBoolean(PropertyReaderUtils.getProValue("map.config.zoomControl")));
        List<Double> center = new ArrayList<>();
        center.add(Double.valueOf(PropertyReaderUtils.getProValue("map.config.center1")));
        center.add(Double.valueOf(PropertyReaderUtils.getProValue("map.config.center2")));
        mapConfig.setCenter(center);
        mapConfig.setIsWMS(Boolean.parseBoolean(PropertyReaderUtils.getProValue("map.config.isWMS")));
        return mapConfig;
    }
}
