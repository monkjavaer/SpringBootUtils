package com.car.netty.service.unv.serverhandlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.car.netty.enums.ErrorCode;
import com.car.netty.enums.UnvEnum;
import com.car.netty.iservice.protocol.AnalyzeProtocolIfc;
import com.car.netty.iservice.protocol.AnalyzeXmlIfc;
import com.car.netty.service.unv.UnvBlockQueue;
import com.car.netty.service.unv.protocol.SendUnvCallBack;
import com.car.netty.struct.unv.bean.UnvProtocol;
import com.car.netty.utils.BytesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


@Scope("prototype")
@Sharable
@Service
public class UnvVehicleServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UnvVehicleServerHandler.class);
    /**
     * 注入协议解析接口
     */
    @Resource
    private AnalyzeProtocolIfc analyzeUnvProtocol;
    /**
     * 注入队列
     */
    @Resource
    private UnvBlockQueue unvBlockQueue;
    /**
     * 注入XML解析接口
     */
    @Resource
    private AnalyzeXmlIfc analyzeVehicleXML;

    /**
     * 宇视协议回包类
     */
    @Resource
    private SendUnvCallBack sendUnvCallBack;

    /**
     * 覆盖继承ChannelInboundHandlerAdapter的方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        try {
            // 是否回包
            boolean isBack = true;
            // 是否过车数据
            boolean isVehicle = true;
            // 是否插入队列成功
            boolean isInsert = true;
            // 回包命令码
            int iBackCmdType = 0;
            // 解析XML获取的Map对象
            HashMap<String, String> oMap = null;
            // 创建获取字节的大小对象
            byte[] bReq = new byte[buf.readableBytes()];
            // 获得缓冲区可读的字节数
            buf.readBytes(bReq);
            // 查找宇视报文头位置
            int msgEndNum = BytesUtils.lastIndexOf(bReq, BytesUtils.inttobytes(UnvEnum.UNV_HEADER));

            if (-1 != msgEndNum) {
                // 解析宇视报文
                UnvProtocol oUpl = analyzeUnvProtocol.analyze(bReq);
                if (null == oUpl) {
                    LOGGER.error("异常信息:返回的协议对象为空。");
                    return;
                }
                //根据命令码判断处理方式
                int iCmd = oUpl.getiCmdType();
                switch (iCmd) {
                    // 域内命令码有应答
                    case 115:
                    case 116:
                        iBackCmdType = 511;
                        break;
                    case 118:
                    case 119:
                        iBackCmdType = 512;
                        break;
                    // 域间命令码有应答
                    case 211:
                    case 212:
                        iBackCmdType = 611;
                        break;
                    // 域间命令码无应答
                    case 213:
                    case 214:
                        isBack = false;
                        break;
                    // 过车更新命令码有应答
                    case 151:
                        // 违法更新命令码有应答
                    case 152:
                        iBackCmdType = 581;
                        break;
                    // 保活消息
                    case 101:
                        // 根据报文获取心跳编码
                        String strCode = oUpl.getStrCode();
                        // 发送心跳报文
                        sendUnvCallBack.sendBackHeart(strCode, ctx, 501, oUpl.getiPackageLen());
                        isBack = false;
                        break;
                    default:
                        LOGGER.debug("调试信息: 不是过车数据。命令码为：" + iCmd);
                        //重置读取缓存指针
                        buf.resetReaderIndex();
                        //透传到后面的handler,执行ChannelRead事件
                        ctx.fireChannelRead(msg);
                        isVehicle = false;
                        isInsert = true;
                        isBack = false;
                        break;
                }

                if (101 != iCmd && isVehicle) {
                    //写入回报文的应答命令码
                    oUpl.setiBackCmdType(iBackCmdType);
                    //存入TCP通道给后续回报文使用
                    oUpl.setCtx(ctx);
                    //过车数据
                    oUpl.setiPackageType(1);
                    //数据写入队列
                    if (!unvBlockQueue.getoBlockQueue().offer(oUpl)) {
                        isInsert = false;
                    }
                }

                // 表示插入队列失败才解析XML
                if (!isInsert) {
                    oMap = analyzeVehicleXML.getXmlData(oUpl.getStrXml());
                }

                //注意：只有插入失败的数据才回包（并且需要命令码支持回包）
                //插入队列成功的数据待写入数据库后各自线程回包
                if (true == isBack && false == isInsert) {
                    oUpl.setiErrorCode(ErrorCode.QUE_INSERT_FAIL);
                    //宇视协议返回报文(先随便给个错误码)
                    sendUnvCallBack.sendBack(oMap, ctx, iBackCmdType, iCmd, oUpl.getiErrorCode());
                    LOGGER.debug(UnvEnum.UNV_DEBUG + "消费队列插入失败。" + "记录编号:" + oMap.get("RecordID") + "车牌号码:" + oMap.get("CarPlate") + "过车时间:" + oMap.get("PassTime"));
                } else {
                    LOGGER.debug(UnvEnum.UNV_DEBUG + "收到包命令码:" + iCmd);
                }
            } else {
                LOGGER.error(UnvEnum.UNV_ERROR + "非宇视报文,丢弃。");
            }
        } catch (Exception e) {
            System.out.println("===========接收数据异常=============");
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        } finally {
            //这内存必须释放
            if (1 == buf.refCnt()) {
                buf.release();
            }
        }
    }

    /**
     * 连接异常
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close theconnection when an exception is raised.
        LOGGER.error(UnvEnum.UNV_ERROR + cause.getMessage(), cause);
        // 关闭连接
        ctx.close();
    }
}
