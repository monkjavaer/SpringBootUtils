package com.car.base.common.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description: 图片与Base64编码互相转换
 * Original Author: monkjavaer, 2017/12/29
 */
public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.info(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            logger.info(e.getLocalizedMessage());
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info(e.getLocalizedMessage());
                }
            }
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @return
     * @Description: 根据图片URL地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStrFromUrl(String imageUrl){

        InputStream inputStream = null;
        byte[] data = null;
        byte[] buffer = new byte[1024];
        int len = 0;

        try {

            URL urlfile = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) urlfile.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inStream.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
            bos.close();
            data = bos.toByteArray();
//            System.out.println(data.length);

//            System.out.println(conn.getContentLength());
//            data = new byte[conn.getContentLength()];
//            inStream.read(data,0,conn.getContentLength());
//            inStream.close();


//            inputStream = new FileInputStream(imageUrl);
//            int dateLength = inputStream.available();
//            System.out.println(dateLength);
//            data = new byte[dateLength];
//
//            inputStream.read(data);

        } catch (IOException e) {
            logger.info(e.getLocalizedMessage());
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info(e.getLocalizedMessage());
                }
            }
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
