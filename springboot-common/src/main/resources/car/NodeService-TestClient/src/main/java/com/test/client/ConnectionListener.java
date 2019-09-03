import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 断线重连监听
 *
 * @author monkjavaer
 * @date 2019/7/15 16:47
 */
public class ConnectionListener implements ChannelFutureListener {
    private static Logger logger = LoggerFactory.getLogger(ConnectionListener.class);

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            final EventLoop loop = future.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    logger.info("服务端链接不上，开始重连操作...");
                    try {
                        TestClient.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5, TimeUnit.SECONDS);
        } else {
            logger.error("服务端链接成功...");
        }
    }
}
