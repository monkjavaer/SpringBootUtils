package com.base.springboot.car.Base.src.main.java.com.car.base.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽取MQ发送端和接收端的公共部分
 *
 * @author monkjavaer
 * @date 2019/7/24 14:34
 */
public class MQCommonUtils {

    private static Logger logger = LoggerFactory.getLogger(MQCommonUtils.class);

    /** 避免重连创建多个连接 */
    private static Lock lock = new ReentrantLock();

    /**
     * 断线重连
     * @param factory ConnectionFactory
     * @return 返回 Connection
     */
    public  static Connection reConnect(ConnectionFactory factory) {
        while (true) {
            lock.lock();
            try {
                logger.info("Waiting...... Reconnect RabbitMQ in 5 seconds !");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Connection connection = null;
            try {
                connection = factory.newConnection();
                logger.info("Reconnect RabbitMQ successful !");
            } catch (IOException | TimeoutException e) {
                logger.error("Reconnect RabbitMQ failed ! error message is: {}", e.getMessage());
            }

            if (connection != null) {
                if (connection.isOpen()) {
                    return connection;
                }
            }
            lock.unlock();
        }
    }


    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @param connection
     * @param channel
     */
    public static void close(Connection connection , Channel channel) {
        try {
            if (channel != null) {
                channel.abort();
            }
            if (connection != null) {
                connection.abort();
            }
            channel = null;
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Close RabbitMQ connection and channel error ！{}", e.getMessage());
        }
    }

}
