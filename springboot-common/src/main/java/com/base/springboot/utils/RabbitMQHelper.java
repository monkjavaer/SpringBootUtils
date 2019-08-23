package com.base.springboot.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @Title: RabbitMQHelper
 * @Package: com.base.springboot.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Date: 2019/8/23 14:57
 * @Version: V1.0
 */
public class RabbitMQHelper {
    /**日志*/
    private static Logger logger = LoggerFactory.getLogger(RabbitMQHelper.class);

    private static volatile Connection connection = null;

    private static ConnectionFactory connectionFactory = null;

    private static int maxThread = 1;

    private static ExecutorService executorService = new ThreadPoolExecutor(maxThread,maxThread,30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(maxThread, true),
            new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * @Description 初始化
     * @Author monkjavaer
     * @Date 2018/1/2 15:49
     */
    private static Connection init() throws IOException, TimeoutException {

        if(connection !=null){
            return connection;
        }

        if(connectionFactory==null){
            String mqServerIP = "rabbitMQ.host";
            String mqServerPort = "rabbitMQ.port";
            String mqServerUserName = "rabbitMQ.userName";
            String mqServerPassword = "rabbitMQ.password";
            int serverPort = StringUtils.isEmpty(mqServerPort) ? 5672 : Integer.valueOf(mqServerPort);

            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(mqServerIP);
            connectionFactory.setPort(serverPort);
            connectionFactory.setUsername(mqServerUserName);
            connectionFactory.setPassword(mqServerPassword);
        }

        connection = connectionFactory.newConnection();
        executorService.execute(new HeartThread());


        logger.warn("create rabbit mq is success !");
        return connection;
    }


    private static void init(String id, Integer port, String userName, String password) throws IOException, TimeoutException {

        if(connection==null){

            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(id);
            connectionFactory.setPort(port);
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connection = connectionFactory.newConnection();
        }
    }


    /**
     * @Description 重连
     * @Author monkjavaer
     * @Date 2018/1/2 15:48
     */
    public static void reConnect() {
        try {

            connection = connectionFactory.newConnection();

            logger.warn("reconnect rabbit mq is success !");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }




    /**
     * @Description 获得一个新的channel
     * @Author monkjavaer
     * @Date 2017/12/6 16:52
     */
    public static Channel newChannel(String id, Integer port, String userName, String password)  {
        try {
            init(id, port, userName, password);
            return connection.createChannel();
        } catch (IOException e) {
            logger.error("connect is failed: "+e.getMessage(), e);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Channel newChannel()  {

        while(true){
            try {
                init();
                return connection.createChannel();
            } catch (Exception e) {
                logger.error("create rabbit connect is failed: ", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {}
            }
        }

    }



    /**
     * @Description 设置路由
     * @Author monkjavaer
     * @Date 2017/12/6 16:55
     */
    public static void setExchange(Channel channel, String exchangeName, String type, Boolean durable) throws IOException {
        channel.exchangeDeclare(exchangeName, type, durable);
    }



    /**
     * @Description 设置队列
     * @Author monkjavaer
     * @Date 2017/12/6 16:55
     */
    public static void setQueue(Channel channel, String queueName, Boolean durable,  Boolean autoDelete, Boolean exclusive) throws IOException {
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
    }



    /**
     * @Description 设置路由和队列的绑定
     * @Author monkjavaer
     * @Date 2017/12/6 16:55
     */
    public static void queueBind(Channel channel, String queueName, String exchangeName,  String routingKey) throws IOException {
        channel.queueBind(queueName, exchangeName, routingKey);
    }


    /**
     * @Description 设置路由、队列以及绑定关系和消费逻辑
     * @Author monkjavaer
     * @Date 2017/12/6 16:59
     */
    public static boolean setChannelProperties(Channel channel, String exchangeName, String exType, Boolean exDurable,  String queueName, Boolean quDurable, Boolean quAutoDelete, Boolean quExclusive, String routingKey) {
        try {
            setExchange(channel, exchangeName, exType, exDurable);
        } catch (IOException e) {
            logger.error("setExchange is failed :" + e.getMessage(), e);
            return false;
        }
        try {
            setQueue(channel, queueName, quDurable, quAutoDelete, quExclusive);
        } catch (IOException e) {
            logger.error("setQueue is failed :" + e.getMessage(), e);
            return false;
        }
        try {
            queueBind(channel, queueName, exchangeName, routingKey);
        } catch (IOException e) {
            logger.error("queueBind is failed :" + e.getMessage(), e);
            return false;
        }

        return true;
    }



    /**
     * @Description 创建消费者
     * @Author monkjavaer
     * @Date 2017/12/6 17:10
     */
    public static QueueingConsumer newConsumer(Channel channel, String queueName, Boolean ack) {

        try {
            //消息分发设置
//            channel.basicQos(0, 1, false);

            QueueingConsumer consumer = new QueueingConsumer(channel);

            channel.basicConsume(queueName, ack, consumer);

            return consumer;
        } catch (IOException e) {
            logger.error("create Consumer is failed :" + e.getMessage(), e);
            return null;
        }

    }


    /**
     * @Description 发送消息
     * @Author monkjavaer
     * @Date 2017/12/6 17:20
     */
    public static boolean publish(String exchangeName, String routingKey, byte[] messages){

        int i = 0; //尝试次数

        Channel channel = null;
        while(true){

            i ++;

            if(i >= 5){
                logger.error("xxxxx");
            }

            channel = newChannel();

            try {
                channel.basicPublish(exchangeName, routingKey, null, messages);
                channel.close();
                return true;
            } catch (Exception e) {
                logger.error("sendMessage is error:" + e.getMessage(), e);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {e1.printStackTrace();}

            }
        }

    }




    /*************************************************************************************************************************************
     ** @Description 关闭rabbitmq连接
     ** @Author monkjavaer
     ** @Date 2018/3/20 14:04
     **
     ************************************************************************************************************************************/
    public static void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                logger.error("close connection is failed!:",e);
            }
        }
    }



    /*************************************************************************************************************************************
     ** @Description 连接心跳监听
     ** @Author monkjavaer
     ** @Date 2018/3/22 14:50
     **
     ************************************************************************************************************************************/
    static class HeartThread extends Thread{
        @Override
        public void run(){

            while(true){

                try{
                    Thread.sleep(5000);

                    if(connection !=null){
                        if(connection.isOpen()){

                            continue;
                        }
                    }

                    reConnect();
                }catch (Exception e){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {}

                    reConnect();
                }


            }

        }
    }
}
