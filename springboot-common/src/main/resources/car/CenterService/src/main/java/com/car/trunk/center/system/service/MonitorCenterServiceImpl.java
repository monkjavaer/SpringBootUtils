package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.model.models.ResultTuple;
import com.car.base.service.services.BaseService;
import com.car.trunk.dal.dictionary.HasDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Description:监控中心服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("MonitorCenterService")
public class MonitorCenterServiceImpl extends BaseService<MonitorCenterEntity,MonitorCenterDao> implements MonitorCenterService {
    @Autowired
    public MonitorCenterServiceImpl(@Qualifier("MonitorCenterDao")MonitorCenterDao dao) {
        super(dao);
    }


    @Override
    public void addMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException {
        entityDao.save(monitorCenterEntity);
    }

    @Override
    public void updateMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException {
        // 修改时针对删除字段前端未传值，修改的时候肯定不会删除
        monitorCenterEntity.setDeleted(HasDelete.NO.value);
        entityDao.update(monitorCenterEntity);
    }


    @Override
    public PageList<MonitorCenterEntity> queryList(MonitorCenterVO monitorCenterVO) {
        ResultTuple<MonitorCenterEntity> result = entityDao.queryList(monitorCenterVO);
        return PageListUtil.getPageList(result.count, monitorCenterVO.getPageNo(), result.items, monitorCenterVO.getPageSize());
    }

    @Override
    public List<MonitorCenterBO> queryAllList() {
        return entityDao.queryAllList();
    }

    @Override
    public boolean checkName(String name) {
        boolean result = true;
        List<MonitorCenterEntity> list = entityDao.getByName(name);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkCode(String adminRegionCode) {
        boolean result = true;
        List<MonitorCenterEntity> list = entityDao.checkCode(adminRegionCode);
        if(list.size() > 0){
            result = false;
        }
        return result;
    }

    @Override
    public void deleteMonitor(MonitorCenterEntity monitorCenterEntity) throws EntityOperateException, ValidateException {
        entityDao.update(monitorCenterEntity);
    }

    @Override
    public MonitorCenterEntity getByDeviceID(BigInteger deviceID){
        return entityDao.getByDeviceID(deviceID);
    }
}
