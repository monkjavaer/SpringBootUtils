package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitSysMonitorCenter;
import com.car.orbit.orbitservice.qo.MonitorCenterQO;
import com.car.orbit.orbitservice.vo.MonitorCenterVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrbitSysMonitorCenterMapper extends Mapper<OrbitSysMonitorCenter> {

    /**
     *  机构分页查询
     * @param monitorCenterQO
     * @return
     */
    List<OrbitSysMonitorCenter> queryListByPage(MonitorCenterQO monitorCenterQO);

    /**
     * 查询所有没被删除的机构信息
     * @return
     */
    List<MonitorCenterVO> queryAllList();
}