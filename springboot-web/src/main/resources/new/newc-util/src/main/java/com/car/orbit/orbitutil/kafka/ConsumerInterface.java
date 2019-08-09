package com.car.orbit.orbitutil.kafka;

import org.apache.kafka.common.protocol.types.Field;

/**
 * CreateDate: 2019-4-1 <br/>
 * Author: monkjavaer <br/>
 * Description: kafka消费者处理函数接口
 * Version: 1.0
**/
public interface ConsumerInterface {


   /**
    * @description 描述一下方法的作用
    * @date: 2019-4-1 12:04
    * @author: monkjavaer
    * @param msg,消费者接收到的来自生产者的String数据
    * @return
    */
    void consumer(String msg);

}
