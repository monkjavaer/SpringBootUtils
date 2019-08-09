package com.car.netty.decode;

import com.car.netty.client.ClientNettyHandler;
import com.google.protobuf.MessageLite;
import edgenodeservice.CarPlateProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 解码器
 * @author monkjavaer
 * @date 2019/01/01 17:54
 */
@Service
public class CarProtobufDecoder extends ByteToMessageDecoder {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ClientNettyHandler.class);

    /**
     *
     * 解析启动
     *
     * 数据协议
     * --------------------------------------
     *包长度(12+576127)         protobuf数据
     *---------------------------------------
     * pktlen | cmd | reserve |CarPlateInfo |
     *   4    |  4  |    4    |  576127     |
     *---------------------------------------
     * @param ctx
     * @param buffer
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {

            // 标记当前的readerIndex
            buffer.markReaderIndex();
            //读取前四位字节(pktlen)并返回int类型数据
            int len = buffer.readInt();
            //大端转小端(java默认是大端)
            byte[] temp = intToMinByteArray(len);
            //数据包长度
            int length = byteArrayToInt(temp);
            //读取前四位字节(cmd)
            buffer.readInt();
            //读取前四位字节(reserve)
            buffer.readInt();
            // 如果可读长度小于body长度，恢复读指针，退出。
            if (buffer.readableBytes() < length) {
                //重置readerIndex到标记markReaderIndex()位。
                buffer.resetReaderIndex();
                return;
            }

            //开始读取核心protobuf数据
            ByteBuf bodyByteBuf = buffer.readBytes(length-12);
            byte[] array;
            //反序列化数据的起始点
            int offset;
            //可读的数据字节长度
            int readableLen= bodyByteBuf.readableBytes();
            //分为包含数组数据和不包含数组数据两种形式
            if (bodyByteBuf.hasArray()) {
                array = bodyByteBuf.array();
                offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
            } else {
                array = new byte[readableLen];
                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
                offset = 0;
            }
            //避免内存泄漏
            bodyByteBuf.release();
            //反序列化
            MessageLite result = decodeBody(array, offset, readableLen);
            out.add(result);

    }

    /**
     * 反序列化protobuf数据
     * @param array
     * @param offset
     * @param length
     * @return
     * @throws Exception
     */
    public MessageLite decodeBody(byte[] array, int offset, int length) throws Exception {

        CarPlateProto.CarPlateInfo carPlateInfo = CarPlateProto.CarPlateInfo.getDefaultInstance()
                .getParserForType().parseFrom(array, offset, length);
        logger.info("==开始解析==");
        return carPlateInfo;
    }

    /**
     * 大端转小端(java默认是大端)
     * @param i
     * @return
     */
    public static byte[] intToMinByteArray(int i) {
        byte[] result = new byte[4];
        //由高位到低位
        result[3] = (byte) ((i >> 24) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 二进制byte[]转int
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b){
        return b[3]&0xFF | (b[2]&0xFF) << 8 | (b[1]&0xFF) << 16 | (b[0]&0xFF) << 24;
    }
}
