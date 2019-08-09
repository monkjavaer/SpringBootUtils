package com.car.orbit.orbitutil.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CreateDate: 2019-4-1 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
public class test {


    public static void main(String[] args){
        String[] topics = new String[]{"blacklist_alarm1"};
        String brokerList = "172.16.3.31:6667";
        KafkaConsumerUtils.consumer("alarmConsumer1", Arrays.asList(topics), brokerList, new TestConsumer());
//        KafkaProducerUtils.init(brokerList);
//        KafkaProducerUtils.sendMsg("alarm_test", "wow");
//        KafkaProducerUtils.sendMsg("alarm_test", "wow");
//        KafkaProducerUtils.close();
    }
}
