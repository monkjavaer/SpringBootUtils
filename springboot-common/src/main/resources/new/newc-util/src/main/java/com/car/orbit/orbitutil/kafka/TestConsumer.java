package com.car.orbit.orbitutil.kafka;

/**
 * CreateDate: 2019-4-1 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
public class TestConsumer implements  ConsumerInterface {
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
