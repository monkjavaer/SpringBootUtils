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
 * CreateDate：2018/8/6
 * Author：monkjavaer
 * Description: 广播模式消息接受类
 **/
public class MQFanoutReceiver extends Observable implements Runnable, Consumer {

    private static Logger logger = LoggerFactory.getLogger(MQFanoutReceiver.class);

    private String fanoutName;
    private Connection connection = null;
    private Channel channel = null;
    private ConnectionFactory factory = null;
    private static Map<String,MQFanoutReceiver> mQFanoutReceiverInstanceMap = new HashMap<>();

    private static ThreadPoolExecutor MQThreadPool = new ThreadPoolExecutor(BlockQueue.corePookSize, BlockQueue.maximumPoolSize,
            BlockQueue.keepActiveTime, BlockQueue.timeUnit, BlockQueue.taskServiceQueue, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("MQFanoutReceiver");
            return t;
        }
    });

    private MQFanoutReceiver() {}

    public static MQFanoutReceiver listen(Observer observer, String fanoutName) {
        MQFanoutReceiver instance = null;
        if (!mQFanoutReceiverInstanceMap.containsKey(fanoutName)){
            instance = new MQFanoutReceiver();
            instance.fanoutName = fanoutName;
            mQFanoutReceiverInstanceMap.put(fanoutName,instance);
        }else {
            instance = mQFanoutReceiverInstanceMap.get(fanoutName);
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
            // 创建connection
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("MQReceiver Connect RabbitMQ failed ! error message is: {}" , e.getMessage());
            //重连
            connection = MQCommonUtils.reConnect(factory);
        }
        try {
            // 创建channel
            channel = connection.createChannel();
            // 声明该channel是fanout类型
            channel.exchangeDeclare(fanoutName, "fanout");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("MQFanoutReceiver create Channel failed ! error message is: {}" , e.getMessage());
        }
    }

    @Override
    public void run() {

        try {
            connect();
            // 由RabbitMQ自行创建的临时队列,唯一且随消费者的中止而自动删除的队列
            String queueName = channel.queueDeclare().getQueue();
            // binding
            channel.queueBind(queueName, fanoutName, "");

            // 指定队列消费者
            channel.basicConsume(queueName, true, this);

            logger.info("MQ configuration in MQFanoutReceiver succeeded. Exchange NAME = " + fanoutName+ " Waiting for messages...");

        } catch (IOException ioEx) {
            logger.info("MQFanoutReceiver run error ! {}" , ioEx.getMessage());
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
