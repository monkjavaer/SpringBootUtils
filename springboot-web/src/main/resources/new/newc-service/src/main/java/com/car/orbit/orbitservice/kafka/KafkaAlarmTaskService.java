package com.car.orbit.orbitservice.kafka;

import com.car.orbit.orbitservice.constant.KafkaConstant;
import com.car.orbit.orbitutil.kafka.KafkaConsumerUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @Title: KafkaAlarmTaskService
 * @Package: com.car.orbit.orbitservice.kafka
 * @Description: kafka布控预警任务
 * @Author: monkjavaer
 * @Data: 2019/4/10 19:00
 * @Version: V1.0
 */
@Service
public class KafkaAlarmTaskService {

    @Autowired
    private Environment env;

    /**
     * 布控预警服务
     */
    @Autowired
    private AlarmConsumerService alarmConsumerService;


    private ThreadFactory kafkaThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("kafka-pool-%d").build();

    private ExecutorService kafkaThreadPool = new ThreadPoolExecutor(2, 5,
            5000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), kafkaThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 新启动一个线程，接收kafka报警消息
     */
    public void start() {

        kafkaThreadPool.execute(() -> {
            String[] topics = new String[]{env.getProperty(KafkaConstant.KAFKA_TOPICS_ALARM)};
            String brokerList = env.getProperty(KafkaConstant.KAFKA_BROKER_LIST);
            KafkaConsumerUtils.consumer(env.getProperty(KafkaConstant.KAFKA_GROUPID_ALARM),
                    Arrays.asList(topics), brokerList, alarmConsumerService);
        });

        //kafkaThreadPool.shutdown();
    }

}
