package com.car.orbit.orbitservice;

import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitColorMapping;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import com.car.orbit.orbitservice.entity.OrbitVehicleTypeMapping;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.kafka.KafkaAlarmTaskService;
import com.car.orbit.orbitservice.mapper.*;
import com.car.orbit.orbitservice.service.IControlTaskService;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IVehicleBrandService;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitutil.tools.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Title: AppStartUp
 * @Package: com.car.orbit.orbitservice
 * @Description: 业务数据初始化
 * @Author: monkjavaer
 * @Data: 2019/3/22 11:00
 * @Version: V1.0
 */
@Component
@Order(1)
public class BusinessInit implements ApplicationRunner {

    /**
     * 设备服务
     */
    @Autowired
    private IDeviceService deviceService;

    /**
     * 品牌、子品牌服务
     */
    @Autowired
    private IVehicleBrandService vehicleBrandService;

    @Autowired
    private OrbitResCityMapper cityMapper;

    @Autowired
    private OrbitResAreaMapper areaMapper;

    @Autowired
    private OrbitResRoadcrossPointMapper roadcrossPointMapper;

    @Autowired
    private OrbitResDeviceMapper deviceMapper;

    /**
     * Color
     */
    @Autowired
    private OrbitVehicleColorMapper colorMapper;

    /**
     * 黑名单
     */
    @Autowired
    private OrbitControlBlacklistMapper orbitControlBlacklistMapper;

    /**
     * 白名单
     */
    @Autowired
    private OrbitControlWhitelistMapper orbitControlWhitelistMapper;

    /**
     * 用户
     */
    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    /**
     * 导出
     */
    @Autowired
    private OrbitExportMapper exportMapper;

    @Autowired
    private OrbitVehicleTypeMapper vehicleTypeMapper;

    @Autowired
    private IControlTaskService controlTaskService;

    @Autowired
    private OrbitColorMappingMapper colorMappingMapper;

    @Autowired
    private OrbitVehicleTypeMappingMapper typeMappingMapper;

    /**
     * redis
     */
    @Autowired
    RedisClient redisClient;

    /**
     * kafka布控预警任务
     */
    @Autowired
    private KafkaAlarmTaskService kafkaAlarmTaskService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        //1. 初始化视频SDK
        deviceService.initSdk();

        //2.启动时新启动一个线程，接收kafka报警消息
        kafkaAlarmTaskService.start();

        //3. 加载字典数据
        initDicInfo();
    }

    /**
     * 初始化DIC字典信息
     */
    private void initDicInfo() {
        // 初始化品牌子品牌
        BrandRedis.saveBrandList(vehicleBrandService.getBrand());
        BrandRedis.saveChildBrandList(vehicleBrandService.getChildBrand());

        // 初始化city信息
        CityRedis.saveVoList(cityMapper.selectAll());

        // 初始化area信息
        AreaRedis.saveVoList(areaMapper.selectAll());

        // 初始化路口/卡口信息
        RoadcrossPointRedis.saveVoList(roadcrossPointMapper.selectAll());

        // 初始化设备点位信息
        DevicePointRedis.saveVoList(deviceMapper.selectAll());

        // 初始化导出表头信息
        ExportHeaderRedis.saveHeaderList(exportMapper.selectAll());

        // 车辆类型
        VehicleTypeRedis.saveVoList(vehicleTypeMapper.selectAll());

        // Color
        ColorRedis.saveVoList(colorMapper.selectAll());

        // 黑名单
        loadBlacklist();

        // 白名单
        loadWhitelist();

        // 正在布控中的列表缓存
        List<ControlTaskBO> controltaskBOs = controlTaskService.getInitAllOnControlTask();
        if (controltaskBOs != null && controltaskBOs.size() > 0) {
            for (ControlTaskBO bo : controltaskBOs) {
                OnControlRedis.save(bo);
            }
        }

        // 从Redis获取vehicleTypeList缓存到本地
        OrbitServiceConstant.vehicleTypeList = VehicleTypeRedis.getAllVehicleType();

        // 保存系统所有用户信息到redis
        OrbitSysUserRedis.saveUserInfos(orbitSysUserMapper.selectAll());

        loadColorMapping();
        loadVehicleTypeMapping();
    }

    /**
     * 加载黑名单
     */
    private void loadBlacklist() {
        Example example = new Example(OrbitControlBlacklist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitControlBlacklist> blackList = orbitControlBlacklistMapper.selectByExample(example);
        BlacklistRedis.saveVoList(blackList);
    }

    /**
     * 加载白名单
     */
    private void loadWhitelist() {
        Example example = new Example(OrbitControlWhitelist.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitControlWhitelist> whiteList = orbitControlWhitelistMapper.selectByExample(example);
        WhitelistRedis.saveVoList(whiteList);
    }

    /**
     * 加载颜色映射
     */
    private void loadColorMapping() {
        Example example = new Example(OrbitColorMapping.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("country", "001");
        List<OrbitColorMapping> list = colorMappingMapper.selectByExample(example);
        ColorMappingRedis.saveVoList(list);
    }

    /**
     * 加载车辆类型映射
     */
    private void loadVehicleTypeMapping() {
        Example example = new Example(OrbitVehicleTypeMapping.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("country", "001");
        List<OrbitVehicleTypeMapping> list = typeMappingMapper.selectByExample(example);
        VehicleTypeMappingRedis.saveVoList(list);
    }
}
