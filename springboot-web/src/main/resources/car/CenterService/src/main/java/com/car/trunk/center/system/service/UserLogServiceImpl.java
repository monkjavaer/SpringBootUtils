package com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.base.model.models.ResultTuple;
import com.car.base.service.services.BaseService;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.model.UserLogEntity;
import com.car.trunk.dal.system.bo.UserLogBO;
import com.car.trunk.dal.system.dao.UserLogDao;
import com.car.trunk.dal.system.vo.UserLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

/**
 * Description:用户日志服务
 * Created by monkjavaer on 2017/12/15 0015.
 */
@Service("UserLogService")
public class UserLogServiceImpl extends BaseService<UserLogEntity,UserLogDao> implements UserLogService{


    @Autowired
    public UserLogServiceImpl(@Qualifier("UserLogDao")UserLogDao dao) {
        super(dao);
    }

    @Autowired
    private UserService userService;

    @Override
    public void addUserLog(BigInteger userId, Integer dataType, Integer actionType, String description) throws EntityOperateException, ValidateException {
        UserLogEntity userLogEntity = new UserLogEntity();
        userLogEntity.setId(SnowflakeIdWorkerUtil.generateId());
        userLogEntity.setCreateTime(new Date());
        userLogEntity.setUserId(userId);
        userLogEntity.setActionType(actionType);
        userLogEntity.setDataType(dataType);
        userLogEntity.setDescription(description);
        entityDao.save(userLogEntity);
    }

    @Override
    public PageList<UserLogBO> queryList(UserLogVO userLogVO,String userName,BigInteger userId) throws ValidateException {
        UserEntity user = userService.get(userId);
        ResultTuple<UserLogBO> result = entityDao.queryList(userLogVO,userName,user.getMonitorCenterId());
        return PageListUtil.getPageList(result.count, userLogVO.getPageNo(), result.items, userLogVO.getPageSize());
    }
}
