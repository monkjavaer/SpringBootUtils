package com.car.netty.service.utils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.car.netty.iservice.netty.NettyChannelIfc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Component
public class TcpServer
{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

    /**
     * 启动服务
     */
    public void run(NettyChannelIfc channelInit, int iPort)
    {
        ChannelHandler channel = channelInit.getChannelInit();
        if(null == channel)
        {
            LOGGER.error("异常信息:传入的channel为空。");
            return;
        }

        // 创建IO调配组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创建执行工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    // 传入的处理通道
                    .childHandler(channel)
                    // 设置TCP连接数
                    .option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_RCVBUF, 1024 * 64)
                    .option(ChannelOption.SO_SNDBUF, 1024 * 64)
                    .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024)
                    .option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 32 * 1024)
                    //Boss线程内存池配置.
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //Work线程内存池配置.
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    // TCP保持心跳长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind andstart to accept incoming connections.
            ChannelFuture futureServer = b.bind(iPort).sync();

            System.out.println("TCP服务启动成功。");
            // Wait untilthe server socket is closed.
            // In this example,this does not happen, but you can do that to
            // gracefully
            // shut downyour server.
            // 挂起服务
            futureServer.channel().closeFuture().sync();
        }
        catch (Exception e)
        {
            LOGGER.error("异常信息:" + e.getMessage(), e);
        }
        finally
        {
            // 优雅关闭
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
