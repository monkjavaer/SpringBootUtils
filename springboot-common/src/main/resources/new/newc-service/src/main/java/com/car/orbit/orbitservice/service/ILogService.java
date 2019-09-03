package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitSysLog;
import com.car.orbit.orbitservice.qo.LogQO;
import com.car.orbit.orbitservice.vo.LogVO;
import com.car.orbit.orbitutil.page.PageUtil;

/**
 * @Title: ILogService
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 日志接口
 * @Author: monkjavaer
 * @Data: 2019/3/12 19:33
 * @Version: V1.0
 */
public interface ILogService {

    /**
     * 添加日志
     * @param orbitSysLog
     */
    void addLog(OrbitSysLog orbitSysLog);

    /**
     * 日志分页列表
     * @param logQO
     * @return
     */
    PageUtil<LogVO> queryPageList(LogQO logQO);
}
