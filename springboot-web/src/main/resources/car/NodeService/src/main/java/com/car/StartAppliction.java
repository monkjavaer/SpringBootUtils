package com.car;

import com.car.netty.action.UnvServerAction;
import com.car.netty.struct.base.InterProtocolFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * web项目启动时执行，建立连接。
 * @author monkjavaer
 * @date 2018/5/1814:13
 */
@Service
public class StartAppliction{


    @Autowired
    private UnvServerAction unvServerAction;

    /**
     * 获取TMS服务器过车入口
     * @throws Exception
     */
    public void getStart() throws Exception {
        InterProtocolFrame interProtocolFrame = new InterProtocolFrame();
        interProtocolFrame.setstrEnableStatus("true");
        unvServerAction.startTrans(interProtocolFrame);
    }
}
