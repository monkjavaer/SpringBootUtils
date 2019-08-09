package com.car.netty.service.unv.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import com.car.netty.enums.UnvEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Scope("prototype")
@Component
public class SendUnvCallBack {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SendUnvCallBack.class);

    /**
     * 发送宇视协议回包报文
     * @param oMap
     * @param ctx
     * @param iBackCmdType
     * @param iCmd
     * @param strError
     */
    public void sendBack(HashMap<String,String> oMap, ChannelHandlerContext ctx, int iBackCmdType, int iCmd, int strError) throws Exception
    {
        Channel che = ctx.channel();
        if (!che.isWritable())
        {
            LOGGER.error(UnvEnum.UNV_ERROR + "无法写入TCP发送缓存 。");
            return;
        }

        //组装宇视协议回包
        byte[] bBackByte = CreateUnvProtocol.unvResponseInfo(oMap, iBackCmdType, iCmd, strError);
        if(null == bBackByte)
        {
         LOGGER.error(UnvEnum.UNV_ERROR + "bBackByte返回协议包为空，不合法。");
         return;
        }

        //使用内存池内存
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.heapBuffer(bBackByte.length);
        buffer.writeBytes(bBackByte);
        ctx.write(buffer);
        ctx.flush();
        LOGGER.debug(UnvEnum.UNV_DEBUG + "回包ID:"+ oMap.get("RecordID"));
    }


    /**
     * 发送心跳回包
     * @param strCode
     * @param ctx
     * @param iCmdType
     */
    public void sendBackHeart(String strCode, ChannelHandlerContext ctx, int iCmdType, int iPackgeLen) throws Exception
    {
        // 返回完整的心跳报文
        byte[] returnByte = CreateUnvProtocol.getHeartBeat(strCode, iCmdType, iPackgeLen);
        ByteBuf buffer  = null;
        if (returnByte != null)
        {
            //使用内存池内存，使用必须释放
            buffer = PooledByteBufAllocator.DEFAULT.heapBuffer(returnByte.length);
            buffer.writeBytes(returnByte);
            ctx.write(buffer);
            ctx.flush();
        }
    }


}
