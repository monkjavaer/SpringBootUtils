/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/12/5
 */
package com.car.trunk.controller.systemcontroller;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.ApiError;
import com.car.base.common.utilities.ApiResponseBody;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.base.model.models.ResultTuple;
import com.car.base.redis.RedisHelper;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.center.system.service.RoleService;
import com.car.trunk.center.system.service.UserLogService;
import com.car.trunk.center.system.service.UserService;
import com.car.trunk.controller.systemcontroller.vo.UpdateRoleAuthorityVO;
import com.car.trunk.dal.dictionary.LogActionType;
import com.car.trunk.dal.dictionary.LogDataType;
import com.car.trunk.dal.model.AuthorityEntity;
import com.car.trunk.dal.model.RoleEntity;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.RoleListBO;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * 角色管理
 */
@Controller
@RequestMapping(value = "/api/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserLogService userLogService;
    /**
     * 用户接口服务
     */
    @Autowired
    private UserService userService;
    /**
     * 获取权限信息列表
     * @return
     */
    @RequestMapping(value = "/queryAuthorityList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponseBody queryAuthorityList() {
        List<AuthorityEntity> list = null;
        try {
            list = roleService.queryAuthorityList();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(list);
    }

    /**
     * 获取角色及权限列表
     * @return
     */
    @RequestMapping(value = "/queryRoleList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponseBody queryRoleList(HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);
        ResultTuple<RoleListBO> list = null;
        try {
            list = roleService.queryRoleList();
            if (!EnvConstant.SUPERADMIN.equals(userInfoBO.getUserName())) {
                UserEntity user = userService.get(userInfoBO.getUserId());
                Iterator<RoleListBO> iterator = list.items.iterator();
                BigInteger roleSuperAdminId = BigInteger.valueOf(2);
                BigInteger roleAdminId = BigInteger.valueOf(1);
                while (iterator.hasNext()){
                    RoleListBO roleListBO = iterator.next();
                    if (roleSuperAdminId.equals(roleListBO.getRoleId())||roleAdminId.equals(roleListBO.getRoleId())){
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(list);
    }


    /**
     * 新增角色
     * @param roleName
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponseBody add(@RequestParam String roleName) {
        try {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(SnowflakeIdWorkerUtil.generateId());
            roleEntity.setName(roleName);
            roleService.addRole(roleEntity);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 修改角色权限
     * @param roleEntity
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody update(@RequestBody RoleEntity roleEntity) {
        try {
            roleService.modifyRole(roleEntity);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 删除角色
     * @param roleEntity
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody delete(@RequestBody RoleEntity roleEntity) {
        try {
            roleService.deleteRole(roleEntity);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }

    /**
     * 更新角色
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateRoleAuthority", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody updateRoleAuthority(@RequestBody UpdateRoleAuthorityVO vo, HttpServletRequest request) {
        try {
            // 验证登录状况
            String token = request.getHeader("authorization");
            if (null == token) {
                return new ApiResponseBody(ApiError.UNKNOWN_USER);
            }
            UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                    JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);

            roleService.updateRoleAuthority(vo.getRoleId(), vo.getAuthorityName());

            // 记录操作日志
            try {
                userLogService.addUserLog(userInfoBO.getUserId(), LogDataType.SYSTEM_DATA_CHANGES.value,
                        LogActionType.UPDATE.value, "update role authority!");
            } catch (EntityOperateException | ValidateException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return new ApiResponseBody(ApiResponseBody.Result.success);
    }
}
