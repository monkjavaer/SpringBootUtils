import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 测试客户端
 * @author monkjavaer
 * @date 2019/7/15 11:00
 */
public class TestClient {

    /**
     * 连接方法
     */
    public static void connect(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ClientEncoder());
                            p.addLast(new ClientHandler());
                        }
                    });
            // Make the connection attempt.
            ChannelFuture f = bootstrap.connect(ClientConstant.IP, ClientConstant.PORT);
            f.addListener(new ConnectionListener());
            f.channel().closeFuture().sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //需要大于重连时间间隔5秒
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            group.shutdownGracefully();
        }
    }

    /**程序入口*/
    public static void main(String[] args) {
        connect();
    }
}
