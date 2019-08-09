package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitSysLog;
import com.car.orbit.orbitservice.mapper.OrbitSysLogMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.LogQO;
import com.car.orbit.orbitservice.service.ILogService;
import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitservice.vo.LogVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.TokenUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Title: LogServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 日志接口实现
 * @Author: monkjavaer
 * @Data: 2019/3/12 19:40
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl implements ILogService {

    @Autowired
    private OrbitSysLogMapper logMapper;

    @Autowired
    private OrbitSysUserMapper userMapper;

    /**
     * 添加日志
     *
     * @param orbitSysLog
     */
    @Override
    public void addLog(OrbitSysLog orbitSysLog) {
        orbitSysLog.setId(UUIDUtils.generate());
        orbitSysLog.setCreateTime(new Date());
        logMapper.insert(orbitSysLog);
    }

    /**
     * 日志分页列表
     *
     * @param logQO
     * @return
     */
    @Override
    public PageUtil<LogVO> queryPageList(LogQO logQO) {
        String currentToken = LocalHolder.getCurrentToken();
        // 超级管理员，可以查看所有人的日志，不需要添加限定的user id，非超级管理员只能查看同机构下其他用户的日志
        if(!TokenUtils.getUserName(currentToken).equals(OrbitServiceConstant.SUPER_USERNAME)){
            List<String> userIdList = userMapper.queryUserIdInSameMonitor(TokenUtils.getUserID(currentToken));
            logQO.setUserIdList(userIdList);
        }
        PageHelper.startPage(logQO.getPageNo(), logQO.getPageSize());
        List<LogVO> list = logMapper.queryPageList(logQO);
        return new PageUtil<>(list);
    }
}
