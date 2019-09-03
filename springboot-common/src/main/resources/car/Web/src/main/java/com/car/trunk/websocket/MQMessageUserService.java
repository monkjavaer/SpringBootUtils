package com.base.springboot.car.Web.src.main.java.com.car.trunk.websocket;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.constant.EnvConstant;
import com.base.springboot.car.Base.src.main.java.com.car.base.message.MQMessage;
import com.base.springboot.car.Base.src.main.java.com.car.base.rabbit.MQFanoutReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

/**
 * CreateDate：2018/8/6
 * Author：monkjavaer
 * Description: 系统账号操作rabbitmq消息监听服务类
 **/

@Service("MQMessageUserService")
public class MQMessageUserService implements Observer {

    private static final Logger log = LoggerFactory.getLogger(MQMessageUserService.class);


    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebscoketManager webscoketManager;


    public MQMessageUserService() {
        MQFanoutReceiver.listen(this, EnvConstant.SYSTEM_WEB_QUEUE_NAME);
    }

    @Override
    public void update(Observable o, Object arg) {
        MQMessage message = (MQMessage) arg;
        webscoketManager.sendMessageToWebUser(message);
    }
}
