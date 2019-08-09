/*
package com.car.base.kafka;

import com.car.base.common.constant.EnvConstant;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
*/
/**
 * CreateDate：2019/03/24<br/>
 * Author：monkjavaer <br/>
 * Description: 该类主要用于发送kafka消息
 **//*

public class KafkaProducerUtils {

    */
/** kafka生产者对象 *//*

    private static Producer<String, String> producer;

    //属性初始化
    static {
        Properties prop = new Properties();
        prop.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        prop.setProperty("metadata.broker.list", EnvConstant.METADATA_BROKER_LIST);
        prop.setProperty("request.required.acks", EnvConstant.REQUEST_REQUIRED_ACKS);
        ProducerConfig config = new ProducerConfig(prop);
        producer = new Producer(config);
    }

    */
/**
     * 发送单条kafka消息
     *
     * @param topic 消息topic
     * @param message json格式的消息内容
     *//*

    public static void sendMsg(String topic, String message) {
        KeyedMessage<String, String> keyedMessage = new KeyedMessage<>(topic, message);
        producer.send(keyedMessage);
    }

    */
/**
     * 批量发送kafka消息
     *
     * @param topic 消息topic
     * @param messages json格式的消息内容列表
     *//*

    public static void batchSendMsg(String topic, List<String> messages) {
        List<KeyedMessage<String, String>> keyedMessages = new ArrayList<>();
        for (String message : messages) {
            KeyedMessage<String, String> keyedMessage = new KeyedMessage<>(topic, message);
            keyedMessages.add(keyedMessage);
        }
        producer.send(keyedMessages);
    }


}
*/
