package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.UserQO;
import com.car.orbit.orbitservice.service.IUserManagementService;
import com.car.orbit.orbitservice.vo.PasswordResetVO;
import com.car.orbit.orbitservice.vo.UserSimpleVO;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: UserManagementService
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 用户管理Controller
 * @Author: monkjavaer
 * @Date: 2019/03/08 15:50
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userManagement")
public class UserManagementController {

    /** 用户名/姓名重复返回码 */
    private final int duplicateNameCode = 1001;

    @Autowired
    private IUserManagementService userManagementService;

    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    /**
     * 查询用户列表（不分页）
     *
     * @return
     */
    @RequestMapping(value = "/allList", method = RequestMethod.POST)
    public OrbitResult queryUserList() {
        List<UserSimpleVO> list = userManagementService.queryUserList();
        return ResultUtil.success(list);
    }

    /**
     * 查询用户列表（分页）
     *
     * @param request
     * @param userQO
     * @return
     */
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public OrbitResult queryPageList(HttpServletRequest request, @RequestBody UserQO userQO) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        userQO.setCurrentUserId(userId);
        if (userQO.getGender() != null) {
            userQO.setGender(userQO.getGender() + 1);
        }
        if (StringUtils.isNotEmpty(userQO.getName())) {
            userQO.setName(userQO.getName().trim());
        }
        PageUtil<UserSimpleVO> list = userManagementService.queryPageList(userQO);
        return ResultUtil.success(list);
    }

    /**
     * 添加用户
     *
     * @param orbitSysUser
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public OrbitResult createUser(@RequestBody OrbitSysUser orbitSysUser) {
        if (userManagementService.existDuplicate(orbitSysUser)) {
            return ResultUtil.error(duplicateNameCode, "Username or name already exist");
        }
        userManagementService.addUser(orbitSysUser);
        userManagementService.saveConfig(orbitSysUser);
        return ResultUtil.success();
    }

    /**
     * 编辑用户
     *
     * @param orbitSysUser
     * @return
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public OrbitResult editUser(@RequestBody OrbitSysUser orbitSysUser) {
        if (userManagementService.existDuplicate(orbitSysUser)) {
            return ResultUtil.error(duplicateNameCode, "Name already exist");
        }
        userManagementService.updateUser(orbitSysUser);
        return ResultUtil.success();
    }

    /**
     * 删除用户
     *
     * @param orbitSysUser 需要删除的用户
     * @return 操作结果
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public OrbitResult deleteUser(@RequestBody OrbitSysUser orbitSysUser) {
        userManagementService.deleteUser(orbitSysUser);
        return ResultUtil.success();
    }

    /**
     * 重置用户密码
     *
     * @param passwordResetVO
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public OrbitResult resetPassword(HttpServletRequest request, @RequestBody PasswordResetVO passwordResetVO) {
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserID(token);
        if (userManagementService.resetPassword(userId, passwordResetVO)) {
            //发websocket消息
            OrbitSysUser orbitSysUser = orbitSysUserMapper.selectByPrimaryKey(passwordResetVO.getUserId());
            UserMessageBody userMessageBody = new UserMessageBody();
            userMessageBody.setUserName(orbitSysUser.getUsername());
            WebSocketMsg webSocketMsg = new WebSocketMsg();
            webSocketMsg.setType(MessageType.reset);
            webSocketMsg.setBody(userMessageBody);
            webSocketManager.sendLogoutMessage(webSocketMsg);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(1001, "密码校验不通过");
        }
    }
}
