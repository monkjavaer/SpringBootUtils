//package com.car.vehicle.controller;
//
//import com.car.base.common.constant.EnvConstant;
//import com.car.base.message.MQMessage;
//import com.car.base.message.MessageType;
//import com.car.base.message.VehicleStructureMsg;
//import com.car.base.rabbit.MQReceiver;
//import com.car.vehicle.common.BlockQueue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//
//import java.util.Observable;
//import java.util.Observer;
//
///**
// * Created by Eric on 2018/6/7.
// *
// * @author monkjavaer
// */
//
//@Controller
//public class FileVehicleController implements Observer {
//
//    private static final Logger logger = LoggerFactory.getLogger(FileVehicleController.class);
//
//
//    public FileVehicleController() {
//        MQReceiver.listen(this, EnvConstant.NODE_PASS_QUEUE_NAME);
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//
//        MQMessage message = (MQMessage) arg;
//
//        if (MessageType.VEHICLE_STRUCTURE.equals(message.getMessageType())) {
//
//            VehicleStructureMsg vehicleStructureMsg = (VehicleStructureMsg) message.getBody();
//
//            logger.info("接收数据=="+vehicleStructureMsg.getPlateNumber());
//            //接收到mq数据放入队列
//            try {
//                logger.info("图片大小==="+vehicleStructureMsg.getPhotoDataList().size());
////                if (vehicleStructureMsg.getPhotoDataList().size() > 0) {
//                    BlockQueue.getVehicleStructureMsgQueue().put(vehicleStructureMsg);
////                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                logger.error("=============接收到mq数据放入队列异常========");
//            }
//
//        }
//    }
//
//
//}
