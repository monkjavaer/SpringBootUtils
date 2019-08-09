package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitservice.exception.IllegalParamException;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * @Title: UrlUtil
 * @Package: com.car.orbit.orbitutil.tools
 * @Description: 获取图片链接地址的byte数据
 * @Author: monkjavaer
 * @Data: 2019/3/27 10:26
 * @Version: V1.0
 */
public class UrlUtil {

    /**
     * 获取图片链接地址的byte数据
     *
     * @param httpUrl http链接地址
     * @return byte[]数据
     * @throws Exception
     */
    public static byte[] getByteDataByURL(String httpUrl) throws IllegalParamException, IOException {
        if (StringUtils.isBlank(httpUrl)) {
            throw new IllegalParamException("http url is empty");
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outStream = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            httpURLConnection.setConnectTimeout(6000);
            inputStream = httpURLConnection.getInputStream();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }
        } finally {
            Optional.ofNullable(inputStream).ifPresent(i -> {
                try {
                    i.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Optional.ofNullable(outStream).ifPresent(o -> {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Optional.ofNullable(httpURLConnection).ifPresent(HttpURLConnection::disconnect);
        }
        return outStream.toByteArray();
    }

}
