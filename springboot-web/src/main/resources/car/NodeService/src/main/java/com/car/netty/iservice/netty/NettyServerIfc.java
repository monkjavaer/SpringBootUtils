package com.car.netty.iservice.netty;

import com.car.netty.struct.base.InterProtocolFrame;


public interface NettyServerIfc
{
    public void run(InterProtocolFrame oInterProtocolFrame);
}
