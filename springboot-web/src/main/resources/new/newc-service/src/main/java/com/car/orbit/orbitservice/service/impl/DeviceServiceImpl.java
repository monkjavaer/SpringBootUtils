package com.car.orbit.orbitservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.*;
import com.car.orbit.orbitservice.enums.*;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.GeoPositionDuplicateException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.mapper.OrbitControlTaskMapper;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceManufacturerMapper;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceMapper;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceTypeMapper;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IDivisionService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.AdminServerUtil;
import com.car.orbit.orbitservice.util.IpUtil;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitservice.vo.DeviceSimpleVO;
import com.car.orbit.orbitservice.vo.DeviceStructuralVO;
import com.car.orbit.orbitservice.vo.DivisionSimpleVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.PropertiesUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.car.vision.VisionSDK;
import com.car.vision.result.DeviceInfo;
import com.car.vision.result.DeviceResult;
import com.car.vision.result.DeviceType;
import com.car.vision.result.Manufacturer;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Title: DeviceServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 设备管理服务实现类
 * @Author: monkjavaer
 * @Data: 2019/3/8 11:08
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    /**
     * 设备mapper
     */
    @Autowired
    private OrbitResDeviceMapper deviceMapper;

    /**
     * 厂商mapper
     */
    @Autowired
    private OrbitResDeviceManufacturerMapper manufacturerMapper;

    /**
     * 设备类型mapper
     */
    @Autowired
    private OrbitResDeviceTypeMapper deviceTypeMapper;

    /**
     * 路口mapper
     */
    @Autowired
    private IDivisionService divisionService;

    /**
     * 用户mapper
     */
    @Autowired
    private IUserService iUserService;

    /**
     * 任务mapper
     */
    @Autowired
    private OrbitControlTaskMapper controlTaskMapper;

    /**
     * 添加设备
     *
     * @param device OrbitResDevice
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_DEVICE, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addDevice(OrbitResDevice device) throws DuplicateDataException, GeoPositionDuplicateException {
        if (checkDuplicate(device)) {
            throw new DuplicateDataException("Already exist same device");
        }

        if (checkGeoPositionDuplicate(device)) {
            throw new GeoPositionDuplicateException("Already exist same geo position");
        }

        device.setId(UUIDUtils.generate());
        device.setOnline(OnlineEnum.NO.getValue());
        device.setDeleted(HasDeleteEnum.NO.getValue());
        device.setCreateTime(new Date());
        deviceMapper.insert(device);

        //添加设备后存入redis
        DeviceSimpleVO redisDevice = new DeviceSimpleVO();
        BeanUtils.copyProperties(device, redisDevice);

        DevicePointRedis.saveVo(device);

        // 通知结构化服务器添加分析设备
        asyncAddToSDK(device);
    }

    /**
     * 设备名查重
     *
     * @param device
     * @return
     */
    private boolean checkDuplicate(OrbitResDevice device) {
        Example example = new Example(OrbitResDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("BINARY NAME = ", device.getName())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (StringUtils.isNotEmpty(device.getId())) {
            criteria.andNotEqualTo("id", device.getId());
        }

        if (deviceMapper.selectCountByExample(example) > 0) {
            return true;
        }

        return false;
    }

    /**
     * 设备经纬度查重
     *
     * @param device
     * @return
     */
    private boolean checkGeoPositionDuplicate(OrbitResDevice device) {
        Example example = new Example(OrbitResDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("latitude", device.getLatitude())
                .andEqualTo("longitude", device.getLongitude())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (StringUtils.isNotEmpty(device.getId())) {
            criteria.andNotEqualTo("id", device.getId());
        }

        if (deviceMapper.selectCountByExample(example) > 0) {
            return true;
        }

        return false;
    }

    /**
     * 添加设备后异步执行外部接口调用
     *
     * @param device
     */
    @Async
    void asyncAddToSDK(OrbitResDevice device) {
        logger.info("======添加设备时，异步处理数据,调用结构化服务器接口=======");
        DeviceInfo deviceInfo = getDeviceInfo(device);

        String videoDeviceId;
        try {
            /** 成功后会把生成的DeviceID写入deviceInfo；若该设备已存在，则会报错，返回已有设备的ID **/
            JSONObject jsonObject = OrbitServiceConstant.visionSDK.installDevice(device.getId(), deviceInfo, true);
            boolean res = jsonObject.getBoolean("result");
            if (res) {
                //成功直接获取
                videoDeviceId = deviceInfo.getDeviceId();
            } else {
                videoDeviceId = jsonObject.getString("id");
            }

            if (StringUtils.isBlank(videoDeviceId)) {
                DeviceResult deviceResult = new DeviceResult();
                boolean status = OrbitServiceConstant.visionSDK.deviceListQuery(UUIDUtils.generate(), 1, 1000, deviceResult);
                if (status) {
                    videoDeviceId = getDeviceId(device, deviceResult);
                } else {
                    logger.error("Add device error by sdk,return false !");
                    return;
                }
            }

            //调用视频SDK添加设备，并获取设备id，用于播放
//            boolean res = OrbitServiceConstant.visionSDK.installDevice(device.getId(), deviceInfo, true);
//            if (!res) {
//                //返回false，可能是视频监控已经存在设备ip，或者其他错误
//                //同步设备，查询视频监控设备id
//                DeviceResult deviceResult = new DeviceResult();
//                boolean status = OrbitServiceConstant.visionSDK.deviceListQuery(UUIDUtils.generate(), 1, 1000, deviceResult);
//                if (status) {
//                    videoDeviceId = getDeviceId(device, deviceResult);
//                } else {
//                    logger.error("Add device error by sdk,return false !");
//                    return;
//                }
//            } else {
//                //成功直接获取
//                videoDeviceId = deviceInfo.getDeviceId();
//            }

            if (StringUtils.isBlank(videoDeviceId)) {
                logger.error("videoDeviceId为空");
                return;
            }


            //添加设备后视频监控平台返回ID,更新到设备表，用于播放
            device.setVideoDeviceId(videoDeviceId);
            updateDevice(device);

            //视频监控平台返回ID,获取实时视频rtsp串
            //String rtsp = "rtsp://Admin:1111@10.15.24.22:554/1/1";
            String rtsp = OrbitServiceConstant.visionSDK
                    .realTimeVideoRTSPRequest(UUIDUtils.generate(), videoDeviceId);
            if (StringUtils.isBlank(rtsp)) {
                logger.error("rtsp is null,准备重连...");
                //5秒后重试1次
                TimeUnit.SECONDS.sleep(5);
                rtsp = OrbitServiceConstant.visionSDK
                        .realTimeVideoRTSPRequest(UUIDUtils.generate(), videoDeviceId);
                if (StringUtils.isBlank(rtsp)) {
                    logger.error("重新获取实时流失败,rtsp is null");
                    return;
                }
            }

            //视频监控返回的rtsp格式为rtsp://192.168.20.33:5554/RealTime/100000037/1/mainstream##http://192.168.20.33:8088/vision
            rtsp = rtsp.substring(0, rtsp.indexOf("mainstream") - 1);
            logger.info("=====rtsp====" + rtsp);
            if (device.getManufacturer().equals(DeviceManufacturerEnum.TIANDY.getValue()) &&
                    device.getType().equals(DeviceTypeEnum.IPC.getValue())) {
                logger.info("添加天地卡口摄像机");
                //处理天地的卡口摄像机
                //构造结构化服务器所需数据
                List<DivisionSimpleVO> divisionSimpleVO = divisionService.queryDivisionSimpleVO(device.getRoadCrossPointId());
                DeviceStructuralVO structuralVO = new DeviceStructuralVO();
                structuralVO.setDeviceId(device.getId());
                structuralVO.setDeviceName(device.getName());
                structuralVO.setCityId(divisionSimpleVO.get(0).getCityId());
                structuralVO.setCityName(divisionSimpleVO.get(0).getCityName());
                structuralVO.setAreaId(divisionSimpleVO.get(0).getAreaId());
                structuralVO.setAreaName(divisionSimpleVO.get(0).getAreaName());
                structuralVO.setRoadCrossPointId(divisionSimpleVO.get(0).getRoadCrossPointId());
                structuralVO.setRoadName(divisionSimpleVO.get(0).getRoadName());
                structuralVO.setIp(device.getIp());
                structuralVO.setPort(device.getPort());
                structuralVO.setUserName(device.getUsername());
                structuralVO.setPassword(device.getPassword());
                structuralVO.setManufacturer(device.getManufacturer());
                //调用结构化服务器，接收视频流任务
                String result = AdminServerUtil.sendIPC(structuralVO);
                logger.info("Add structural server result:{}", result);
            } else {
                //处理普通摄像机
                //构造结构化服务器所需数据
                List<DivisionSimpleVO> divisionSimpleVO = divisionService.queryDivisionSimpleVO(device.getRoadCrossPointId());
                DeviceStructuralVO structuralVO = new DeviceStructuralVO();
                structuralVO.setDeviceId(device.getId());
                structuralVO.setDeviceName(device.getName());
                structuralVO.setCityId(divisionSimpleVO.get(0).getCityId());
                structuralVO.setCityName(divisionSimpleVO.get(0).getCityName());
                structuralVO.setAreaId(divisionSimpleVO.get(0).getAreaId());
                structuralVO.setAreaName(divisionSimpleVO.get(0).getAreaName());
                structuralVO.setRoadCrossPointId(divisionSimpleVO.get(0).getRoadCrossPointId());
                structuralVO.setRoadName(divisionSimpleVO.get(0).getRoadName());
                structuralVO.setVideoPath(rtsp);
                //调用结构化服务器，接收视频流任务
                String result = AdminServerUtil.sendRtsp(structuralVO);
                logger.info("Add structural server result:{}", result);
            }

        } catch (Exception e) {
            logger.error("Add structural server error,{}", e.getMessage());
        }
    }

    private String getDeviceId(OrbitResDevice device, DeviceResult deviceResult) {
        List<DeviceInfo> deviceinfos = deviceResult.getDevices();
        for (DeviceInfo deviceInfo : deviceinfos) {
            if (device.getIp().equals(deviceInfo.getIp())) {
                return deviceInfo.getDeviceId();
            }
        }
        return null;
    }


    /**
     * 包装SDK所需List<DeviceInfo>参数
     *
     * @param device OrbitResDevice
     * @return List<DeviceInfo>
     */
    private DeviceInfo getDeviceInfo(OrbitResDevice device) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceName(device.getName());
        if (device.getType() == DeviceTypeEnum.IPC.getValue()) {
            deviceInfo.setDeviceType(DeviceType.IPC);
        } else if (device.getType() == DeviceTypeEnum.COMMON_CAMERA.getValue()) {
            deviceInfo.setDeviceType(DeviceType.IPC);
        }
        deviceInfo.setInstallAddress(device.getInstallAddress());
        deviceInfo.setManufacturer(Manufacturer.convert(device.getManufacturer()));
        deviceInfo.setLatitude(device.getLatitude());
        deviceInfo.setLongitude(device.getLongitude());
        deviceInfo.setIp(device.getIp());
        deviceInfo.setPort(device.getPort());
        deviceInfo.setUserName(device.getUsername());
        deviceInfo.setPassword(device.getPassword());
        //下面这两个参数，视频平台暂时让加的
        deviceInfo.setSnCode(UUIDUtils.generate());
        deviceInfo.setChannelCount("1");

        return deviceInfo;
    }

    /**
     * 删除设备（逻辑删除，delete字段）
     *
     * @param device OrbitResDevice
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_DEVICE, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitResDeviceMapper.class)
    @Override
    public void deleteDevice(OrbitResDevice device) throws RelationshipException {
        Example example = new Example(OrbitControlTask.class);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ControlTaskStatusEnum.ON_CONTROL.getValue());
        statusList.add(ControlTaskStatusEnum.PENDING.getValue());
        example.createCriteria()
                .andLike("devices", "%" + device.getId() + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andIn("status", statusList);
        if (controlTaskMapper.selectCountByExample(example) > 0) {
            throw new RelationshipException("Device related with control task");
        }

        device.setDeleted(HasDeleteEnum.YES.getValue());
        deviceMapper.updateByPrimaryKeySelective(device);
        //删除审批未通过布控任务中包含的该设备信息,已结束的因为查看历史状态所以不删除该设备信息
        statusList = new ArrayList<>();
        statusList.add(ControlTaskStatusEnum.OVER.getValue());
        statusList.add(ControlTaskStatusEnum.NOT_PASS.getValue());
        example = new Example(OrbitControlTask.class);
        example.createCriteria()
                .andLike("devices", "%" + device.getId() + "%")
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue())
                .andIn("status", statusList);
        List<OrbitControlTask> tasks = controlTaskMapper.selectByExample(example);
        for (OrbitControlTask task : tasks){
            List<String> tmpTaskDevices = new ArrayList<>();
            for(String taskDevice : task.getDevices().split(",")){
                if (!taskDevice.equals(device.getId())){
                    tmpTaskDevices.add(taskDevice);
                }
            }
            task.setDevices(String.join(",", tmpTaskDevices));
            controlTaskMapper.updateByPrimaryKeySelective(task);
        }
        //删除设备异步调用结构化删除任务
        asyncRemoveTask(device.getId());
    }

    /**
     * 删除设备，异步移除结构化任务
     *
     * @param deviceId
     */
    @Async
    void asyncRemoveTask(String deviceId) {
        logger.info("======删除设备，异步移除结构化任务=======");
        try {
            //调用结构化服务器，删除视频流任务
            String result = AdminServerUtil.remove(deviceId);
            logger.info("remove structural server task result:{}", result);
        } catch (Exception e) {
            logger.error("remove structural server task error,{}", e.getMessage());
        }
    }

    /**
     * 修改设备
     *
     * @param device OrbitResDevice
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_DEVICE, actionType = OrbitServiceConstant.ACTION_UPDATE, mapperType = OrbitResDeviceMapper.class)
    @Override
    public void updateDevice(OrbitResDevice device) throws IllegalParamException, DuplicateDataException, GeoPositionDuplicateException {
        if (StringUtils.isBlank(device.getName().trim())
                || StringUtils.isBlank(device.getIp().trim())
                || StringUtils.isBlank(device.getUsername().trim())
                || StringUtils.isBlank(device.getPassword().trim())
                || device.getLatitude() == null
                || device.getLongitude() == null
                || !IpUtil.isIP(device.getIp())) {
            throw new IllegalParamException("Invalid param");
        }
        if (checkDuplicate(device)) {
            throw new DuplicateDataException("Already exist same device");
        }
        if (checkGeoPositionDuplicate(device)) {
            throw new GeoPositionDuplicateException("Already exist same geo position");
        }
        device.setUpdateTime(new Date());
        if (deviceMapper.updateByPrimaryKeySelective(device) > 0) {
            DevicePointRedis.saveVo(device);
        }
    }

    /**
     * 设备分页查询
     *
     * @param deviceQO 设备分页查询qo
     * @return
     */
    @Override
    public PageUtil<DeviceDetailVO> queryPageList(DeviceQO deviceQO) {
        OrbitResDeviceExtends tmp = iUserService.getUserAuthority();
        deviceQO.setCityIdList(tmp.getCityIdList());
        deviceQO.setAreaIdList(tmp.getAreaIdList());
        deviceQO.setRoadIdList(tmp.getRoadIdList());

        PageHelper.startPage(deviceQO.getPageNo(), deviceQO.getPageSize());
        List<DeviceDetailVO> list = deviceMapper.queryListByPage(deviceQO);

        List<OrbitResDeviceManufacturer> manufacturerList = manufacturerMapper.selectAll();
        Map<String, OrbitResDeviceManufacturer> map = new HashMap<>();
        manufacturerList.stream().forEach(orbitResDeviceManufacturer -> map.put(orbitResDeviceManufacturer.getId(), orbitResDeviceManufacturer));

        list.stream().forEach(deviceDetailVO -> {
            if (deviceDetailVO.getType() == 1) {
                deviceDetailVO.setTypeName("IPC");
                deviceDetailVO.setManufacturerName("TIANDY");
            } else {
                deviceDetailVO.setTypeName("Ordinary Camera");
                deviceDetailVO.setManufacturerName("");
            }
        });
        return new PageUtil<>(list);
    }

    /**
     * 查询所有未被删除设备
     *
     * @param device
     * @return
     */
    @Override
    public List<DeviceSimpleVO> queryAllList(OrbitResDevice device) {
        /** 拷贝查询条件*/
        OrbitResDeviceExtends orbitResDeviceExtends = new OrbitResDeviceExtends();
        BeanUtils.copyProperties(device, orbitResDeviceExtends);
        /** 加入用户权限限制*/
        OrbitResDeviceExtends tmp = iUserService.getUserAuthority();
        BeanUtils.copyProperties(tmp, orbitResDeviceExtends);
        /** 查询*/
        return deviceMapper.queryAllList(orbitResDeviceExtends);
    }

    /**
     * 获取用户权限内所有设备的Id列表
     * @return
     */
    @Override
    public List<String> queryAllDeviceIdList() {
        OrbitResDeviceExtends deviceExtends = iUserService.getUserAuthority();
        deviceExtends.setDeleted(HasDeleteEnum.NO.getValue());
        List<DeviceSimpleVO> deviceList = deviceMapper.queryAllList(deviceExtends);
        List<String> deviceIdList = new ArrayList<>();
        deviceList.stream().forEach(deviceSimpleVO -> deviceIdList.add(deviceSimpleVO.getId()));
        return deviceIdList;
    }

    /**
     * 设备类型
     *
     * @return
     */
    @Override
    public List<OrbitResDeviceType> queryDeviceType() {
        return deviceTypeMapper.selectAll();
    }

    /**
     * 设备厂商
     *
     * @return
     */
    @Override
    public List<OrbitResDeviceManufacturer> queryDeviceManufacturer() {
        return manufacturerMapper.selectAll();
    }

    @Override
    public OrbitResDevice getDeviceById(String id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    /**
     * 启动时加载sdk
     */
    @Override
    public void initSdk() {
        try {
            PropertiesUtils propertiesUtil = new PropertiesUtils("/sdk.properties");
            String serverIP = propertiesUtil.getPropertieValue("serverIP");
            int soapPort = Integer.parseInt(propertiesUtil.getPropertieValue("soapPort"));
            int tcpPort = Integer.parseInt(propertiesUtil.getPropertieValue("tcpPort"));
            String url = propertiesUtil.getPropertieValue("url");
            String username = propertiesUtil.getPropertieValue("username");
            String password = propertiesUtil.getPropertieValue("password");
            OrbitServiceConstant.visionSDK = VisionSDK.getInstance(
                    serverIP, soapPort, tcpPort, url, username, password);
            logger.info("****sdk****={}", OrbitServiceConstant.visionSDK.toString());
            OrbitServiceConstant.relayIP = propertiesUtil.getPropertieValue("relayIP");
            OrbitServiceConstant.credential = propertiesUtil.getPropertieValue("credential");
            OrbitServiceConstant.relayUsername = propertiesUtil.getPropertieValue("relayUsername");
            OrbitServiceConstant.relayPassword = propertiesUtil.getPropertieValue("relayPassword");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("init sdk error,error message:{}", e.getMessage());
        }
    }
}
