package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.server;

import com.base.springboot.car.NodeService.src.main.java.com.car.netty.iservice.netty.NettyChannelIfc;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.iservice.netty.NettyServerIfc;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.utils.TcpServer;
import com.car.netty.struct.base.InterProtocolFrame;
import com.car.netty.struct.unv.config.UnvServerConfig;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


@Service
public class UnvService implements NettyServerIfc
{
    /**
     * 实际处理Handler
     */
    @Resource
    private NettyChannelIfc channelInit;

    /**
     * TCP服务端
     */
    @Resource
    private TcpServer tcpServer;

    /**
     * 配置文件
     */
    @Resource
    private UnvServerConfig unvServerConfig;

    ////多例需要通过bean方式获取保证不同的线程使用不同的对象
    @Autowired
    private UnvThread unvThread;

    
    private InterProtocolFrame oInterProtocolFrame;
    public InterProtocolFrame getoInterProtocolFrame() {
        return oInterProtocolFrame;
    }
    public void setoInterProtocolFrame(InterProtocolFrame oInterProtocolFrame) {
        this.oInterProtocolFrame = oInterProtocolFrame;
    }

    /**
     * 20个处理线程
     */
    private int iThreads = 5;

    @Override
    public void run(InterProtocolFrame oInterProtocolFrame)
    {
        // 启动多线程处理队列数据
        //ExecutorService oThreadExecutor = Executors.newFixedThreadPool(iThreads);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(iThreads,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        for(int i=0; i<iThreads; i++)
        {
            //多例需要通过bean方式获取保证不同的线程使用不同的对象
            //UnvThread unvThread = (UnvThread)SpringContextUtils.getBean("unvThread");
            executorService.execute(unvThread);
        }
        // 关闭启动线程池
        executorService.shutdown();

        // 收包获取数据并写入队列（netty都是默认配置）
        tcpServer.run(channelInit, unvServerConfig.getiPort());

    }
}
