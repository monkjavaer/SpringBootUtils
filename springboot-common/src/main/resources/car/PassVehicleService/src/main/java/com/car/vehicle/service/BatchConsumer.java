//package com.car.vehicle.service;
//
//import com.car.trunk.center.vehiclecontrol.service.PassVehicleService;
//import com.car.vehicle.common.BlockQueue;
//import com.google.common.collect.Queues;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
///**
// * 批量插入消费者
// *
// * @author monkjavaer
// * @date 2018/9/12 10:46
// */
//@Service("batchConsumer")
//public class BatchConsumer {
//
//    private static final Logger logger = LoggerFactory.getLogger(BatchConsumer.class);
//
//    /**
//     * 解析的过车数据队列
//     */
//    private BlockingQueue<PassedbyVehicleEntity> passedbyVehicleQueue;
//
//    /**
//     * 过车服务
//     */
//    @Autowired
//    private PassVehicleService passVehicleService;
//
//    @Autowired
//    private  SendToKafkaConsumer sendToKafkaConsumer;
//
//    /**
//     * 批量插入
//     */
//    public void batchsave() {
//        passedbyVehicleQueue = BlockQueue.getPassedbyVehicleQueue();
//        ThreadFactory kafkaThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("kafka-pool-%d").build();
//        ExecutorService kafkaThreadPool = new ThreadPoolExecutor(2, 5,
//                5000L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), kafkaThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//        kafkaThreadPool.execute(() -> sendToKafkaConsumer.sendToKafka());
//        while (true) {
//            List<PassedbyVehicleEntity> lists = new ArrayList<PassedbyVehicleEntity>();
//            try {
//                Queues.drain(passedbyVehicleQueue, lists, BlockQueue.BATCH_QUEUE, 100, TimeUnit.MILLISECONDS);
//                if (lists.size() > 0) {
//                    logger.info("batchsave passVehicle Data");
//                    passVehicleService.batchSave(lists);
//                }else{
//                    TimeUnit.MILLISECONDS.sleep(10);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("批量执行异常");
//            }
//
//        }
//
//    }
//
//}
