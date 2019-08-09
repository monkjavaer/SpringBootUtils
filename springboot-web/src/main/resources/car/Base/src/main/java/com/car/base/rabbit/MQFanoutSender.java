/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/5/8
 */
package com.car.base.rabbit;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.utilities.PropertiesUtil;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * CreateDate：2018/8/6
 * Author：monkjavaer
 * Description: 广播模式消息发送类
 **/
public class MQFanoutSender  {

    private static Logger logger = LoggerFactory.getLogger(MQFanoutSender.class);
    private Channel channel;
    private Connection connection;
    private String fanoutName;
    private ConnectionFactory factory;

    public MQFanoutSender(String fanoutName) {
        this.fanoutName = fanoutName;
            //Create a connection factory
            factory = new ConnectionFactory();
            factory.setHost(EnvConstant.HOST);
            factory.setPort(EnvConstant.MESSAGE_QUEUE_PORT);
            factory.setUsername(EnvConstant.MESSAGE_QUEUE_USERNAME);
            factory.setPassword(EnvConstant.MESSAGE_QUEUE_PASSWORD);
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            logger.error("MQFanoutSender Connect RabbitMQ failed ! error message is: {}" , e.getMessage());
            //重连
            connection = MQCommonUtils.reConnect(factory);
        }
        try {
            channel = connection.createChannel();
            // 声明该channel是fanout类型
            channel.exchangeDeclare(fanoutName, "fanout");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("MQFanoutSender create Channel failed ! error message is: {}" , e.getMessage());
        }
    }

    public void sendMessage(Serializable object) throws IOException {
        try {
            if (channel != null) {
                // 将消息发送给exchange
                channel.basicPublish(fanoutName, "", null, SerializationUtils.serialize(object));
            }
        } catch (AlreadyClosedException e) {
            logger.error("MQFanoutSender already closed ! error message is: {}" , e.getMessage());
            //关闭资源
            MQCommonUtils.close(connection,channel);
            //重连
            connection = MQCommonUtils.reConnect(factory);
            //重新创建channel
            channel = connection.createChannel();
            // 声明该channel是fanout类型
            channel.exchangeDeclare(fanoutName, "fanout");
            //递归调用重发数据
            sendMessage(object);
        }
    }
}
