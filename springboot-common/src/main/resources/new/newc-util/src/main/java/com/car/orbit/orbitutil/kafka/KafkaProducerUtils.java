package com.car.orbit.orbitutil.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
/**
 * CreateDate：2017/11/20 <br/>
 * Author：monkjavaer <br/>
 * Description: 该类主要用于发送kafka消息,分区采用轮询的方式
 **/
public class KafkaProducerUtils {

    /** kafka生产者对象 */
    private static Producer<String, byte[]> producer;
    private static boolean inited = false;

    /**
     * 发送单条kafka消息
     *
     * @param topic 消息topic枚举值
     * @param message json格式的消息内容
     */
    public static void sendMsg(String topic ,String message) {
        if(!inited) {
            System.out.println("!!!!!!error: 请先调用init方法初始化");
            return;
        }
        sendOne(topic, message);
    }

    private static void  sendOne(String topic, String message){
        try {
            byte[] msg = message.getBytes("utf-8");
            ProducerRecord<String, byte[]> keyedMessage = new ProducerRecord<>(topic, topic, msg);
            producer.send(keyedMessage);
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        producer.close();
        inited = false;
    }

    public static void init(String brokerList){
        if(!inited) {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", brokerList);
            props.setProperty("key.serializer", StringSerializer.class.getName());
            props.setProperty("value.serializer", ByteArraySerializer.class.getName());
            props.setProperty("partitioner.class", OrderedPartitioner.class.getName());
            props.put("batch.size", 16384);//16M
            props.put("linger.ms", 10);
            props.put("buffer.memory", 33554432);//32M
            producer = new KafkaProducer<String, byte[]>(props);
            inited = true;
        }
    }
}
