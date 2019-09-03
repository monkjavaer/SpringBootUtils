package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitSysRole;
import com.car.orbit.orbitservice.exception.AuthFailException;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.RoleQO;
import com.car.orbit.orbitservice.service.IRoleService;
import com.car.orbit.orbitservice.vo.RoleOperatorVO;
import com.car.orbit.orbitservice.vo.RoleVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: RoleController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 角色管理Controller
 * @Author: monkjavaer
 * @Date: 2019/03/12 14:23
 * @Version: V1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /** 重复数据 返回码 */
    private final int duplicateDataCode = 1001;

    /** 修改或删除角色时，存在登录的用户，不能删除 返回码 */
    private final int relatedUsersCode = 1002;

    /** 删除角色时密码认证失败返回码 */
    private final int authFailCode = 1003;

    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色列表
     *
     * @return
     */
    @RequestMapping(value = "/queryRoleList", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult queryRoleList() {
        List<OrbitSysRole> list = roleService.queryRoleList();
        return ResultUtil.success(list);
    }

    /**
     * 角色分页查询
     *
     * @param roleQO
     * @return
     */
    @RequestMapping(value = "/queryPageList", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryRoleList(@RequestBody RoleQO roleQO) {
        PageUtil<RoleVO> list = roleService.queryPageList(roleQO);
        return ResultUtil.success(list);
    }

    /**
     * 添加角色
     *
     * @param request
     * @param roleOperatorVO
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addRole(HttpServletRequest request, @RequestBody RoleOperatorVO roleOperatorVO) {
        roleOperatorVO.setOperatorId(TokenUtils.getUserID(request.getHeader("token")));
        roleOperatorVO.setOperatorName(TokenUtils.getUserName(request.getHeader("token")));
        try {
            roleService.addRole(roleOperatorVO);
        } catch (DuplicateDataException e) {
            return ResultUtil.error(duplicateDataCode, "角色名已存在");
        }
        return ResultUtil.success();
    }

    /**
     * 更新角色
     *
     * @param request
     * @param roleOperatorVO
     * @return
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateRole(HttpServletRequest request, @RequestBody RoleOperatorVO roleOperatorVO) {
        roleOperatorVO.setOperatorId(TokenUtils.getUserID(request.getHeader("token")));
        roleOperatorVO.setOperatorName(TokenUtils.getUserName(request.getHeader("token")));
        try {
            roleService.updateRole(roleOperatorVO);
        } catch (DuplicateDataException e) {
            return ResultUtil.error(duplicateDataCode, "角色名已存在");
        }
        return ResultUtil.success();
    }

    /**
     * 删除角色
     *
     * @param request
     * @param roleOperatorVO
     * @return
     */
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteRole(HttpServletRequest request, @RequestBody RoleOperatorVO roleOperatorVO) {
        roleOperatorVO.setOperatorId(TokenUtils.getUserID(request.getHeader("token")));
        try {
            roleService.deleteRole(roleOperatorVO);
        } catch (AuthFailException e) {
            return ResultUtil.error(authFailCode, e.getMessage());
        } catch (RelationshipException e) {
            return ResultUtil.error(relatedUsersCode, e.getMessage());
        }
        return ResultUtil.success();
    }
}
