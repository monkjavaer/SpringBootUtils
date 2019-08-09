package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitSysLog;
import com.car.orbit.orbitservice.qo.LogQO;
import com.car.orbit.orbitservice.vo.LogVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitSysLogMapper extends Mapper<OrbitSysLog> {

    /**
     * 日志分页列表
     * @param logQO
     * @return
     */
    List<LogVO> queryPageList(LogQO logQO);
}