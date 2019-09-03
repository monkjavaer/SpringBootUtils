package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.dal.model.UserLogEntity;
import com.car.trunk.dal.system.bo.UserLogBO;
import com.car.trunk.dal.system.dao.UserLogDao;
import com.car.trunk.dal.system.vo.UserLogVO;

import java.math.BigInteger;

/**
 * Description:用户日志接口
 * Created by monkjavaer on 2017/12/15 0015.
 */
public interface UserLogService extends IBaseService<UserLogEntity,UserLogDao> {

    /**
     * 增、删、改需要向日志表添加一条记录
     * @param userId 登录用户id
     * @param dataType 数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）
     * @param actionType 操作类型(0,添加；1，修改；2，删除)
     * @param description 操作描述
     */
    void addUserLog(BigInteger userId,Integer dataType,Integer actionType,String description) throws EntityOperateException, ValidateException;

    /**
     * 系统日志分页列表
     * @param userLogVO
     * @param userName 用户名
     * @param userId 用户id
     * @return
     */
    PageList<UserLogBO> queryList(UserLogVO userLogVO,String userName,BigInteger userId) throws ValidateException;
}
