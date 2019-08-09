package com.car.trunk.center.device.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.base.model.models.ResultTuple;
import com.car.base.service.services.BaseService;
import com.car.trunk.dal.device.bo.DeviceBO;
import com.car.trunk.dal.device.bo.DeviceQueryVO;
import com.car.trunk.dal.device.bo.DeviceSimpleBO;
import com.car.trunk.dal.device.dao.DeviceDao;
import com.car.trunk.dal.device.vo.DeviceRedisVO;
import com.car.trunk.dal.device.vo.DeviceSimpleVO;
import com.car.trunk.dal.device.vo.DeviceUserVO;
import com.car.trunk.dal.device.vo.DeviceVO;
import com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.dictionary.HasOnline;
import com.car.trunk.dal.model.DeviceEntity;
import com.car.trunk.dal.model.DeviceStatusLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:设备服务
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Service("DeviceService")
public class DeviceServiceImpl extends BaseService<DeviceEntity,DeviceDao> implements DeviceService {
    @Autowired
    public DeviceServiceImpl(@Qualifier("DeviceDao")DeviceDao dao) {
        super(dao);
    }

    @Autowired
    private DeviceStatusLogService deviceStatusLogService;



    /**
     * 获取两个BigInteger list的不同元素
     * @param list1
     * @param list2
     * @return
     */
    private static List<BigInteger> getDiffrent(List<BigInteger> list1, List<BigInteger> list2) {
        List<BigInteger> different = new ArrayList<BigInteger>();
        for(BigInteger str:list1)
        {
            if(!list2.contains(str))
            {
                different.add(str);
            }
        }
        return different;
    }

    @Override
    public PageList<DeviceBO> queryList(DeviceVO deviceVO,BigInteger userId,String userName) {
        ResultTuple<DeviceBO> result = entityDao.queryList(deviceVO,userId,userName);
        return PageListUtil.getPageList(result.count, deviceVO.getPageNo(), result.items, deviceVO.getPageSize());
    }

    @Override
    public List<DeviceSimpleBO> queryDeviceList(DeviceSimpleVO deviceSimpleVO) {
        return entityDao.queryDeviceList(deviceSimpleVO);
    }

    @Override
    public List<DeviceUserVO> queryDeviceListByUser(DeviceSimpleVO deviceSimpleVO, BigInteger userId){
        List<DeviceUserVO> returnList = new ArrayList<DeviceUserVO>();
        try {
            List<DeviceSimpleBO> allList = entityDao.queryDeviceList(deviceSimpleVO);
            List<DeviceSimpleBO> allListByUser = entityDao.queryDeviceListByUser(deviceSimpleVO, userId);
            for (int i = 0; i < allList.size(); i++) {
                boolean opFlag = false;
                DeviceSimpleBO deviceSimpleBOTemp = allList.get(i);
                for (DeviceSimpleBO deviceSimpleBOByUser:allListByUser) {
                    if(deviceSimpleBOTemp.getId().equals(deviceSimpleBOByUser.getId())){
                        opFlag = true;
                        break;
                    }
                }
                DeviceUserVO deviceUserVOTemp = new DeviceUserVO(deviceSimpleBOTemp.getId(),deviceSimpleBOTemp.getName(),deviceSimpleBOTemp.getOnline(),deviceSimpleBOTemp.getUrl(),deviceSimpleBOTemp.getLatitude(),deviceSimpleBOTemp.getLongitude(),opFlag);
                returnList.add(deviceUserVOTemp);
            }
        }catch (Exception e){

        }
        return  returnList;
    }


    @Override
    public void addDevice(DeviceEntity deviceEntity) throws EntityOperateException, ValidateException {
        //入设备表
        entityDao.save(deviceEntity);

        //入设备状态日志表
        DeviceStatusLogEntity deviceStatusLogEntity = new DeviceStatusLogEntity();
        deviceStatusLogEntity.setId(SnowflakeIdWorkerUtil.generateId());
        deviceStatusLogEntity.setCreateTime(deviceEntity.getCreateTime());
        deviceStatusLogEntity.setType(deviceEntity.getType());
        deviceStatusLogEntity.setOnline(deviceEntity.getOnline());
        deviceStatusLogEntity.setDeviceId(deviceEntity.getId());
        deviceStatusLogEntity.setLastupdateTime(deviceEntity.getUpdateTime());
        deviceStatusLogEntity.setTotalTime(0);
        deviceStatusLogService.save(deviceStatusLogEntity);
    }

    /**
     * 根据设备号查询设备
     * @param deviceId
     * @return
     */
    @Override
    public List<DeviceEntity> queryDeviceByDeviceId(String deviceId) {
        return entityDao.queryDeviceByDeviceId(deviceId);
    }

    /**
     * 同步设备时更新设备和设备状态表
     * @param lastUpdateTime 更新之前的更新时间
     * @param deviceEntity
     * @throws EntityOperateException
     * @throws ValidateException
     * @throws ParseException
     */
    @Override
    public void updateSynchronizeDevice(Date lastUpdateTime,DeviceEntity deviceEntity) throws EntityOperateException, ValidateException, ParseException {
        if (deviceEntity.getDeleted() == HasDelete.YES.value){
            deviceEntity.setDeleted(HasDelete.NO.value);

            //入设备状态日志表
            DeviceStatusLogEntity deviceStatusLogEntity = new DeviceStatusLogEntity();
            deviceStatusLogEntity.setId(SnowflakeIdWorkerUtil.generateId());
            deviceStatusLogEntity.setCreateTime(deviceEntity.getCreateTime());
            deviceStatusLogEntity.setType(deviceEntity.getType());
            deviceStatusLogEntity.setOnline(deviceEntity.getOnline());
            deviceStatusLogEntity.setDeviceId(deviceEntity.getId());
            deviceStatusLogEntity.setLastupdateTime(deviceEntity.getUpdateTime());
            deviceStatusLogEntity.setTotalTime(0);
            deviceStatusLogService.save(deviceStatusLogEntity);
        }else {
            //更新设备状态日志表
            DeviceStatusLogEntity deviceStatusLogEntity = deviceStatusLogService.querybyDeviceId(deviceEntity.getId());
            //更新持续在线时长
            if (lastUpdateTime != null&&deviceEntity.getOnline()==HasOnline.YES.value) {
                deviceStatusLogEntity.setTotalTime(deviceStatusLogEntity.getTotalTime() + hourBetween(lastUpdateTime, deviceEntity.getUpdateTime()));
            }
            deviceStatusLogEntity.setOnline(deviceEntity.getOnline());
            deviceStatusLogEntity.setLastupdateTime(deviceEntity.getUpdateTime());
            deviceStatusLogService.update(deviceStatusLogEntity);
        }

        entityDao.update(deviceEntity);
    }


    /**
     *  获取两个时间小时差
     */
    public  Integer hourBetween(Date startDate,Date endDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDate=sdf.parse(sdf.format(startDate));
        endDate=sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long betweenDays=(time2-time1)/(1000*60*60);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    @Override
    public List<DeviceQueryVO> queryInfoByDeviceId(BigInteger deviceId) {
        return entityDao.queryInfoByDeviceId(deviceId);
    }



    /**
     * 查询没有删除的电子警察部分信息
     * @return
     */
    @Override
    public List<DeviceRedisVO> queryDevicetoRedis() {
        return entityDao.queryDevicetoRedis();
    }
}
