package com.base.springboot.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Title: DownloadUtils
 * @Package: com.base.springboot.utils
 * @Description: 下载图片，添加水印
 * @Author: monkjavaer
 * @Date: 2019/8/8 17:42
 * @Version: V1.0
 */
public class DownloadUtils {

    /**
     * 根据图片访问路径下载原始图片
     *
     * @param response HttpServletResponse
     * @param urlPath  图片访问路径
     */
    private void downloadFile(HttpServletResponse response, String urlPath) throws Exception {
        InputStream is = null;
        OutputStream out = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6 * 1000);
            is = conn.getInputStream();
            String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.length());
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            if (null != is) {
                out = response.getOutputStream();
                byte[] b = new byte[512];
                int len;
                while ((len = is.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.flush();
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
            assert conn != null;
            conn.disconnect();
        }
    }

    /**
     * 下载图片并合成水印
     *
     * @param response      HttpServletResponse
     * @param urlPath       图片访问路径
     * @param pictureString 水印信息
     */
    private void downloadFileWithParam(HttpServletResponse response, String urlPath, String pictureString) throws Exception {
        OutputStream out = null;
        try {
            byte[] newPictureData = draw(urlPath, pictureString);
            String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.length());
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");

            out = response.getOutputStream();
            out.write(newPictureData);
            out.flush();
        } finally {
            assert out != null;
            out.close();
        }

    }


    /**
     * 图片画框标注
     *
     * @param urlPath 图片路径
     * @return 合成图片二进制
     * @throws Exception
     */
    private byte[] draw(String urlPath, String str) throws Exception {
        //原始图片
        Image image = null;
        BufferedImage bufferedImage = null;
        image = ImageIO.read(new URL(urlPath));
        //图片宽
        int width = image.getWidth(null);
        //图片高
        int height = image.getHeight(null);

        bufferedImage = new BufferedImage(width, height + 70, BufferedImage.TYPE_INT_RGB);

        //创建一个指定 BufferedImage 的 Graphics2D 对象
        Graphics2D g = bufferedImage.createGraphics();
        ByteArrayOutputStream out;

        try {
            //确定图片尺寸
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, width, 70);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片


            //设置水印颜色
            g.setColor(new Color(255, 255, 255));
            //设置水印字体
            Font font = new Font("宋体", Font.PLAIN, 25);
            g.setFont(font);
            //设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
            //设置水印的坐标画出水印
            g.drawString(str, 5, 30);

            g.drawImage(image, 0, 70, width, height, null);

            //画图完成，BufferedImage转换为byte[]
            out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
        } finally {
            g.dispose();
        }
        return out.toByteArray();
    }
}
