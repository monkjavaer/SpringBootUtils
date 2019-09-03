//package com.car.vehicle.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.car.base.common.constant.AppConstant;
//import com.car.base.common.utilities.DateUtil;
//import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
//import com.car.base.fastdfs.FastDFSClient;
//import com.car.base.message.MQMessage;
//import com.car.base.message.MessageType;
//import com.car.base.message.VehicleStructureMsg;
//import com.car.base.rabbit.MQSender;
//import com.car.base.rabbit.QueueUtil;
//import com.car.base.redis.RedisHelper;
//import com.car.trunk.center.device.service.DeviceService;
//import com.car.trunk.dal.device.vo.DeviceRedisVO;
//import com.car.vehicle.common.BlockQueue;
//import com.google.common.base.Strings;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import com.car.vehicle.common.PassedbyVehicleKafkaVO;
//
///**
// * 解析原始数据消费者
// *
// * @author monkjavaer
// * @date 2018/9/12 11:26
// */
//@Service("resolveConsumer")
//public class ResolveConsumer implements Runnable {
//
//    private static final Logger logger = LoggerFactory.getLogger(ResolveConsumer.class);
//
//    /**
//     * 设备服务
//     */
//    @Autowired
//    private DeviceService deviceService;
//
//    private BlockingQueue<PassedbyVehicleEntity> passedbyVehicleQueue;
//
//    private BlockingQueue<String> kafkaQueue;
//
//    @Override
//    public void run() {
//        passedbyVehicleQueue = BlockQueue.getPassedbyVehicleQueue();
//        kafkaQueue = BlockQueue.getKafkaQueue();
//        while (true) {
//            try {
//                VehicleStructureMsg vehicleStructureMsg = null;
//                try {
//                    //从数据缓冲队列中取一条原始数据用于构造过车数据
//                    vehicleStructureMsg = BlockQueue.getVehicleStructureMsgQueue().take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if (vehicleStructureMsg == null) {
//                    logger.error("=====VehicleStructureMsgQueue 队列里没有数据=====");
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    continue;
//                }
//
//                logger.info("消费线程获取数据=="+vehicleStructureMsg);
//                //构造过车数据
//                PassedbyVehicleEntity passedbyVehicleEntity = getPassedbyVehicleEntity(vehicleStructureMsg);
//                if(passedbyVehicleEntity == null){
//                    logger.info("passedbyVehicleEntity不应该==NULL,若出现，则放弃该数据");
//                    continue;
//                }
//                //存储图片和记录完了后准备推送布控消息
//                try {
//                    MQSender detectionSender = QueueUtil.getPassDetecMqSender();
//                    MQMessage sendDetectionMessage = new MQMessage();
//                    sendDetectionMessage.setMessageType(MessageType.VEHICLE_DETECTION);
//                    vehicleStructureMsg.setAreaId(passedbyVehicleEntity.getAreaId());
//                    vehicleStructureMsg.setCityId(passedbyVehicleEntity.getCityId());
//                    vehicleStructureMsg.setDeviceId(passedbyVehicleEntity.getDeviceId());
//                    sendDetectionMessage.setBody(vehicleStructureMsg);
//                    detectionSender.sendMessage(sendDetectionMessage);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    logger.error("FileVehicleController send detection message error");
//                }
//
//                //构造的过车数据放入过车数据批量写入队列
//                try {
//                    if (passedbyVehicleEntity != null) {
//                        passedbyVehicleQueue.put(passedbyVehicleEntity);
//                        kafkaQueue.put(JSON.toJSONString(new PassedbyVehicleKafkaVO(passedbyVehicleEntity), SerializerFeature.WriteMapNullValue));
//
//                        logger.info("组装数据=="+passedbyVehicleEntity.getPlateNumber());
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    logger.error("====入队列异常=====");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("====出现未知异常也不应该终止我们的线程====");            }
//        }
//    }
//
//
//
//    /**
//     * 构造过车数据
//     *
//     * @param vehicleStructureMsg
//     * @return
//     */
//    public PassedbyVehicleEntity getPassedbyVehicleEntity(VehicleStructureMsg vehicleStructureMsg) {
//        ArrayList<byte[]> photoDataList = vehicleStructureMsg.getPhotoDataList();
//        PassedbyVehicleEntity psVehicle = new PassedbyVehicleEntity();
//
//        String photoFastDFSUrl = null;
//        //取第一个就是过车图片
//        if (photoDataList.size() > 0) {
//            photoFastDFSUrl =getSavePhotoUrl(photoDataList.get(0));
//        }
//
//        if (!Strings.isNullOrEmpty(photoFastDFSUrl)) {
//            try {
//                return saveRecordToMQ(psVehicle, vehicleStructureMsg, photoFastDFSUrl,deviceService);
//            } catch (Exception e) {
//                logger.error("FileVehicleController save vehicle" + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 保存图片到FASTDFS
//     *
//     * @return
//     */
//    public String getSavePhotoUrl(byte[] photoData) {
//        //定义一个标志
//        boolean getPhotoUrlFlg = true;
//        String photoFastDFSUrl = null;
//        try {
//            photoFastDFSUrl = FastDFSClient.uploadFileByURL(photoData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            getPhotoUrlFlg = false;
//        }
//        //fastDFS未保存成功重试一次
//        if (!getPhotoUrlFlg) {
//            logger.error("======fastDFS保存图片重试1次开始=======");
//            try {
//                Thread.sleep(1000);
//                photoFastDFSUrl = FastDFSClient.uploadFileByURL(photoData);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//                logger.error("======fastDFS保存图片重试1次失败=======");
//            }
//        }
//        return photoFastDFSUrl;
//    }
//
//
//    //TODO 为了演示方便，放在本地
//    public static String uploadFileByURL(byte[] photoData) throws Exception{
//        String photoSave = "/home/data/photo";
//        String separa = "/";
//        String date = DateUtil.currentTime();
//        String sourceCenterPath = photoSave + separa + SnowflakeIdWorkerUtil.generateId() + separa
//                + date.substring(0, 4) + separa
//                + date.substring(5, 7) + separa
//                + date.substring(8, 10) + separa;
//        String fileName = SnowflakeIdWorkerUtil.generateId() + ".jpg";
//        //文件保存位置
//        File saveDir = new File(sourceCenterPath);
//        if (!saveDir.exists()) {
//            saveDir.mkdirs();
//        }
//        File file = new File(saveDir + separa + fileName);
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(photoData);
//        if (fos != null) {
//            fos.close();
//        }
//        return sourceCenterPath + fileName;
//    }
//
//    public static PassedbyVehicleEntity saveRecordToMQ(PassedbyVehicleEntity psVehicle,
//                                                       VehicleStructureMsg vehicleStructureMsg, String photoUrl,DeviceService deviceService) throws Exception {
//        psVehicle.setId(vehicleStructureMsg.getId());
//        psVehicle.setPhotoFastDFSUrl(photoUrl);
//        psVehicle.setCaptureTime(vehicleStructureMsg.getCaptureTime());
//        psVehicle.setPlateNumber(vehicleStructureMsg.getPlateNumber());
//        psVehicle.setVehicleColor(vehicleStructureMsg.getVehicleColor());
//        psVehicle.setPlateColor(vehicleStructureMsg.getPlateColor());
//        psVehicle.setVehicleBrand(vehicleStructureMsg.getVehicleBrand());
//        psVehicle.setVehicleType(vehicleStructureMsg.getVehicleType());
//        psVehicle.setSpeed(vehicleStructureMsg.getSpeed());
//        psVehicle.setRoadwayNo(vehicleStructureMsg.getRoadwayNo());
//        psVehicle.setRoadwayName(vehicleStructureMsg.getRoadwayName());
//
//        psVehicle.setDirectionCode(vehicleStructureMsg.getDirectionCode());
//        psVehicle.setDriveStatus(vehicleStructureMsg.getDriveStatus());
//
//
////        List<DeviceQueryVO> deviceQueryVOS = deviceService.queryInfoByDeviceId(vehicleStructureMsg.getDeviceId());
////        if (deviceQueryVOS.size() > 0) {
////            //将设备ID放入map以便定时更新状态
////            MapConstant.getDeviceIdMap().put(vehicleStructureMsg.getDeviceId(),vehicleStructureMsg.getDeviceId());
////
////            psVehicle.setDeviceName(deviceQueryVOS.get(0).getDeviceName());
////            psVehicle.setAreaName(deviceQueryVOS.get(0).getAreaName());
////            psVehicle.setCityName(deviceQueryVOS.get(0).getCityName());
////            psVehicle.setRoadName(deviceQueryVOS.get(0).getRoadName());
////            psVehicle.setRoadCrossPointId(deviceQueryVOS.get(0).getRoadCrossPointId());
////            psVehicle.setCityId(deviceQueryVOS.get(0).getCityId());
////            psVehicle.setAreaId(deviceQueryVOS.get(0).getAreaId());
////        }
//
//        String deviceQueryVOSJson = RedisHelper.get(AppConstant.DEVICE_REDIS_KEY, vehicleStructureMsg.getBayonetNo());
//        DeviceRedisVO deviceRedisVO= JSON.parseObject(deviceQueryVOSJson,DeviceRedisVO.class);
//        if (deviceRedisVO != null) {
//            //将设备ID放入map以便定时更新状态
//            if(deviceRedisVO.getDeviceId()!=null){
//                MapConstant.getDeviceIdMap().put(deviceRedisVO.getDeviceId(),deviceRedisVO.getDeviceId());
//            }
//            psVehicle.setDeviceId(deviceRedisVO.getDeviceId());
//            psVehicle.setDeviceName(deviceRedisVO.getDeviceName());
//            psVehicle.setAreaName(deviceRedisVO.getAreaName());
//            psVehicle.setCityName(deviceRedisVO.getCityName());
//            psVehicle.setRoadName(deviceRedisVO.getRoadName());
//            psVehicle.setRoadCrossPointId(deviceRedisVO.getRoadId());
//            psVehicle.setCityId(deviceRedisVO.getCityId());
//            psVehicle.setAreaId(deviceRedisVO.getAreaId());
//        }
//
//        return psVehicle;
//    }
//
//}
