package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.server;

import com.car.netty.decode.UnvDecode;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.iservice.netty.NettyChannelIfc;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.serverhandlers.UnvVehicleServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Scope("prototype")
@Service
public class ChannelInit implements NettyChannelIfc
{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ChannelInit.class);

    /**
     * 最大报文长度 4M
     */
    private int iMaxLenth = 4096000;

    /**
     * 超时时间（90秒）
     */
    private int iTimeOut = 90;

    //过车报文处理函数,多实例实现
    @Autowired
    private UnvVehicleServerHandler unvVehicleServerHandler;


    /**
     * netty通道注册函数
     * @return ChannelHandler
     */
    @Override
    public ChannelHandler getChannelInit()
    {
        //使用Netty实现的线程池
        DefaultEventExecutorGroup executorGroup = new DefaultEventExecutorGroup(4, new DefaultThreadFactory("unv-server-thread"));
        //过车报文处理函数,多实例实现
       // UnvVehicleServerHandler unvVehicleServerHandler = (UnvVehicleServerHandler) SpringContextUtils.getBean("unvVehicleServerHandler");
        ChannelHandler oChannel = null;
        try
        {
             oChannel = new ChannelInitializer<SocketChannel>()
             {
                @Override
                public void initChannel(SocketChannel ch) throws Exception
                {
                    // 自定义解码器支持宇视协议
                    ch.pipeline().addLast("decoder", new UnvDecode(iMaxLenth, 4, 4, 0, 0));
                    // 超时时间
                    ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(iTimeOut));
                    // 添加过车的处理
                    ch.pipeline().addLast(executorGroup, "vehicleHandler", unvVehicleServerHandler);

                }
            };
        }
        catch (Exception e)
        {
            LOGGER.error("异常信息:" + e.getMessage(), e);
        }
        return oChannel;
    }
}
