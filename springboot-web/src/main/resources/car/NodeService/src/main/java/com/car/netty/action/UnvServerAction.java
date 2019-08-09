package com.car.netty.action;

import com.car.netty.enums.UnvEnum;
import com.car.netty.iaction.ActionTransIfc;
import com.car.netty.service.unv.server.UnvService;
import com.car.netty.struct.base.InterProtocolFrame;
import com.car.netty.utils.MyThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnvServerAction implements ActionTransIfc, Runnable
{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UnvServerAction.class);

    /**
     * 宇视协议处理函数
     */
    @Autowired
    private UnvService unvService;

    /**
     * 宇视协议函数入口
     * @param oInterProtocolFrame
     */
    @Override
    public void startTrans(InterProtocolFrame oInterProtocolFrame)
    {
        unvService.setoInterProtocolFrame(oInterProtocolFrame);
        MyThreadFactory mt = new MyThreadFactory("unv-server");
        Thread newThread = mt.newThread(this);
        newThread.start();
    }

    /**
     * 启动服务
     * @param
     */
    @Override
    public void run()
    {
        LOGGER.info(UnvEnum.UNV_INFO + "启动unvService。");
        //挂起服务必须使用线程启动，否则会堵塞主线程的运行
        unvService.run(unvService.getoInterProtocolFrame());
    }
}
