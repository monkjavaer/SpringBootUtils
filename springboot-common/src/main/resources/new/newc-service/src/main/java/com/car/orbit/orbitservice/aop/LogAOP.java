package com.car.orbit.orbitservice.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.StatusEnum;
import com.car.orbit.orbitservice.mapper.*;
import com.car.orbit.orbitservice.service.ILogService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.SpringUtil;
import com.car.orbit.orbitservice.vo.ControlTaskVO;
import com.car.orbit.orbitservice.vo.RoleOperatorVO;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Title: LogAOP
 * @Package: com.car.orbit.orbitservice.aop
 * @Description: 系统日志：切面处理类
 * @Author: monkjavaer
 * @Data: 2019/3/12 19:31
 * @Version: V1.0
 */
@Aspect
@Component
@Order(4)
public class LogAOP {

    private static Logger logger = LoggerFactory.getLogger(LogAOP.class);

    /**
     * 系统日志接口
     */
    @Autowired
    private ILogService logService;

    @Autowired
    private OrbitControlTaskMapper taskMapper;

    @Autowired
    private OrbitSysRoleMapper roleMapper;

    @Autowired
    private OrbitControlWhitelistMapper whitelistMapper;

    @Autowired
    private OrbitControlBlacklistMapper blacklistMapper;

    @Autowired
    private OrbitSysVariableMapper sysVariableMapper;

    @Autowired
    private IUserService userService;

    /**
     * 切点
     */
    @Pointcut("@annotation( com.car.orbit.orbitservice.annotation.LogAnnotation)")
    public void logPointCut() {
    }

    /**
     * 配置通知
     *
     * @param joinPoint
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {

        //从切面织入点处通过反射机制获取织入点处方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在方法
        Method method = signature.getMethod();

        //获取操作
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if (Objects.isNull(logAnnotation)) {
            return;
        }

        try {
            //请求参数args
            Object[] args = joinPoint.getArgs();
            //params格式为 [{"xx":"xx"}]
            String params = JSON.toJSONString(args);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = JSON.parseObject(params.substring(1, params.length() - 1));
            } catch (Exception ignore) {
                // 退出登录时，参数只有一个token，不能转化成jsonObject，忽略该异常
            }


            String dataType = logAnnotation.dataType();
            String actionType = logAnnotation.actionType();
            Class tClass = logAnnotation.mapperType();

            OrbitSysLog sysLog;
            switch (dataType) {
                case OrbitServiceConstant.DATA_LOGIN:
                    sysLog = loginLog(jsonObject, actionType);
                    break;
                case OrbitServiceConstant.DATA_CONTROL:
                    sysLog = controlTaskLog(jsonObject, actionType);
                    break;
                case OrbitServiceConstant.DATA_ROLE:
                    sysLog = roleLog(jsonObject, actionType);
                    break;
                case OrbitServiceConstant.DATA_WHITELIST:
                    sysLog = whiteListLog(jsonObject, actionType);
                    break;
                case OrbitServiceConstant.DATA_BLACKLIST:
                    sysLog = blackListLog(jsonObject, actionType);
                    break;
                case OrbitServiceConstant.DATA_SYSVARIABLE:
                    sysLog = sysParamLog(jsonObject);
                    break;
                case OrbitServiceConstant.DATA_ALARM_VOICE:
                    sysLog = alarmVoiceLog(jsonObject);
                    break;
                default:
                    sysLog = otherBusinessLog(jsonObject, tClass, actionType);
            }
            // 修改报警铃声会自己设置这个值
            if (sysLog.getDataType() == null || sysLog.getDataType() == 0) {
                sysLog.setDataType(Integer.valueOf(dataType));
            }
            // 黑名单修改布控状态时会自己设置这个值
            if (sysLog.getActionType() == null || sysLog.getActionType() == 0) {
                sysLog.setActionType(Integer.valueOf(actionType));
            }
            logService.addLog(sysLog);
        } catch (Exception e) {
            // 发生任何异常均不做处理，只是记录一下【日志没有被记录】这个事情
            logger.error("Record log error, method: {}", method.getName(), e);
        }
    }

    /**
     * 组装登录日志，登录没有token，从参数中获取id
     *
     * @param paramObject 登录参数，格式为 [{"xx":"xx"}]，类型：{@link OrbitSysUser}
     * @param actionType 操作类型
     * @return 登录日志
     */
    private OrbitSysLog loginLog(JSONObject paramObject, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        //获取用户id
        if (actionType.equals(OrbitServiceConstant.ACTION_LOGIN)) {
            String username = paramObject.getString("username");
            String password = paramObject.getString("password");
            OrbitSysUser orbitSysUsers = userService.queryUserList(username, password).get(0);
            sysLog.setUserId(orbitSysUsers.getId());
        } else {
            sysLog.setUserId(getUserId());
        }
        return sysLog;
    }

    /**
     * 组装布控任务日志
     *
     * @param paramObject 布控任务参数，格式为 [{"xx":"xx"}]，对象类型：{@link ControlTaskBO}
     * @param actionType 操作类型
     * @return 布控任务日志
     */
    private OrbitSysLog controlTaskLog(JSONObject paramObject, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        // 操作类型是删除时，通过id获取对象，再获取名字
        if (actionType.equals(OrbitServiceConstant.ACTION_DELETE) || actionType.equals(OrbitServiceConstant.ACTION_OVER)) {
            String taskId = paramObject.getString("id");
            OrbitControlTask orbitControlTask = taskMapper.selectByPrimaryKey(taskId);
            sysLog.setDescription(orbitControlTask.getTaskName());
        } else {
            sysLog.setDescription(paramObject.getObject("task", ControlTaskVO.class).getTaskName());
        }
        return sysLog;
    }

    /**
     * 组装角色操作日志
     *
     * @param paramObject 角色参数，格式为 [{"xx":"xx"}]，对象类型：{@link RoleOperatorVO}
     * @param actionType 操作类型
     * @return 角色操作日志
     */
    private OrbitSysLog roleLog(JSONObject paramObject, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        if (actionType.equals(OrbitServiceConstant.ACTION_DELETE)) {
            String roleId = paramObject.getString("roleId");
            OrbitSysRole orbitSysRole = roleMapper.selectByPrimaryKey(roleId);
            sysLog.setDescription(orbitSysRole.getRoleName());
        } else {
            sysLog.setDescription(paramObject.getString("roleName"));
        }
        return sysLog;
    }

    /**
     * 组装白名单操作日志，内容为车牌号
     *
     * @param paramObject 白名单参数，格式为 [{"xx":"xx"}]，对象类型：{@link OrbitControlWhitelist}
     * @param actionType 操作类型
     * @return 白名单操作日志
     */
    private OrbitSysLog whiteListLog(JSONObject paramObject, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        if (actionType.equals(OrbitServiceConstant.ACTION_DELETE)) {
            String vid = paramObject.getString("vid");
            OrbitControlWhitelist orbitControlWhitelist = whitelistMapper.selectByPrimaryKey(vid);
            sysLog.setDescription(orbitControlWhitelist.getPlateNumber());
        } else {
            sysLog.setDescription(paramObject.getString("plateNumber"));
        }
        return sysLog;
    }

    /**
     * 组装黑名单操作日志，内容为车牌号
     *
     * @param paramObject
     * @param actionType
     * @return
     */
    private OrbitSysLog blackListLog(JSONObject paramObject, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        if (actionType.equals(OrbitServiceConstant.ACTION_DELETE)) {
            String vid = paramObject.getString("vid");
            OrbitControlBlacklist orbitControlBlacklist = blacklistMapper.selectByPrimaryKey(vid);
            sysLog.setDescription(orbitControlBlacklist.getPlateNumber());
        } else if (actionType.equals(OrbitServiceConstant.ACTION_UPDATE)
                && StringUtils.isBlank(paramObject.getString("plateNumber"))) {
            if (paramObject.getInteger("status") == StatusEnum.CLOSED.getValue()) {
                sysLog.setActionType(Integer.valueOf(OrbitServiceConstant.ACTION_CLOSE_CONTROL));
            } else if (paramObject.getInteger("status") == StatusEnum.OPEN.getValue()) {
                sysLog.setActionType(Integer.valueOf(OrbitServiceConstant.ACTION_OPEN_CONTROL));
            }
            String vid = paramObject.getString("vid");
            OrbitControlBlacklist orbitControlBlacklist = blacklistMapper.selectByPrimaryKey(vid);
            sysLog.setDescription(orbitControlBlacklist.getPlateNumber());
        } else {
            sysLog.setDescription(paramObject.getString("plateNumber"));
        }
        return sysLog;
    }

    /**
     * 组装系统参数修改日志
     *
     * @param paramObject 系统参数，格式为 [{"xx":"xx"}]
     * @return 系统参数修改日志
     */
    private OrbitSysLog sysParamLog(JSONObject paramObject) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        String id = paramObject.getString("id");
        OrbitSysVariable orbitSysVariable = sysVariableMapper.selectByPrimaryKey(id);
        sysLog.setDescription(orbitSysVariable.getName());
        return sysLog;
    }

    /**
     * 组装报警铃声修改日志
     *
     * @param paramObject 系统参数，格式为 [{"xx":"xx"}]
     * @return 系统参数修改日志
     */
    private OrbitSysLog alarmVoiceLog(JSONObject paramObject) {
        // 该字符串用于前台做国际化翻译，与前台商定好，不要改动
        final String desc = "alarm voice";
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        sysLog.setDataType(Integer.valueOf(OrbitServiceConstant.DATA_SYSVARIABLE));
        sysLog.setDescription(desc);
        return sysLog;
    }

    /**
     * 除需要特殊操作获取操作主体之外的其他业务操作，这些业务操作新增和修改时均可通过传递的参数直接获取name；删除操作时使用id获取对象，再获取name
     *
     * @param paramObject 操作对象参数
     * @return 操作日志
     */
    private OrbitSysLog otherBusinessLog(JSONObject paramObject, Class clazz, String actionType) {
        OrbitSysLog sysLog = new OrbitSysLog();
        String userId = getUserId();
        sysLog.setUserId(userId);
        if (actionType.equals(OrbitServiceConstant.ACTION_DELETE)) {
            String id = paramObject.getString("id");
            Mapper mapper = (Mapper) SpringUtil.getBean(clazz);
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(mapper.selectByPrimaryKey(id)));
            sysLog.setDescription(object.getString("name"));
        } else {
            sysLog.setDescription(paramObject.getString("name"));
        }
        return sysLog;
    }

    /**
     * 从token中获取操作用户的id
     *
     * @return 当前用户id
     */
    private String getUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        String userId = null;
        //获取用户id
        if (attributes != null) {
            request = attributes.getRequest();
            String token = request.getHeader("token");
            userId = TokenUtils.getUserID(token);
        }
        return userId;
    }
}
