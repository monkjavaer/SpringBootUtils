//package com.car.vehicle.service;
//
//import com.car.trunk.center.device.service.DeviceService;
//import com.car.trunk.center.device.service.DeviceStatusLogService;
//import com.car.trunk.dal.dictionary.HasOnline;
//import com.car.trunk.dal.model.DeviceEntity;
//import com.car.trunk.dal.model.DeviceStatusLogEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author monkjavaer
// * @date 2018/9/12 12:09
// */
//@Service("updateDeviceService")
//public class UpdateDeviceService {
//
//    private static final Logger logger = LoggerFactory.getLogger(UpdateDeviceService.class);
//    /**
//     * 设备服务
//     */
//    @Autowired
//    private DeviceService deviceService;
//    /**
//     * 智能主机服务
//     */
//    @Autowired
//    private TerminalService terminalService;
//    /**
//     * 设备日志服务
//     */
//    @Autowired
//    private DeviceStatusLogService deviceStatusLogService;
//
//
//    /**
//     * 定时任务更新数据
//     */
//    public void updateDevice() {
//
//        ConcurrentHashMap<BigInteger, BigInteger> dealMap = MapConstant.getDeviceIdMap();
//        try {
//            for (BigInteger deviceId : dealMap.values()) {
//                List<DeviceEntity> deviceQueryVOS = deviceService.queryDeviceByDeviceId(String.valueOf(deviceId));
//                if (deviceQueryVOS.size() > 0) {
//
//                    if (deviceQueryVOS.get(0).getTerminalId() != null) {
//                        TerminalEntity terminalEntity = new TerminalEntity();
//                        terminalEntity.setId(deviceQueryVOS.get(0).getTerminalId());
//                        terminalEntity.setOnline(HasOnline.YES.value);
//                        MapConstant.getTerminalMap().put(terminalEntity.getId(), terminalEntity);
//
//                        DeviceStatusLogEntity deviceStatusLogEntity = new DeviceStatusLogEntity();
//                        deviceStatusLogEntity.setTerminalId(deviceQueryVOS.get(0).getTerminalId());
//                        Date date = new Date();
//                        deviceStatusLogEntity.setOnline(HasOnline.YES.value);
//                        deviceStatusLogEntity.setLastupdateTime(date);
//                        MapConstant.getDeviceStatusLogEntityMap().put(deviceStatusLogEntity.getTerminalId(), deviceStatusLogEntity);
//                    }
//
//
//                }
//            }
//            //清空map
//            MapConstant.getDeviceIdMap().clear();
//
//            //批量更新智能主机，设备日志表
//            List<TerminalEntity> terminalEntityList = new ArrayList<>(MapConstant.getTerminalMap().values());
//            if (terminalEntityList.size() > 0) {
//                terminalService.batchUpdateTerminal(terminalEntityList);
//            }
//            List<DeviceStatusLogEntity> deviceStatusLogEntityList = new ArrayList<>(MapConstant.getDeviceStatusLogEntityMap().values());
//            if (deviceStatusLogEntityList.size() > 0) {
//                deviceStatusLogService.batchUpdateDeviceStatusLog(deviceStatusLogEntityList);
//            }
//
//            //清空Map
//            MapConstant.getTerminalMap().clear();
//            MapConstant.getDeviceStatusLogEntityMap().clear();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("=========定时更新异常======");
//        }
//    }
//
//}
