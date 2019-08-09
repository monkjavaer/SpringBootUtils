//package com.car.vehicle.common;
//
//import com.car.base.message.VehicleStructureMsg;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Eric on 2018/6/15.
// */
//public class BlockQueue {
//
//    public static int corePookSize = 60;
//
//    public static int maximumPoolSize = 100;
//
//    public static long keepActiveTime = 50;
//
//    public static TimeUnit timeUnit = TimeUnit.SECONDS;
//
//
//    /**
//     * 批量写数据
//     */
//    public static int BATCH_QUEUE = 500;
//
//    /**
//     * 批量发送kafka数据
//     */
//    public static int BATCH_SEND_KAFKA_QUEUE = 50;
//
//    /**
//     * MQ接收队列大小
//     */
//    public static int GETMQ_QUEUE_SIZE = 20000;
//
//    public static int PASS_VEHICLE_QUEUE_SIZE = 20000;
//
//    public static BlockingQueue<Runnable> taskServiceQueue = new LinkedBlockingQueue<Runnable>(100);
//
//    /**
//     * 过车数据批量写入队列
//     */
//    private static BlockingQueue<PassedbyVehicleEntity> passedbyVehicleQueue;
//
//    /**
//     * 过车数据发送到kafka队列
//     */
//    private static BlockingQueue<String> kafkaQueue;
//
//    /**
//     * 从MQ接收数据缓冲队列
//     */
//    private static BlockingQueue<VehicleStructureMsg> vehicleStructureMsgQueue;
//
//
//
//
//    /**
//     * 获取过车数据批量写入队列
//     *
//     * @return
//     */
//    public static BlockingQueue<PassedbyVehicleEntity> getPassedbyVehicleQueue() {
//        if (passedbyVehicleQueue == null) {
//            passedbyVehicleQueue = new LinkedBlockingQueue<PassedbyVehicleEntity>(BlockQueue.PASS_VEHICLE_QUEUE_SIZE);
//        }
//        return passedbyVehicleQueue;
//    }
//
//    /**
//     * 获取发送到kafka中间件的写入队列
//     *
//     * @return
//     */
//    public static BlockingQueue<String> getKafkaQueue() {
//        if (kafkaQueue == null) {
//            kafkaQueue = new LinkedBlockingQueue<String>(BlockQueue.PASS_VEHICLE_QUEUE_SIZE);
//        }
//        return kafkaQueue;
//    }
//
//    /**
//     * 获取MQ接收队列
//     * @return
//     */
//    public static BlockingQueue<VehicleStructureMsg> getVehicleStructureMsgQueue() {
//        if (vehicleStructureMsgQueue == null) {
//            vehicleStructureMsgQueue = new LinkedBlockingQueue<VehicleStructureMsg>(BlockQueue.GETMQ_QUEUE_SIZE);
//        }
//        return vehicleStructureMsgQueue;
//    }
//
//}
