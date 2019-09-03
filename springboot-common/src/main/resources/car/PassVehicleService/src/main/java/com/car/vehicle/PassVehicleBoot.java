//package com.car.vehicle;
//
//import com.car.vehicle.service.BatchConsumer;
//import com.car.vehicle.service.SaveRecordService;
//import com.car.vehicle.service.SendToKafkaConsumer;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
//import java.util.concurrent.*;
//
///**
// * 过车服务程序入口
// *
// * @author monkjavaer
// * @date 2018/8/14 17:52
// */
//public class PassVehicleBoot {
//
//    /**
//     * 日志对象
//     */
//    private static Logger logger = LoggerFactory.getLogger(PassVehicleBoot.class);
//
//    /**
//     * xml配置文件。
//     */
//    private static String[] strPublicXml = new String[]
//            {"classpath:applicationContext.xml"};
//
//    /**
//     * 程序入口
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        logger.info("==============PassVehicle start================");
//        try {
//            AbstractApplicationContext ctx = new FileSystemXmlApplicationContext(strPublicXml);
//            ctx.getBean("fileVehicleController");
//
//            SaveRecordService saveRecordService = (SaveRecordService) ctx.getBean("saveRecordService");
//            //调用init方法
//            saveRecordService.initSave(ctx);
//
//            BatchConsumer batchConsumer = (BatchConsumer) ctx.getBean("batchConsumer");
//            batchConsumer.batchsave();
//
//            // 注册关闭函数钩子，随JVM关闭而关闭
//            ctx.registerShutdownHook();
//        } catch (BeansException e) {
//            e.printStackTrace();
//            logger.error("exception:" + e.getMessage(), e);
//        }
//    }
//
//}
