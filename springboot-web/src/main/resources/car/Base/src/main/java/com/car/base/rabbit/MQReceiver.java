/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/10
 */
package com.car.base.rabbit;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.utilities.BlockQueue;
import com.car.base.message.MQMessage;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class MQReceiver extends Observable implements Runnable, Consumer {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    private String queueName;
    private Connection connection = null;
    private Channel channel = null;
    private ConnectionFactory factory = null;
    private static Map<String,MQReceiver> mQReceiverInstanceMap = new HashMap<>();

    private static ThreadPoolExecutor MQThreadPool = new ThreadPoolExecutor(BlockQueue.corePookSize, BlockQueue.maximumPoolSize,
            BlockQueue.keepActiveTime, BlockQueue.timeUnit, BlockQueue.taskServiceQueue, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("MQReceiver");
            return t;
        }
    });

    private MQReceiver() {}

    public static MQReceiver listen(Observer observer,String queueName) {
        MQReceiver instance = null;
        if (!mQReceiverInstanceMap.containsKey(queueName)){
            instance = new MQReceiver();
            instance.queueName = queueName;
            mQReceiverInstanceMap.put(queueName,instance);
        }else {
            instance = mQReceiverInstanceMap.get(queueName);
        }
        MQThreadPool.execute(instance);
        instance.addObserver(observer);
        return instance;

    }

    private void connect() {
        factory = new ConnectionFactory();
        factory.setHost(EnvConstant.HOST);
        factory.setPort(EnvConstant.MESSAGE_QUEUE_PORT);
        factory.setUsername(EnvConstant.MESSAGE_QUEUE_USERNAME);
        factory.setPassword(EnvConstant.MESSAGE_QUEUE_PASSWORD);
        //恢复连接
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(20);

        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            logger.error("MQReceiver Connect RabbitMQ failed ! error message is: {}" , e.getMessage());
            //重连
            connection = MQCommonUtils.reConnect(factory);
        }
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("MQReceiver create Channel failed ! error message is: {}" , e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            connect();
            channel.basicConsume(queueName, true, this);
            logger.info("MQReceiver configuration in MQReceiver succeeded. QUEUE NAME = " + EnvConstant.PASSDETEC_WEB_QUEUE_NAME + " Waiting for messages...");
        } catch (IOException e) {
            logger.error("MQReceiver basicConsume error ！{}" , e.getMessage());
        }
    }

    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

    @Override
    public void handleDelivery(String s,
            Envelope envelope,
            AMQP.BasicProperties basicProperties,
            byte[] bytes) throws IOException {

        //序列化
        Object object = SerializationUtils.deserialize(bytes);
        MQMessage message = (MQMessage) object;

        logger.info("MQReceiver received message type = " + message.getMessageType().prefix);
        logger.info("MQReceiver received and observers = " + countObservers());

        setChanged();
        notifyObservers(message);
    }
}
