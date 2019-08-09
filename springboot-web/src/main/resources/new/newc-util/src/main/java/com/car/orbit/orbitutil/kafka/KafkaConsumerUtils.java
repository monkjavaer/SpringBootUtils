package com.car.orbit.orbitutil.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CreateDate: 2019-4-1 <br/>
 * Author: monkjavaer <br/>
 * Description:封装kafka消费者
 * Version: 1.0
**/
public class KafkaConsumerUtils {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private KafkaConsumer<String, byte[]> consumer;
    private List<String> topics;
    private AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * @description KafkaConsumerUtils构造函数
     * @date: 2019-4-1 11:55
     * @author: monkjavaer
     * @param groupId,消费者组名
     * @param topics,主题列表
     * @param brokerList,形如"ip1:9092,ip2:9092"
     * @return
     */
    private KafkaConsumerUtils(String groupId, List<String> topics, String brokerList) {
        /** 消费者属性配置*/
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");//latest  earliest
        //关闭自动提交偏移量，设置成false，保证数据不丢失
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 10485760);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000);  // 不到1m的情况 等500ms  默认是500
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 12000); //  两个时间 要成对出现  1/3
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 24000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(props);
        /** 配置主题列表*/
        this.topics = topics;
    }

    /**
     * @description 启动消费者线程，处理数据
     * @date: 2019-4-1 12:06
     * @author: monkjavaer
     * @param consumerInterface,消费处理函数
     * @return
     */
    private void consumer(ConsumerInterface consumerInterface) {
        consumer.subscribe(topics);
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);
            boolean empty = records.isEmpty();
            if (!empty) {
                for (ConsumerRecord<String, byte[]> record : records) {
                    byte[] value = record.value();
                    try {
                        consumerInterface.consumer(new String(value, "utf-8"));
                        consumer.commitSync();
                    } catch (Exception e) {
                        logger.error("消费消息异常", e);
                    }
                }
            }
        }
    }

    /**
     * @description 消费静态方法
     * @date: 2019-4-1 14:03
     * @author: monkjavaer
     * @param groupId,组名
     * @param topics,主题列表
     * @param brokerList,服务器broker列表,形如"ip1:9092,ip2:9092"
     * @return
     */
    public static void consumer(String groupId, List<String> topics, String brokerList, ConsumerInterface consumerInterface) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        KafkaConsumerUtils kafkaConsumerUtils = new KafkaConsumerUtils(groupId, topics, brokerList);
                        kafkaConsumerUtils.consumer(consumerInterface);
                    } catch (Exception e) {
                        logger.error("kafka异常, topic: {}", topics, e);
                    }
                }
            }
        });
        thread.start();
    }


    public KafkaConsumer<String, byte[]> getConsumer() {
        return consumer;
    }

    public AtomicBoolean getClosed() {
        return closed;
    }
}
