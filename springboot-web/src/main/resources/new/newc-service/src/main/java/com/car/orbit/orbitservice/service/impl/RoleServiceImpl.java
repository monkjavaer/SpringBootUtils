package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitSysAuthority;
import com.car.orbit.orbitservice.entity.OrbitSysRole;
import com.car.orbit.orbitservice.entity.OrbitSysRoleAuthority;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.exception.AuthFailException;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.mapper.OrbitSysAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.RoleQO;
import com.car.orbit.orbitservice.service.IRoleService;
import com.car.orbit.orbitservice.vo.RoleAuthorityVO;
import com.car.orbit.orbitservice.vo.RoleOperatorVO;
import com.car.orbit.orbitservice.vo.RoleVO;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.TokenUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Title: RoleServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 角色服务实现类
 * @Author: monkjavaer
 * @Date: 2019/03/12 14:31
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements IRoleService {

    public static final String SUPER_USER_ROLE = "管理员角色组";

    @Autowired
    private OrbitSysRoleMapper orbitSysRoleMapper;

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    @Autowired
    private OrbitSysRoleAuthorityMapper orbitSysRoleAuthorityMapper;

    @Autowired
    private OrbitSysAuthorityMapper orbitSysAuthorityMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private WebSocketManager webSocketManager;

    /**
     * 查询角色列表（前端下拉框使用）
     *
     * @return
     */
    @Override
    public List<OrbitSysRole> queryRoleList() {
        Example example = new Example(OrbitSysRole.class);
        example.setOrderByClause("CREATE_TIME DESC");
        example.createCriteria().andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andNotEqualTo("roleName", SUPER_USER_ROLE);
        return orbitSysRoleMapper.selectByExample(example);
    }

    /**
     * 查询角色列表（分页）
     *
     * @param roleQO
     * @return
     */
    @Override
    public PageUtil<RoleVO> queryPageList(RoleQO roleQO) {
        PageHelper.startPage(roleQO.getPageNo(), roleQO.getPageSize());

        Example example = new Example(OrbitSysRole.class);
        example.setOrderByClause("CREATE_TIME DESC");
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(roleQO.getRoleId())) {
            criteria.andEqualTo("id", roleQO.getRoleId());
        }
        if (!StringUtils.isEmpty(roleQO.getCreator())) {
            criteria.andLike("creator", "%" + roleQO.getCreator() + "%");
        }
        criteria.andNotEqualTo("roleName", SUPER_USER_ROLE);
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitSysRole> list = orbitSysRoleMapper.selectByExample(example);

        PageUtil pageUtil = new PageUtil(list);

        // 系统所有的权限
        List<OrbitSysAuthority> allAuthorities = orbitSysAuthorityMapper.selectAll();

        List<RoleVO> roleVOList = new ArrayList<>();
        for (OrbitSysRole item : list) {
            RoleVO roleVO = new RoleVO();
            roleVO.setId(item.getId());
            roleVO.setRoleName(item.getRoleName());
            roleVO.setCreator(item.getCreator());
            roleVO.setCreateTime(DateUtils.format(item.getCreateTime()));

            OrbitSysUser orbitSysUser = new OrbitSysUser();
            orbitSysUser.setId(item.getCreatorId());
            orbitSysUser = orbitSysUserMapper.selectByPrimaryKey(orbitSysUser);
            roleVO.setCreatorPhoto(orbitSysUser.getPhotoUrl());

            roleVO.setAuthorityList(queryRoleAuthorityList(allAuthorities, item.getId()));
            roleVOList.add(roleVO);
        }

        return new PageUtil<>(pageUtil.getPageIndex(), pageUtil.getPageSize(),
                (int) pageUtil.getItemCount(), pageUtil.getPageCount(), roleVOList);
    }

    /**
     * 添加角色
     *
     * @param roleOperatorVO
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROLE, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addRole(RoleOperatorVO roleOperatorVO) throws DuplicateDataException {
        if (checkRoleNameExist(roleOperatorVO.getRoleName(), null)) {
            throw new DuplicateDataException("Already exist this role");
        }
        OrbitSysRole role = new OrbitSysRole();
        role.setId(UUIDUtils.generate());
        role.setRoleName(roleOperatorVO.getRoleName());
        role.setCreatorId(roleOperatorVO.getOperatorId());
        role.setCreator(roleOperatorVO.getOperatorName());
        role.setCreateTime(new Date());
        role.setUpdatorId(roleOperatorVO.getOperatorId());
        role.setUpdator(roleOperatorVO.getOperatorName());
        role.setUpdateTime(new Date());
        role.setDeleted(HasDeleteEnum.NO.getValue());
        orbitSysRoleMapper.insert(role);

        if (!StringUtils.isEmpty(roleOperatorVO.getAuthorityIdStr())) {
            String[] idArray = roleOperatorVO.getAuthorityIdStr().split(",");
            batchInsertRoleAuthority(idArray, role.getId());
        }
    }

    /**
     * 更新角色
     *
     * @param roleOperatorVO
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROLE, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateRole(RoleOperatorVO roleOperatorVO) throws DuplicateDataException {
        String roleId = roleOperatorVO.getRoleId();
        if (checkRoleNameExist(roleOperatorVO.getRoleName(), roleId)) {
            throw new DuplicateDataException("Already exist this role");
        }

        // 更新角色名
        if (!StringUtils.isEmpty(roleOperatorVO.getRoleName())) {
            OrbitSysRole role = new OrbitSysRole();
            role.setId(roleId);
            role.setRoleName(roleOperatorVO.getRoleName());
            orbitSysRoleMapper.updateByPrimaryKeySelective(role);
        }

        // 更新角色权限
        Example example = new Example(OrbitSysRoleAuthority.class);
        example.createCriteria()
                .andEqualTo("roleId", roleId);
        orbitSysRoleAuthorityMapper.deleteByExample(example);
        if (!StringUtils.isEmpty(roleOperatorVO.getAuthorityIdStr())) {
            String[] idArray = roleOperatorVO.getAuthorityIdStr().split(",");
            batchInsertRoleAuthority(idArray, roleId);
        }

        OrbitSysRole role = new OrbitSysRole();
        role.setId(roleId);
        role.setUpdatorId(roleOperatorVO.getOperatorId());
        role.setUpdator(roleOperatorVO.getOperatorName());
        role.setUpdateTime(new Date());
        orbitSysRoleMapper.updateByPrimaryKeySelective(role);

        sendLogoutMessageToLoginUser(roleId);

    }

    /**
     * 给当前角色下登录的用户发送下线通知
     *
     * @param roleId 角色id
     */
    private void sendLogoutMessageToLoginUser(String roleId) {
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("roleId", roleId)
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());

        List<OrbitSysUser> userList = orbitSysUserMapper.selectByExample(example);
        Set<String> idSet = new HashSet<>();
        userList.stream().forEach(orbitSysUser -> idSet.add(orbitSysUser.getId()));

        // boolean result = false;
        // Map<String, String> map = redisClient.get(BaseBusinessRedis.LOGIN_USER);
        // for (Map.Entry<String, String> entry : map.entrySet()) {
        //     String token = entry.getKey();
        //     if (idSet.contains(TokenUtils.getUserID(token))) {
        //         result = true;
        //         break;
        //     }
        // }
        // return result;

        if (CollectionUtils.isEmpty(idSet)) {
            return;
        }
        // 检测redis里面是否有该角色下的用户登录，有则放入集合并返回
        Set<String> allKeys = redisClient.getAllKeys();
        allKeys.forEach(key -> {
            if (TokenUtils.isLegalToken(key) && idSet.contains(TokenUtils.getUserID(key))) {
                OrbitSysUser user = redisClient.getValue(key, OrbitSysUser.class);
                //重复登录发websocket消息
                UserMessageBody userMessageBody = new UserMessageBody();
                userMessageBody.setUserName(user.getUsername());
                WebSocketMsg webSocketMsg = new WebSocketMsg();
                webSocketMsg.setType(MessageType.role);
                webSocketMsg.setBody(userMessageBody);
                webSocketManager.sendLogoutMessage(webSocketMsg);
                // 删除旧的用户token
                redisClient.delete(key);
            }
        });
    }

    /**
     * 删除角色
     *
     * @param roleOperatorVO
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_ROLE, actionType = OrbitServiceConstant.ACTION_DELETE)
    @Override
    public void deleteRole(RoleOperatorVO roleOperatorVO) throws AuthFailException, RelationshipException {
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("id", roleOperatorVO.getOperatorId())
                .andEqualTo("password", roleOperatorVO.getMyPassword());
        if (orbitSysUserMapper.selectOneByExample(example) == null) {
            throw new AuthFailException("Password is wrong");
        }

        example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("roleId", roleOperatorVO.getRoleId());
        if (orbitSysUserMapper.selectCountByExample(example) > 0) {
            throw new RelationshipException("There are users who already login under this role");
        }

        OrbitSysRole orbitSysRole = new OrbitSysRole();
        orbitSysRole.setId(roleOperatorVO.getRoleId());
        orbitSysRole.setDeleted(HasDeleteEnum.YES.getValue());
        orbitSysRoleMapper.updateByPrimaryKeySelective(orbitSysRole);
    }

    /**
     * 批量添加角色权限
     *
     * @param authorityIdArray
     * @param roleId
     */
    private void batchInsertRoleAuthority(String[] authorityIdArray, String roleId) {
        for (int i = 0; i < authorityIdArray.length; i++) {
            OrbitSysRoleAuthority roleAuthority = new OrbitSysRoleAuthority();
            roleAuthority.setId(UUIDUtils.generate());
            roleAuthority.setRoleId(roleId);
            roleAuthority.setAuthorityId(authorityIdArray[i]);
            roleAuthority.setOperateAdd(0);
            roleAuthority.setOperateUpdate(0);
            roleAuthority.setOperateDelete(0);
            orbitSysRoleAuthorityMapper.insert(roleAuthority);
        }
    }

    /**
     * 检查角色名是否重名
     *
     * @param roleName
     * @param roleId
     * @return
     */
    private boolean checkRoleNameExist(String roleName, String roleId) {
        Example example = new Example(OrbitSysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName);
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (roleId != null) {
            criteria.andNotEqualTo("id", roleId);
        }
        return orbitSysRoleMapper.selectCountByExample(example) > 0;
    }

    /**
     * 获取角色的权限情况
     *
     * @param authorityList
     * @param roleId
     * @return
     */
    private List<RoleAuthorityVO> queryRoleAuthorityList(List<OrbitSysAuthority> authorityList, String roleId) {
        List<RoleAuthorityVO> roleAuthorityVOList = new ArrayList<>();
        Set<String> authorityIdSet = new HashSet<>();

        getRoleAuthorityIdSet(roleId, authorityIdSet, orbitSysRoleAuthorityMapper);

        for (OrbitSysAuthority item : authorityList) {
            RoleAuthorityVO roleAuthorityVO = new RoleAuthorityVO();
            roleAuthorityVO.setId(item.getId());
            roleAuthorityVO.setName(item.getAuthorityName());
            roleAuthorityVO.setZhName(item.getZhName());
            roleAuthorityVO.setParentNode(Integer.parseInt(item.getParentNode()));
            roleAuthorityVO.setHasPermission(authorityIdSet.contains(item.getId()));
            roleAuthorityVOList.add(roleAuthorityVO);
        }

        return roleAuthorityVOList;
    }

    /**
     * @param roleId
     * @param authorityIdSet
     * @param orbitSysRoleAuthorityMapper
     */
    static void getRoleAuthorityIdSet(String roleId, Set<String> authorityIdSet, OrbitSysRoleAuthorityMapper orbitSysRoleAuthorityMapper) {
        Example example = new Example(OrbitSysRoleAuthority.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<OrbitSysRoleAuthority> roleAuthorities = orbitSysRoleAuthorityMapper.selectByExample(example);
        for (OrbitSysRoleAuthority item : roleAuthorities) {
            authorityIdSet.add(item.getAuthorityId());
        }
    }
}
