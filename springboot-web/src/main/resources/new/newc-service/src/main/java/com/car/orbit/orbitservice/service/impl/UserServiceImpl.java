package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.bo.UserBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.mapper.OrbitSysAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.ColorMappingQO;
import com.car.orbit.orbitservice.qo.VehicleTypeMappingQO;
import com.car.orbit.orbitservice.service.IColorMappingService;
import com.car.orbit.orbitservice.service.IMonitorCenterService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.service.IVehicleTypeMappingService;
import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.UserInfoVO;
import com.car.orbit.orbitservice.websocket.WebSocketManager;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Title: UserServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/08 16:02
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    @Autowired
    private OrbitSysRoleMapper orbitSysRoleMapper;

    @Autowired
    private OrbitSysRoleAuthorityMapper orbitSysRoleAuthorityMapper;

    @Autowired
    private OrbitSysAuthorityMapper orbitSysAuthorityMapper;

    @Autowired
    private IMonitorCenterService iMonitorCenterService;

    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private IColorMappingService colorMappingService;

    @Autowired
    private IVehicleTypeMappingService vehicleTypeMappingService;

    @Value("${user.login-expire-time}")
    private int expireTime;

    /**
     * 用户登录
     *
     * @param orbitSysUser 需要登录的用户
     * @return 登录成功的token
     * @throws LoginException 用户名或者密码错误
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_LOGIN, actionType = OrbitServiceConstant.ACTION_LOGIN)
    @Override
    public String login(OrbitSysUser orbitSysUser) throws LoginException {
        // 检查用户名密码是否正确
        List<OrbitSysUser> userList = queryUserList(orbitSysUser.getUsername(), orbitSysUser.getPassword());
        if (CollectionUtils.isEmpty(userList)) {
            throw new LoginException("Username or password wrong");
        }

        // 发送重复登录的消息给用户，并删除之前的token
        sendReloginMsg(orbitSysUser.getUsername());

        // token保存到redis，初始超时时间10分钟
        OrbitSysUser user = userList.get(0);
        String token = TokenUtils.generateToken(user.getId(), user.getUsername(), "");
        redisClient.save(token, JsonUtils.toJSONString(user));
        redisClient.setExpireTime(token, expireTime);

        return token;
    }

    /**
     * 用户退出登录
     *
     * @param token 需要退出登录的用户的token
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_LOGIN, actionType = OrbitServiceConstant.ACTION_LOGOUT)
    @Override
    public void logout(String token) throws IllegalParamException {
        // 退出登录之前检测是否还有该token在redis里面
        if (!redisClient.existsKey(token)) {
            throw new IllegalParamException("用户信息不存在");
        }
        redisClient.delete(token);
    }

    /**
     * 通过用户名和密码查询用户
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public List<OrbitSysUser> queryUserList(String name, String password) {
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                // .andEqualTo("username", name).andEqualTo("password", password)
                .andCondition("BINARY USERNAME = ", name)
                .andCondition("BINARY PASSWORD = ", password)
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        return orbitSysUserMapper.selectByExample(example);
    }

    /**
     * 查询用户的基础信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO queryUserSimpleInfo(String userId) {
        return orbitSysUserMapper.queryUserInfo(userId);
    }

    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserBO queryUserInfo(String userId) {
        UserBO userBO = new UserBO();

        OrbitSysUser user = orbitSysUserMapper.selectByPrimaryKey(userId);
        userBO.set_id(user.getId());
        userBO.setUsername(user.getUsername());
        userBO.setName(user.getName());
        userBO.setAvatar(user.getPhotoUrl());
        userBO.setPhone(user.getPhone());
        userBO.setMonitorCenterId(user.getMonitorCenterId());

        if (user.getRoleId() != null) {
            OrbitSysRole orbitSysRole = orbitSysRoleMapper.selectByPrimaryKey(user.getRoleId());
            userBO.getRole().setRoleName(orbitSysRole.getRoleName());

            Example example = new Example(OrbitSysRoleAuthority.class);
            example.createCriteria()
                    .andEqualTo("roleId", user.getRoleId());
            List<OrbitSysRoleAuthority> authorityList = orbitSysRoleAuthorityMapper.selectByExample(example);

            Set<String> nameSet = new HashSet<>();
            for (OrbitSysRoleAuthority authority : authorityList) {
                nameSet.add(orbitSysAuthorityMapper.selectByPrimaryKey(authority.getAuthorityId()).getAuthorityName());
            }

            userBO.getRole().getRolePermission().getVehicleSearch().setFindPermission(nameSet.contains("vehicleSearch"));
            userBO.getRole().getRolePermission().getSearchByPicture().setFindPermission(nameSet.contains("searchByPicture"));
            userBO.getRole().getRolePermission().getCarInquiry().setFindPermission(nameSet.contains("carInquiry"));
            userBO.getRole().getRolePermission().getFirstCarInCity().setFindPermission(nameSet.contains("firstCarInCity"));
            userBO.getRole().getRolePermission().getPeerVehicle().setFindPermission(nameSet.contains("peerVehicle"));
            userBO.getRole().getRolePermission().getCrouchingAtNight().setFindPermission(nameSet.contains("crouchingAtNight"));
            userBO.getRole().getRolePermission().getFrequentAtNight().setFindPermission(nameSet.contains("frequentAtNight"));
            userBO.getRole().getRolePermission().getHiddenVehicle().setFindPermission(nameSet.contains("hiddenVehicle"));
            userBO.getRole().getRolePermission().getAnalysisOfTheFoothold().setFindPermission(nameSet.contains("analysisOfTheFoothold"));
            userBO.getRole().getRolePermission().getFrequentDriving().setFindPermission(nameSet.contains("frequentDriving"));
            userBO.getRole().getRolePermission().getTrajectoryAnalysis().setFindPermission(nameSet.contains("trajectoryAnalysis"));
            userBO.getRole().getRolePermission().getDeckAnalysis().setFindPermission(nameSet.contains("deckAnalysis"));
            userBO.getRole().getRolePermission().getOneCarOneFile().setFindPermission(nameSet.contains("oneCarOneFile"));
            userBO.getRole().getRolePermission().getVehicleControl().setFindPermission(nameSet.contains("vehicleControl"));
            userBO.getRole().getRolePermission().getBlacklistManagement().setFindPermission(nameSet.contains("blacklistManagement"));
            userBO.getRole().getRolePermission().getViolationManagement().setFindPermission(nameSet.contains("violationManagement"));
            userBO.getRole().getRolePermission().getSpecialVehicleManagement().setFindPermission(nameSet.contains("specialVehicleManagement"));
            userBO.getRole().getRolePermission().getRealTimeAlarm().setFindPermission(nameSet.contains("realTimeAlarm"));
            userBO.getRole().getRolePermission().getHistoryAlarm().setFindPermission(nameSet.contains("historyAlarm"));
            userBO.getRole().getRolePermission().getControlApproval().setFindPermission(nameSet.contains("controlApproval"));
            userBO.getRole().getRolePermission().getUserManagement().setFindPermission(nameSet.contains("userManagement"));
            userBO.getRole().getRolePermission().getEquipmentManagement().setFindPermission(nameSet.contains("equipmentManagement"));
            userBO.getRole().getRolePermission().getSystemLog().setFindPermission(nameSet.contains("systemLog"));
            userBO.getRole().getRolePermission().getSystemParameters().setFindPermission(nameSet.contains("systemParameters"));
            userBO.getRole().getRolePermission().getDivisionManagement().setFindPermission(false);
            userBO.getRole().getRolePermission().getInstitutionalManagement().setFindPermission(nameSet.contains("institutionalManagement"));
            userBO.getRole().getRolePermission().getRoleManagement().setFindPermission(nameSet.contains("roleManagement"));
            userBO.getRole().getRolePermission().getListTypeManagement().setFindPermission(nameSet.contains("listTypeManagement"));
            userBO.getRole().getRolePermission().getVideoManagement().setFindPermission(false);
        }

        userBO.setPlateColorArr(colorMappingService.queryColorList(new ColorMappingQO("001")));
        userBO.setVehicleTypeArr(vehicleTypeMappingService.queryTypeList(new VehicleTypeMappingQO("001")));

        return userBO;
    }

    /**
     * 编辑用户基础信息
     *
     * @param orbitSysUser
     */
    @Override
    public void editUserInfo(OrbitSysUser orbitSysUser) {
        if (orbitSysUser.getGender() != null) {
            orbitSysUser.setGender(orbitSysUser.getGender() + 1);
        }
        orbitSysUserMapper.updateByPrimaryKeySelective(orbitSysUser);
        OrbitSysUserRedis.saveUserInfo(orbitSysUser);
    }

    /**
     * 重置密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public boolean resetPassword(String userId, String oldPassword, String newPassword) {
        OrbitSysUser user = orbitSysUserMapper.selectByPrimaryKey(userId);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }

        OrbitSysUser orbitSysUser = new OrbitSysUser();
        orbitSysUser.setId(userId);
        orbitSysUser.setPassword(newPassword);
        orbitSysUserMapper.updateByPrimaryKeySelective(orbitSysUser);
        return true;
    }

    /**
     * @return
     * @description 获取当前用户所管辖的行政区域范围
     * @date: 17:11
     * @author: monkjavaer
     */
    @Override
    public OrbitResDeviceExtends getUserAuthority() {
        OrbitResDeviceExtends orbitResDeviceExtends = new OrbitResDeviceExtends();
        /** 加入用户权限限制*/
        String token = LocalHolder.getCurrentToken();
        String userId = TokenUtils.getUserID(token);
        UserInfoVO userInfoVO = queryUserSimpleInfo(userId);
        //超级管理员可以查看所有数据
        if (userInfoVO == null || userInfoVO.getUsername().equals(OrbitServiceConstant.SUPER_USERNAME)) {
            return orbitResDeviceExtends;
        }
        String monitorCenterId = userInfoVO.getMonitorCenterId();
        OrbitSysMonitorCenter orbitSysMonitorCenter = iMonitorCenterService.queryById(monitorCenterId);
        if (StringUtils.isNotBlank(orbitSysMonitorCenter.getAreaId())) {
            orbitResDeviceExtends.setAreaIdList(Arrays.asList(orbitSysMonitorCenter.getAreaId().split(",")));
        }
        if (StringUtils.isNotBlank(orbitSysMonitorCenter.getCityId())) {
            orbitResDeviceExtends.setCityIdList(Arrays.asList(orbitSysMonitorCenter.getCityId().split(",")));
        }
        if (StringUtils.isNotBlank(orbitSysMonitorCenter.getRoadId())) {
            orbitResDeviceExtends.setRoadIdList(Arrays.asList(orbitSysMonitorCenter.getRoadId().split(",")));
        }
        return orbitResDeviceExtends;
    }

    @Override
    public QueryBuilder getUserAuthorityQeuryBuilder() {
        //增加按照用户权限进行过滤
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        OrbitResDeviceExtends tmp = getUserAuthority();
        if (tmp.getAreaIdList() != null && tmp.getAreaIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.AREA_ID, tmp.getAreaIdList()));
        }
        if (tmp.getCityIdList() != null && tmp.getCityIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.CITY_ID, tmp.getCityIdList()));
        }
        if (tmp.getRoadIdList() != null && tmp.getRoadIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.ROAD_CROSS_POINT_ID, tmp.getRoadIdList()));
        }
        return queryBuilder;
    }

    /**
     * 判断姓名是否重复
     *
     * @param orbitSysUser 需要判断的用户
     * @return true-重复，false-不重复
     */
    @Override
    public boolean existDuplicate(OrbitSysUser orbitSysUser) {
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andEqualTo("monitorCenterId", orbitSysUser.getMonitorCenterId())
                .andNotEqualTo("id", orbitSysUser.getId())
                .andCondition("BINARY name = ", orbitSysUser.getName());
        return orbitSysUserMapper.selectCountByExample(example) > 0;
    }

    /**
     * 发送重复登陆的socket消息，并且删除之前的token
     *
     * @param username 重复登录的用户名
     */
    private void sendReloginMsg(String username) {
        // Map<String, String> map = redisClient.get(BaseBusinessRedis.LOGIN_USER);
        // map.forEach((key, value) -> {
        //     OrbitSysUser u = JsonUtils.toBean(value, OrbitSysUser.class);
        //     if (u.getUsername().equals(username)) {
        //
        //         //重复登录发websocket消息
        //         UserMessageBody userMessageBody = new UserMessageBody();
        //         userMessageBody.setUserName(username);
        //         WebSocketMsg webSocketMsg = new WebSocketMsg();
        //         webSocketMsg.setType(MessageType.login);
        //         webSocketMsg.setBody(userMessageBody);
        //         webscoketManager.sendLogoutMessage(webSocketMsg);
        //
        //         redisClient.delete(BaseBusinessRedis.LOGIN_USER, key);
        //     }
        // });

        Set<String> allKeys = redisClient.getAllKeys();
        allKeys.forEach(key -> {
            if (TokenUtils.isLegalToken(key)) {
                OrbitSysUser u = redisClient.getValue(key, OrbitSysUser.class);
                if (u.getUsername().equals(username)) {
                    //重复登录发websocket消息
                    UserMessageBody userMessageBody = new UserMessageBody();
                    userMessageBody.setUserName(username);
                    WebSocketMsg webSocketMsg = new WebSocketMsg();
                    webSocketMsg.setType(MessageType.login);
                    webSocketMsg.setBody(userMessageBody);
                    webSocketManager.sendLogoutMessage(webSocketMsg);
                    // 删除旧的用户token
                    redisClient.delete(key);
                }
            }
        });
    }
}
