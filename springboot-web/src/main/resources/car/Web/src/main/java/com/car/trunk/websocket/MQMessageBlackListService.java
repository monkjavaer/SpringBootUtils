package com.car.trunk.websocket;


import com.car.base.common.constant.EnvConstant;
import com.car.base.message.AlarmMsg;
import com.car.base.message.MQMessage;
import com.car.base.message.MessageType;
import com.car.base.rabbit.MQReceiver;
import com.car.trunk.center.passedbyVehicle.service.PassedbyVehicleService;
import com.car.trunk.center.system.service.IUserCityAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Observable;
import java.util.Observer;

/**
 * Description: 黑名单报警消息推送类
 * Original Author: monkjavaer, 2017/12/15
 */
@Component
public class MQMessageBlackListService implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(MQMessageBlackListService.class);

    /**
     * 系统用户城市区域绑定服务接口
     */
    @Autowired
    private IUserCityAreaService iUserCityAreaService;

    /**
     * 过车记录接口
     */
    @Autowired
    private PassedbyVehicleService passedbyVehicleService;

    /**
     * Webscoket发送消息服务
     */
    @Autowired
    private WebscoketManager webscoketManager;

    public MQMessageBlackListService() {
        MQReceiver.listen(this, EnvConstant.PASSDETEC_WEB_QUEUE_NAME);
    }

    @Override
    public void update(Observable o, Object arg) {
        MQMessage message = (MQMessage) arg;
        MQMessage sendpassMessage = new MQMessage();
        if (MessageType.VEHICLE_DETECTION.equals(message.getMessageType())) {
            AlarmMsg alarmMsg = (AlarmMsg) message.getBody();
            sendpassMessage.setBody(alarmMsg);
            sendpassMessage.setMessageType(MessageType.VEHICLE_DETECTION);
            webscoketManager.sendMessageToUsers(sendpassMessage);
        }


    }
}
