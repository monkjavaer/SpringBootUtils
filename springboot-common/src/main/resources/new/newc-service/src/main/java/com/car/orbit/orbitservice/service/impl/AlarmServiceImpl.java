package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.AlarmDetailBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.AlarmStatusEnum;
import com.car.orbit.orbitservice.mapper.OrbitControlAlarmMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistTypeMapper;
import com.car.orbit.orbitservice.qo.AlarmQO;
import com.car.orbit.orbitservice.service.IAlarmService;
import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.AlarmOneFileVO;
import com.car.orbit.orbitservice.vo.AlarmVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.TokenUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: AlarmServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 预警service
 * @Author: monkjavaer
 * @Data: 2019/4/1 11:54
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class AlarmServiceImpl implements IAlarmService {

    /**
     * es客服端
     */
    @Autowired
    private TransportClient transportClient;

    /**预警mapper*/
    @Autowired
    private OrbitControlAlarmMapper alarmMapper;

    /**
     * 黑名单Mapper
     */
    @Autowired
    private OrbitControlBlacklistMapper blacklistMapper;

    /**
     * 黑名单类型Mapper
     */
    @Autowired
    private OrbitControlBlacklistTypeMapper blacklistTypeMapper;

    /**
     * 保存报警信息
     * @param alarm 报警实体类
     */
    @Override
    public void insertAlarm(OrbitControlAlarm alarm) {
        alarmMapper.insertAlarm(alarm);
    }

    /**
     * 查询实时预警(返回最近十条警情)
     * @return List<AlarmVO>
     */
    @Override
    public List<AlarmVO> queryRealTimeAlarm(String startTime,String endTime) {
        String queryUserId = null;

        String userId = TokenUtils.getUserID(LocalHolder.getCurrentToken());
        OrbitSysUser user = OrbitSysUserRedis.getUserById(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            queryUserId = userId;
        }

        List<AlarmVO> alarms = alarmMapper.queryRealTimeAlarm(queryUserId, startTime, endTime);
        for (AlarmVO alarm : alarms) {
            //根据过车记录ID从es获取过车抓拍记录
            OrbitPassVehicleRecord pass = getPassVehicleRecord(DateUtils.format(alarm.getCaptureTime()), alarm.getPassVehicleId());
            //从Redis获取车辆品牌相关信息
            if (pass == null){
                continue;
            }
            alarm.setFullBrand(BrandRedis.getFullBrandNameByCode(pass.getVehicleBrandChild()));
            alarm.setCityName(pass.getCityName());
            alarm.setAreaName(pass.getAreaName());
            alarm.setRoadName(pass.getRoadName());
            alarm.setVehicleColor(pass.getVehicleColor());
            alarm.setPlateColor(pass.getPlateColor());
            alarm.setVehicleType(pass.getVehicleType());
            if (alarm.getTaskName() == null) {
                alarm.setLevel(1);
            }
        }
        return alarms;
    }

    /**
     * 历史警情分页列表
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmVO>
     */
    @Override
    public PageUtil<AlarmVO> queryHistoricalAlarm(AlarmQO alarmQO) {
        String userId = TokenUtils.getUserID(LocalHolder.getCurrentToken());
        OrbitSysUser user = OrbitSysUserRedis.getUserById(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            alarmQO.setUserId(userId);
        }

        PageHelper.startPage(alarmQO.getPageNo(), alarmQO.getPageSize());
        if (alarmQO.getDeviceIdList()!=null){
            String temp = alarmQO.getDeviceIdList().toString();
            if (StringUtils.isNotBlank(temp)){
                alarmQO.setDeviceIdListStr(temp.substring(1,temp.length()-1));
            }
        }
        List<AlarmVO> list= alarmMapper.queryHistoricalAlarm(alarmQO);
        list.stream().forEach(alarmVO -> {
            if (alarmVO.getTaskName() == null) {
                alarmVO.setLevel(1);
            }
        });

        return new PageUtil<>(list);
    }

    /**
     * 一车一档中查看指定车牌对应的历史警情分页列表
     *
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmVO>
     */
    @Override
    public PageUtil<AlarmVO> queryHistoricalAlarmOneFile(AlarmQO alarmQO) {
        String userId = TokenUtils.getUserID(LocalHolder.getCurrentToken());
        OrbitSysUser user = OrbitSysUserRedis.getUserById(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            alarmQO.setUserId(userId);
        }

        PageHelper.startPage(alarmQO.getPageNo(), alarmQO.getPageSize());
        if (alarmQO.getDeviceIdList()!=null){
            String temp = alarmQO.getDeviceIdList().toString();
            if (StringUtils.isNotBlank(temp)){
                alarmQO.setDeviceIdListStr(temp.substring(1,temp.length()-1));
            }
        }
        List<AlarmVO> list= alarmMapper.queryHistoricalAlarmExactPlateNum(alarmQO);
        list.stream().forEach(alarmVO -> {
            if (alarmVO.getTaskName() == null) {
                alarmVO.setLevel(1);
            }
        });

        return new PageUtil<>(list);
    }


    /**
     * 开启一车一档，预警
     * @param alarmQO AlarmQO
     * @return PageUtil<AlarmOneFileVO>
     */
    @Override
    public PageUtil<AlarmOneFileVO> queryAlarmOneFile(AlarmQO alarmQO) {
        String userId = TokenUtils.getUserID(LocalHolder.getCurrentToken());
        OrbitSysUser user = OrbitSysUserRedis.getUserById(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            alarmQO.setUserId(userId);
        }

        PageHelper.startPage(alarmQO.getPageNo(), alarmQO.getPageSize());
        if (alarmQO.getDeviceIdList()!=null){
            String temp = alarmQO.getDeviceIdList().toString();
            if (StringUtils.isNotBlank(temp)){
                alarmQO.setDeviceIdListStr(temp.substring(1,temp.length()-1));
            }

        }
        List<AlarmOneFileVO> list = alarmMapper.queryAlarmOneFile(alarmQO);
        return new PageUtil<>(list);
    }

    /**
     * 根据主键查询预警对比信息
     * @param alarmId
     * @return
     */
    @Override
    public AlarmDetailBO alarmDetail(String alarmId) {
        AlarmDetailBO alarmDetailBO = new AlarmDetailBO();
        OrbitControlAlarm orbitControlAlarm = alarmMapper.selectByPrimaryKey(alarmId);
        //1、根据过车记录ID从es获取过车抓拍记录

        OrbitPassVehicleRecord pass = getPassVehicleRecord(
                DateUtils.format(orbitControlAlarm.getCaptureTime()),
                orbitControlAlarm.getPassVehicleId());
        // 从Redis获取车辆品牌相关信息
        if (pass != null) {
            pass.setFullBrand(BrandRedis.getFullBrandNameByCode(pass.getVehicleBrandChild()));
            OrbitResDevice device = DevicePointRedis.getDevicePointByCode(pass.getDeviceId());
            if (device != null) {
                pass.setLongitude(device.getLongitude());
                pass.setLatitude(device.getLatitude());
            }
            alarmDetailBO.setOrbitPassVehicleRecord(pass);
        }

        //黑名单id即vid
        OrbitControlBlacklist blacklist = blacklistMapper.selectByPrimaryKey(orbitControlAlarm.getBlacklistId());
        if (blacklist!=null) {
            // 从Redis获取车辆品牌相关信息
            blacklist.setFullBrand(BrandRedis.getFullBrandNameByCode(blacklist.getVehicleBrandChild()));
            OrbitControlBlacklistType type = blacklistTypeMapper.selectByPrimaryKey(blacklist.getType());
            if (type != null) {
                blacklist.setTypeName(type.getName());
            }
            alarmDetailBO.setOrbitControlBlacklist(blacklist);
        }
        return alarmDetailBO;
    }

    /**
     * 查询未处理的警情条数
     * @return
     */
    @Override
    public Integer queryUntreatedAlarmNumber() {
        String queryUserId = null;

        String userId = TokenUtils.getUserID(LocalHolder.getCurrentToken());
        OrbitSysUser user = OrbitSysUserRedis.getUserById(userId);
        if (!OrbitServiceConstant.SUPER_USERNAME.equals(user.getUsername())) {
            queryUserId = userId;
        }
        return alarmMapper.queryUntreatedAlarmNumber(queryUserId);
    }

    /**
     * 更新警情
     * @param orbitControlAlarm
     */
    @Override
    public boolean updateAlarm(OrbitControlAlarm orbitControlAlarm) {
        OrbitControlAlarm alarm = alarmMapper.selectByPrimaryKey(orbitControlAlarm.getId());
        if (alarm.getStatus() != AlarmStatusEnum.NO_ACK.getValue()) {
            return false;
        }
        alarmMapper.updateByPrimaryKeySelective(orbitControlAlarm);
        return true;
    }


    /**
     * 根据过车主键查询es记录
     * @param captureTime 抓拍时间 取前后5分钟 方便构造索引 提升查询性能
     * @param passVehicleId 过车ID
     * @return
     */
    @Override
    public  OrbitPassVehicleRecord getPassVehicleRecord(String captureTime, String passVehicleId){
        //取前后5分钟 方便构造索引 提升查询性能
        String startTime = DateUtils.getBeforeOrAfterMinute(captureTime, -5);
        String endTime = DateUtils.getBeforeOrAfterMinute(captureTime, 5);
        // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(startTime, endTime);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.ID, passVehicleId));
        //开始查询
        SearchHits hits = transportClient
                .prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .execute()
                .actionGet()
                .getHits();

        //被查询车辆过车记录集合
        List<OrbitPassVehicleRecord> vehicleTrajectoryList = new ArrayList<>();
        //根据查询hits获得
        for (SearchHit searchHit : hits.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            OrbitPassVehicleRecord record = JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class);
            record.setPhotoFastdfsUrl(record.getPhotoOriFastdfsUrl());
            vehicleTrajectoryList.add(record);
        }
        //查询出来只会有1条记录
        if (vehicleTrajectoryList.size() == 0){
            return null;
        }
        return vehicleTrajectoryList.get(0);
    }

}
