package com.car.orbit.orbitservice.test;

import com.alibaba.fastjson.JSON;
import com.car.orbit.orbitservice.constant.KafkaConstant;
import com.car.orbit.orbitservice.kafka.AlarmConsumerService;
import com.car.orbit.orbitutil.kafka.ConsumerInterface;
import com.car.orbit.orbitutil.kafka.KafkaConsumerUtils;
import com.car.orbit.orbitutil.kafka.TestConsumer;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import io.micrometer.core.instrument.util.TimeUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2018/1/23.
 */
public class TestProducer {

    public static class TestConsumer implements ConsumerInterface {
        /**
         * @param msg
         * @return
         * @description 描述一下方法的作用
         * @date: 2019-4-1 12:04
         * @author: monkjavaer
         */
        @Override
        public void consumer(String msg) {
            System.out.println("get:" + msg);
        }
    }

    public static void main(String[] args) {
        BlacklistAlarm blacklistAlarm = new BlacklistAlarm(UUIDUtils.generate(),"","4c6a97e66cac8d7ba06996d74a73829a","FBF460F09BD94CDFB6A8A80BE9739AEF","tang123456", DateUtils.format(new Date()),"172.16.3.31:8888/group1/M00/00/F0/rBADH1y-yWeAQN16AAF5BxgEusQ850.jpg" ,"CAFB3CBD42B4442EA675BB700B4E34E9",null,null,DateUtils.format(new Date()) ,"E62C651C91A544919B72C317E84537A0" ,"B82368E24457489A80CA66E6D2C6F257","B177AD0AE3E7402983E56CAB0A9430D6" ,"10" ,"3","0003","00030005");
        String warningStr = JSON.toJSONString(blacklistAlarm);
        KafkaUtil kafkaUtil = new KafkaUtil("blacklist_alarm2" );
        kafkaUtil.sendMessage(null, warningStr);

//        String[] topics = new String[]{"blacklist_alarm1"};
//        String brokerList = "172.16.3.31:6667";
//        KafkaConsumerUtils.consumer("alarmConsumer2", Arrays.asList(topics), brokerList, new TestConsumer());

    }



}
