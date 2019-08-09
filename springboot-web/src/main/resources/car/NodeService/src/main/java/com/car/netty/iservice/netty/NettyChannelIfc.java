package com.car.netty.iservice.netty;

import io.netty.channel.ChannelHandler;


public interface NettyChannelIfc {
    public ChannelHandler getChannelInit();
}
