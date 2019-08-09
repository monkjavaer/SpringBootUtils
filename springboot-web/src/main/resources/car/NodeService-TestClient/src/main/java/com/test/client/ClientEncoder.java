package com.test.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 自定义编码器
 * @author monkjavaer
 * @date 2019/7/15 11:19
 */
public class ClientEncoder extends MessageToByteEncoder<UnvProtocol> {

    private static Logger logger = LoggerFactory.getLogger(ClientEncoder.class);
    /**
     * 包头                 数据包长度             版本号    Data内容       包尾
     * +------------+----------------------------+---------+----------+------------+
     * 0x77aa77aa     版本号+命令码+Data内容的长度   版本号    Data内容    0x77ab77ab
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, UnvProtocol msg, ByteBuf out) throws Exception {
        if (msg == null) {
            throw new Exception("msg is null");
        }

        try {
            String body = msg.getStrXml();
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
            ArrayList<byte[]> bImageData = msg.getoArrayList();
            byte[] pics = bImageData.get(0);

            //1-4,包头
            out.writeInt(ClientConstant.UNV_HEADER);
            //4-8,包长=版本号+命令码+xml长度+xml+图片数量+图片大小+图片
            out.writeInt(4 + 4 + 4 + bodyBytes.length + 4 + 4 + pics.length );
            //8-12,版本号
            out.writeInt(msg.getiVersion());
            //12-16，命令码
            out.writeInt(211);

            //xml 长度
            out.writeInt(bodyBytes.length);
            //xml
            out.writeBytes(bodyBytes);

            //图片数量
            out.writeInt(1);
            //图片大小
            out.writeInt(pics.length);
            out.writeBytes(pics);
            out.writeInt(ClientConstant.UNV_LAST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("编码异常！");
        }

    }


}
