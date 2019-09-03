package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.server;

import com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.UnvBlockQueue;
import com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv.serverhandlers.UnvVehicleService;
import com.car.netty.struct.unv.bean.UnvProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Scope("prototype")
@Service
public class UnvThread implements Runnable {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UnvThread.class);

    /**
     * 注入队列
     */
    @Resource
    private UnvBlockQueue unvBlockQueue;

    /**
     * 注入处理函数
     */
    @Resource
    private UnvVehicleService unvVehicleService;

    @Override
    public void run()
    {
        while (true)
        {
            try {
                LOGGER.debug("数据队列长度:" + unvBlockQueue.getoBlockQueue().size());
                // 获取对象
                UnvProtocol oUpl = unvBlockQueue.getoBlockQueue().take();
                if (null == oUpl) {
                    LOGGER.debug("异常信息: 队列数据为空。");
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }
                //过车（包含违法数据）消息处理
                unvVehicleService.stratDealData(oUpl);

            } catch (InterruptedException ie) {
                LOGGER.error("异常信息:" + ie.getMessage(), ie);
            } catch (Exception ie) {
                System.out.println("=====异常出现：" + ie.getMessage());
            }
        }
    }
}
