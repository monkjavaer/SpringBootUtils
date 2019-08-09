package com.car.trunk.dal.system.dao;

import com.car.base.dao.interfaces.IBaseDao;
import com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.model.UserLogEntity;
import com.car.trunk.dal.system.bo.UserLogBO;
import com.car.trunk.dal.system.vo.UserLogVO;

import java.math.BigInteger;

/**
 * Created by monkjavaer on 2017/12/15 0015.
 */
public interface UserLogDao extends IBaseDao<UserLogEntity> {
    /**
     * 系统日志分页列表
     * @param userLogVO
     * @param userName 用户名
     * @param monitorCenterId 机构id
     * @return
     */
    ResultTuple<UserLogBO> queryList(UserLogVO userLogVO,String userName,BigInteger monitorCenterId);
}
