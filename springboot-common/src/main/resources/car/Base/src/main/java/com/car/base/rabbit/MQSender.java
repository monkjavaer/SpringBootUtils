/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/5/8
 */
package com.base.springboot.car.Base.src.main.java.com.car.base.rabbit;

import com.car.base.common.constant.EnvConstant;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class MQSender{
    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    private Channel channel;
    private Connection connection;
    private String queueName;
    private ConnectionFactory factory;

    public MQSender(String queueName) {
        this.queueName = queueName;
        //Create a connection factory
        factory = new ConnectionFactory();
        //hostname of your rabbitmq server
        factory.setHost(EnvConstant.HOST);
        factory.setPort(EnvConstant.MESSAGE_QUEUE_PORT);
        factory.setUsername(EnvConstant.MESSAGE_QUEUE_USERNAME);
        factory.setPassword(EnvConstant.MESSAGE_QUEUE_PASSWORD);
//        factory.setRequestedHeartbeat(20);

        try {
            //getting a connection
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            logger.error("MQSender Connect RabbitMQ failed ! error message is: {}" , e.getMessage());
            //重连
            connection = MQCommonUtils.reConnect(factory);
        }

        try {
            //creating a channel
            channel = connection.createChannel();
            //declaring a queue for this channel. If queue does not exist,
            //it will be created on the server.
            channel.queueDeclare(queueName, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("MQSender createChannel or queueDeclare failed ! error message is: {}" , e.getMessage());
        }
    }


    public void sendMessage(Serializable object) throws IOException {
        try {
            if (channel != null) {
                channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));
            }
        } catch (AlreadyClosedException e) {
            logger.error("RabbitMQ already closed ! error message is: {}" , e.getMessage());
            //关闭资源
            MQCommonUtils.close(connection,channel);
            //重连
            connection = MQCommonUtils.reConnect(factory);
            //重新创建channel
            channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            //递归调用重发数据
            sendMessage(object);
        }
    }

}
