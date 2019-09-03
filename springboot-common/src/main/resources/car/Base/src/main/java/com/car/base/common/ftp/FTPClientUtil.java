package com.base.springboot.car.Base.src.main.java.com.car.base.common.ftp;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class FTPClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPClientUtil.class);

    private static FTPClientPool ftpClientPool;

    private static FTPConfig config;

    private static FTPClient ftpClient;

    private static String serverIp;
    private static int serverPort;
    private static String ftpServerUsername;
    private static String ftpServerPassword;
    private static int bufferSize;
    private static String ftpTopFolder;

    /*************************************************************************************************************************************
     ** @Description 初始化ftp的配置
     ** @Author Ding
     ** @Date 2018/4/16 17:46
     **
     ************************************************************************************************************************************/
    static {
        PropertiesUtil propertiesUtil = new PropertiesUtil("/ftp.properties");

        serverIp = propertiesUtil.getPropertieValue("ftp.server.ip");
        serverPort = Integer.parseInt(propertiesUtil.getPropertieValue("ftp.server.port"));
        ftpServerUsername = propertiesUtil.getPropertieValue("ftp.server.username");
        ftpServerPassword = propertiesUtil.getPropertieValue("ftp.server.password");
        bufferSize = Integer.parseInt(propertiesUtil.getPropertieValue("ftp.buffer.size"));
        ftpTopFolder = propertiesUtil.getPropertieValue("ftp.server.top.folder");

    }

    /**
     * 初始化ftp
     */
    private static void initFTPClient() {
        if (config == null) {
            config = new FTPConfig(serverIp, serverPort, ftpServerUsername, ftpServerPassword,
                    true, bufferSize);
        }
        if (ftpClientPool == null) {
            ftpClientPool = new FTPClientPool(config);
        }
    }

    /*************************************************************************************************************************************
     ** @Description 获取ftpClient
     ** @Author Ding
     ** @Date 2018/4/16 17:46
     **
     ************************************************************************************************************************************/
    private static void getFtpClient() {
        initFTPClient();
        if (ftpClient == null) {
            try {
                ftpClient = ftpClientPool.borrowObject();
            } catch (Exception e) {
                logger.error("get ftpClient is error :", e);
            }
        }
    }


    /*************************************************************************************************************************************
     ** @Description 上传ftp文件     PS fileName是相对文件名称
     ** @Author Ding
     ** @Date 2018/4/16 17:46
     **
     ************************************************************************************************************************************/
    public static String uploadFile(InputStream inputStream, String fileName) throws Exception {
        getFtpClient();

        try {
            if (ftpClient != null) {
                String basicPath = ftpTopFolder;
                String[] fps = fileName.split("/");
                for (int i = 0; i < fps.length - 1; i++) {
                    if (StringUtils.isBlank(fps[i])) {
                        continue;
                    }
                    basicPath = basicPath + "/" + fps[i];

                    if (!ftpClient.changeWorkingDirectory(basicPath)) {
                        ftpClient.makeDirectory(basicPath);
                    }

                }

                if (ftpClient.storeFile(ftpTopFolder + fileName, inputStream)) {
                    return ftpTopFolder + fileName;
                } else {
                    throw new Exception("Common.operateFailed");
                }
            }

        } catch (Exception e) {
            logger.error("upload ftp file is error !", e);
            ftpClientPool.returnObject(ftpClient);
            ftpClient = null;
        }
        return null;
    }

    public static void downloadFile(String fileName, HttpServletResponse response, boolean downloadAsFile) {
        InputStream is = null;
        OutputStream out = null;
        try {
            is = FTPClientUtil.downloadFile(fileName);

            if (null != is) {
                if (downloadAsFile) {
                    //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
                    response.setContentType("multipart/form-data");
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/")), "UTF-8"));
                }

                out = response.getOutputStream();
                byte[] b = new byte[512];
                int len;
                while ((len = is.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.flush();
            }
        } catch (Exception e) {
            logger.error("get ftp file is error !", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*************************************************************************************************************************************
     ** @Description 获取ftp上的文件   PS fileName是相对文件名称
     ** @Author Ding
     ** @Date 2018/4/16 18:05
     ** zks备注：ftpClient.getReply()这个方法在获取retrieveFileStream后要调用，否则之后请求retrieveFileStream就永远是null了
     ************************************************************************************************************************************/
    public static InputStream downloadFile(String fileName) {
        getFtpClient();
        InputStream is = null;
        try {
            is = ftpClient.retrieveFileStream(ftpTopFolder + fileName);
            return is;
        } catch (Exception e) {
            logger.error("download ftp file is error !", e);

        } finally {
            ftpClientPool.returnObject(ftpClient);
            ftpClient = null;
        }
        return null;
    }


    /*************************************************************************************************************************************
     ** @Description 删除文件的功能
     ** @Author Ding
     ** @Date 2018/4/17 10:16
     **
     ************************************************************************************************************************************/
    public static boolean deleteFile(String fileName) {
        getFtpClient();
        try {
            return ftpClient.deleteFile(ftpTopFolder + fileName);
        } catch (Exception e) {
            logger.error("delete ftp file is error !", e);
        } finally {
            ftpClientPool.returnObject(ftpClient);
        }
        return false;

    }


    /*************************************************************************************************************************************
     ** @Description 删除整个文件夹的功能
     ** @Author Ding
     ** @Date 2018/4/17 10:15
     **
     ************************************************************************************************************************************/
    public static boolean deleteDirectory(String path) {
        getFtpClient();
        try {
            FTPFile[] files = ftpClient.listFiles(ftpTopFolder + path);

            for (FTPFile f : files) {
                String fs = path + "/" + f.getName();
                if (f.isFile()) {
                    // 是文件就删除文件
                    ftpClient.deleteFile(ftpTopFolder + fs);
                } else if (f.isDirectory()) {
                    deleteDirectory(fs);
                }
            }

            return ftpClient.removeDirectory(ftpTopFolder + path);
        } catch (Exception e) {
            logger.error("delete ftp file is error !", e);
        } finally {
            ftpClientPool.returnObject(ftpClient);
        }
        return false;


    }


}
