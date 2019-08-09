package com.car.base.rabbit;

import com.car.base.common.constant.EnvConstant;

import java.io.IOException;

/**
 * Description:
 * Created by liych on 2018/6/27
 *
 * @Author monkjavaer
 * @Right Car
 */
public class QueueUtil {

    private static MQSender nodePassMqSender;
    private static MQSender passDetecMqSender;
    private static MQSender passDetecWebMqSender;
    private static MQFanoutSender systemWebMqSender;

    public static MQSender getNodePassMqSender() throws IOException {
        if (nodePassMqSender == null) {
            nodePassMqSender = new MQSender(EnvConstant.NODE_PASS_QUEUE_NAME);
        }
        return nodePassMqSender;
    }

    public static MQSender getPassDetecMqSender() throws IOException {
        if (passDetecMqSender == null) {
            passDetecMqSender = new MQSender(EnvConstant.PASS_DETEC_QUEUE_NAME);
        }
        return passDetecMqSender;
    }
    public static MQSender getPassDetecWebMqSender() throws IOException{
        if (passDetecWebMqSender == null){
            passDetecWebMqSender = new MQSender(EnvConstant.PASSDETEC_WEB_QUEUE_NAME);
        }
        return passDetecWebMqSender;
    }

    public static MQFanoutSender getSystemWebMqSender() throws IOException {
        if (systemWebMqSender == null) {
            systemWebMqSender = new MQFanoutSender(EnvConstant.SYSTEM_WEB_QUEUE_NAME);
        }
        return systemWebMqSender;
    }

}
