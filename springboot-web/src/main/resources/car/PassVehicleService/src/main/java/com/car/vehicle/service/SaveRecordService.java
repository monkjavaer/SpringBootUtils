//package com.car.vehicle.service;
//
//import com.car.trunk.center.device.service.DeviceInitUtils;
//import com.car.trunk.center.device.service.DeviceService;
//import com.car.vehicle.common.BlockQueue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.RejectedExecutionException;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * Created by Eric on 2018/6/12.
// *
// * @author monkjavaer
// */
//@Service("saveRecordService")
//public class SaveRecordService {
//    /**
//     * 日志
//     */
//    private static final Logger logger = LoggerFactory.getLogger(SaveRecordService.class);
//
//    /**
//     * 设备服务
//     */
//    @Autowired
//    private DeviceService deviceService;
//
//    ThreadPoolExecutor taskService = new ThreadPoolExecutor(BlockQueue.corePookSize, BlockQueue.maximumPoolSize,
//            BlockQueue.keepActiveTime, BlockQueue.timeUnit, BlockQueue.taskServiceQueue, new ThreadFactory() {
//        @Override
//        public Thread newThread(Runnable r) {
//            Thread t = new Thread(r);
//            t.setName("VehiclePass-Receiver");
//            return t;
//        }
//    });
//
//
//    /**
//     * 开始处理接收的数据
//     */
//    public void initSave(AbstractApplicationContext ctx) {
//
//        //将所有电子警察放入rides
//        logger.info("passVehicle service start init redis information...");
//        try {
//            DeviceInitUtils.initDeviceToRedis(deviceService);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("init redis information error！！！");
//        }
//        logger.info("init redis information complete.");
//
//        ResolveConsumer resolveConsumer = (ResolveConsumer) ctx.getBean("resolveConsumer");
//        try {
//            executeSave(resolveConsumer);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("========executeSave Exception========");
//        }
//
//    }
//
//    /**
//     * ThreadPoolExecutor没有定义拒绝策略，目前捕获RejectedExecutionException异常递归调用，继续执行
//     *
//     * @param runnable
//     * @throws InterruptedException
//     */
//    public void executeSave(Runnable runnable) throws InterruptedException {
//        boolean flg = true;
//        try {
//            taskService.execute(runnable);
//        } catch (RejectedExecutionException e) {
//            e.printStackTrace();
//            flg = false;
//            logger.error("=========RejectedExecutionException=========");
//        }
//        if (!flg) {
//            Thread.sleep(100);
//            executeSave(runnable);
//        }
//    }
//}
