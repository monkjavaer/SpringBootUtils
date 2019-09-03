//package com.car.vehicle.service;
//
//import com.car.base.common.constant.EnvConstant;
//import com.car.base.kafka.KafkaProducerUtils2;
//import com.car.trunk.center.device.service.DeviceService;
//import com.car.vehicle.common.BlockQueue;
//import com.google.common.collect.Queues;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
///**
// * 发送过车数据到百分点kafka中间件
// *
// * @author monkjavaer
// * @date 2019/3/25 10:48
// */
//@Service("sendToKafkaConsumer")
//public class SendToKafkaConsumer {
//
//    private static final Logger logger = LoggerFactory.getLogger(SendToKafkaConsumer.class);
//
//    /**
//     * 设备服务
//     */
//    @Autowired
//    private DeviceService deviceService;
//
//
//    private BlockingQueue<String> passedbyVehicleKfakaQueue ;
//
//    public void sendToKafka() {
//        passedbyVehicleKfakaQueue = BlockQueue.getKafkaQueue();
//        KafkaProducerUtils2.init(EnvConstant.METADATA_BROKER_LIST);
//        while (true) {
//            List<String> lists = new ArrayList<String>();
//            try {
//                Queues.drain(passedbyVehicleKfakaQueue, lists, BlockQueue.BATCH_SEND_KAFKA_QUEUE, 500, TimeUnit.MILLISECONDS);
//                if (lists.size() > 0) {
//                    KafkaProducerUtils2.sendMsgs(EnvConstant.KAFKA_TOPIC_PASSEDBYVEHICLE,lists);
//                }else{
//                    TimeUnit.MILLISECONDS.sleep(500);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("批量执行异常");
//                KafkaProducerUtils2.close();
//            }
//
//        }
//
//    }
//
//
//
//
//
//}
