package com.car.netty.iaction;

import com.car.netty.struct.base.InterProtocolFrame;

public interface ActionTransIfc
{
    /**
     * 所有对接都要实现该接口,对接启动入口
     *
     * @author z00562 2016年4月6日
     */
    public void startTrans(InterProtocolFrame oInterProtocolFrame);
  
}
