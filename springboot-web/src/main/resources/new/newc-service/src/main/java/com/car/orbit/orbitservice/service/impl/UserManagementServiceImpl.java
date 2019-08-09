package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlTask;
import com.car.orbit.orbitservice.entity.OrbitSysConfig;
import com.car.orbit.orbitservice.entity.OrbitSysRoleAuthority;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.enums.ControlTaskStatusEnum;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.mapper.OrbitControlTaskMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysConfigMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.UserQO;
import com.car.orbit.orbitservice.service.IUserManagementService;
import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.PasswordResetVO;
import com.car.orbit.orbitservice.vo.UserSimpleVO;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.TokenUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: UserManagementServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/08 16:00
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class UserManagementServiceImpl implements IUserManagementService {

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    @Autowired
    private OrbitSysRoleAuthorityMapper roleAuthorityMapper;

    @Autowired
    private OrbitSysConfigMapper configMapper;

    @Autowired
    private OrbitControlTaskMapper controlTaskMapper;

    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebSocketManager webSocketManager;

    /**
     * 查询所有用户列表（不分页）
     *
     * @return
     */
    @Override
    public List<UserSimpleVO> queryUserList() {
        String token = LocalHolder.getCurrentToken();
        String userId = TokenUtils.getUserID(token);
        OrbitSysUser user = orbitSysUserMapper.selectByPrimaryKey(userId);
        if (OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            //  超级管理员
            return orbitSysUserMapper.queryUserList(new UserQO());
        } else {
            // 非超级管理员,只能查看同机构下的用户
            UserQO userQO = new UserQO();
            userQO.setMonitorCenterId(user.getMonitorCenterId());
            return orbitSysUserMapper.queryUserList(userQO);
        }
    }

    /**
     * 查询用户列表(分页)
     *
     * @param userQO
     * @return
     */
    @Override
    public PageUtil<UserSimpleVO> queryPageList(UserQO userQO) {
        //  如果不是超级管理员,只能查看同机构下的用户;如果是超级管理员,则按照实际的查询条件, monitorCenterId可为空,也可指定查询特定机构下的用户
        String token = LocalHolder.getCurrentToken();
        String userId = TokenUtils.getUserID(token);
        OrbitSysUser currentUser = orbitSysUserMapper.selectByPrimaryKey(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(currentUser.getUsername())) {
            userQO.setMonitorCenterId(currentUser.getMonitorCenterId());
        }

        PageHelper.startPage(userQO.getPageNo(), userQO.getPageSize());
        List<UserSimpleVO> list = orbitSysUserMapper.queryUserList(userQO);
        for (UserSimpleVO user : list) {
            user.setGender(user.getGender() - 1);
            user.setDeletable(!isControlTaskAssociated(user.getId()));
        }

        return new PageUtil<>(list);
    }

    /**
     * 添加用户
     *
     * @param orbitSysUser
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_USER, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addUser(OrbitSysUser orbitSysUser) {
        if (orbitSysUser.getGender() != null) {
            orbitSysUser.setGender(orbitSysUser.getGender() + 1);
        }
        orbitSysUser.setId(UUIDUtils.generate());
        orbitSysUser.setUsername(orbitSysUser.getUsername().trim());
        orbitSysUser.setName(orbitSysUser.getName().trim());
        orbitSysUser.setDeleted(HasDeleteEnum.NO.getValue());
        orbitSysUserMapper.insert(orbitSysUser);
        OrbitSysUserRedis.saveUserInfo(orbitSysUser);
    }

    /**
     * 更新用户
     *
     * @param orbitSysUser 需要更新的用户
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_USER, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateUser(OrbitSysUser orbitSysUser) {
        //用于判断是否修改角色
        OrbitSysUser oldUser = orbitSysUserMapper.selectByPrimaryKey(orbitSysUser.getId());

        if (orbitSysUser.getGender() != null) {
            orbitSysUser.setGender(orbitSysUser.getGender() + 1);
        }
        orbitSysUser.setName(orbitSysUser.getName().trim());
        orbitSysUserMapper.updateByPrimaryKeySelective(orbitSysUser);
        OrbitSysUserRedis.saveUserInfo(orbitSysUserMapper.selectByPrimaryKey(orbitSysUser.getId()));

        //用户角色被修改
        if (!oldUser.getRoleId().equals(orbitSysUser.getRoleId())) {
            sendLogoutMessage(oldUser.getUsername());
        }
    }

    /**
     * 推送下线通知socket
     *
     * @param username 下线用户的用户名
     */
    private void sendLogoutMessage(String username) {
        UserMessageBody userMessageBody = new UserMessageBody();
        userMessageBody.setUserName(username);
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setType(MessageType.role);
        webSocketMsg.setBody(userMessageBody);
        webSocketManager.sendLogoutMessage(webSocketMsg);
    }

    /**
     * 删除用户
     *
     * @param orbitSysUser 需要删除的用户
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_USER, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitSysUserMapper.class)
    @Override
    public void deleteUser(OrbitSysUser orbitSysUser) {
        OrbitSysUser orbitSysUserUpdate = orbitSysUserMapper.selectByPrimaryKey(orbitSysUser.getId());
        orbitSysUserUpdate.setDeleted(HasDeleteEnum.YES.getValue());
        orbitSysUserMapper.updateByPrimaryKeySelective(orbitSysUserUpdate);
        OrbitSysUserRedis.saveUserInfo(orbitSysUserMapper.selectByPrimaryKey(orbitSysUser.getId()));

        // 推送下线通知
        sendLogoutMessage(orbitSysUserUpdate);
    }

    /**
     * 向被删除的用户发送socket消息
     *
     * @param orbitSysUser 被删除的用户
     */
    private void sendLogoutMessage(OrbitSysUser orbitSysUser) {
        //删除用户发websocket消息
        UserMessageBody userMessageBody = new UserMessageBody();
        userMessageBody.setUserName(orbitSysUser.getUsername());
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setType(MessageType.delete);
        webSocketMsg.setBody(userMessageBody);
        webSocketManager.sendLogoutMessage(webSocketMsg);
    }

    /**
     * 重置用户密码
     *
     * @param operatorId
     * @param passwordResetVO
     * @return
     */
    @Override
    public boolean resetPassword(String operatorId, PasswordResetVO passwordResetVO) {
        // 校验密码
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("id", operatorId)
                .andEqualTo("password", passwordResetVO.getMyPassword());
        if (orbitSysUserMapper.selectOneByExample(example) == null) {
            return false;
        }

        OrbitSysUser orbitSysUser = new OrbitSysUser();
        orbitSysUser.setId(passwordResetVO.getUserId());
        orbitSysUser.setPassword("123456");
        orbitSysUserMapper.updateByPrimaryKeySelective(orbitSysUser);

        return true;
    }

    /**
     * 检查用户名或者姓名是否存在重复
     *
     * @return true-重复，false-不重复
     */
    @Override
    public boolean existDuplicate(OrbitSysUser orbitSysUser) {
        Example example = new Example(OrbitSysUser.class);
        example.and()
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andCondition("BINARY username = ", orbitSysUser.getUsername());
        example.or()
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andCondition("BINARY name = ", orbitSysUser.getName())
                .andEqualTo("monitorCenterId", orbitSysUser.getMonitorCenterId());
        if (StringUtils.isNotBlank(orbitSysUser.getId())) {
            example.and()
                    .andNotEqualTo("id", orbitSysUser.getId());
        }
        return orbitSysUserMapper.selectCountByExample(example) > 0;
    }

    /**
     * 配置所有权限均在首页显示
     *
     * @param orbitSysUser 需要配置的用户
     */
    @Override
    public void saveConfig(OrbitSysUser orbitSysUser) {
        String idStr = getRoleAuthorityIdStr(orbitSysUser.getRoleId());
        OrbitSysConfig config = new OrbitSysConfig();
        config.setId(UUIDUtils.generate());
        config.setUserId(orbitSysUser.getId());
        config.setAuthorityIdstr(idStr);
        configMapper.insert(config);
    }

    /**
     * 根据角色id获取权限id字符串
     *
     * @param roleId
     * @return
     */
    private String getRoleAuthorityIdStr(String roleId) {
        Example example = new Example(OrbitSysRoleAuthority.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<OrbitSysRoleAuthority> roleAuthorities = roleAuthorityMapper.selectByExample(example);

        StringBuilder sb = new StringBuilder();
        int size = roleAuthorities.size();
        for (int i = 0; i < size; i++) {
            sb.append(roleAuthorities.get(i).getAuthorityId());
            if (i != size - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    /**
     * 用户是否和布控任务有关联
     *
     * @param userId
     * @return
     */
    private boolean isControlTaskAssociated(String userId) {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ControlTaskStatusEnum.ON_CONTROL.getValue());
        statusList.add(ControlTaskStatusEnum.PENDING.getValue());

        // 是否为任一布控任务的接收人
        Example example = new Example(OrbitControlTask.class);
        example.createCriteria()
                .andLike("receivorId", "%" + userId + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andIn("status", statusList);
        if (controlTaskMapper.selectCountByExample(example) > 0) {
            return true;
        }

        // 是否存在“布控中”和“审批中”的布控任务
        example = new Example(OrbitControlTask.class);
        example.createCriteria()
                .andEqualTo("creatorId", userId)
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andIn("status", statusList);
        return controlTaskMapper.selectCountByExample(example) > 0;
    }
}

