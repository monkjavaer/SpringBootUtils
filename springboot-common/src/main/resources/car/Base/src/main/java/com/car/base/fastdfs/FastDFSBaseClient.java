package com.base.springboot.car.Base.src.main.java.com.car.base.fastdfs;

import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @ClassName: FastDFSClient
 * @Description: 备份中心fdfs工具类
 * @author: monkjavaer
 * @date: 2018-9-25 下午3:34:46
 */
public class FastDFSBaseClient {


    private static final String CONF_FILENAME = "fdfs_baseclient.conf";
    private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient1 storageClient1= null;
    private static Logger logger = LoggerFactory.getLogger(FastDFSBaseClient.class);

    /**
     * 只加载一次.
     */
    static {
        try {
            logger.info("=== CONF_FILENAME:" + CONF_FILENAME);
            ClientGlobal.init(CONF_FILENAME);
            trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                logger.error("getConnection return null");
            }
            storageServer = trackerClient.getStoreStorage(trackerServer);
//            if (storageServer == null) {
//                logger.error("getStoreStorage return null");
//            }
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 上传文件
     * @param photoUrl 图片文件的浏览路径
     * @return
     */
    public static String uploadFileByURL(String photoUrl) {
        try {
            String fileName = SnowflakeIdWorkerUtil.generateId() + ".jpg";
            URL url = new URL(photoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //重要：网络文件的大小
            int fileLength = conn.getContentLength();
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取图片的数组
            byte[] fileContent = readInputStream(inputStream);
            Map<String,String> metaList = new HashMap<String,String>();
            metaList.put("fileName", fileName);
            metaList.put("fileExtName", "jpg");
            metaList.put("fileLength", String.valueOf(fileLength));
            return uploadFile(fileContent, fileName,metaList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Update image to FastDFS is error,emessage="+e.getMessage());
        }
        return "erro";
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @return
     */
    public static String uploadFile(File file, String fileName) {
        try {
            return  uploadFile(file,fileName,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @param metaList 文件元数据
     * @return
     */
    public static String uploadFile(File file, String fileName, Map<String,String> metaList) {
        try {
            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
            return  uploadFile(buff,fileName,metaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     * @param fileContent 文件的内容，字节数组
     * @param fileName 文件名,带扩展名
     * @return
     */
    public static String uploadFile(byte[] fileContent, String fileName) {
        return uploadFile(fileContent, fileName, null);
    }

    /**
     * 上传文件
     * @param fileContent 文件的内容，字节数组
     * @param fileName 文件名,带扩展名
     * @param metaList 文件扩展信息
     * @return
     */
    public static String uploadFile(byte[] fileContent, String fileName, Map<String,String> metaList) {
        try {
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name, value);
                }
            }
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            String fileUrl = storageClient1.upload_file1(fileContent, FilenameUtils.getExtension(fileName), nameValuePairs);
            logger.info("uploadFile to FastDFS is sucess,fileUrl="+fileUrl);
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("uploadFile to FastDFS is error,emessage="+e.getMessage());
        }
        return null;
    }

    /**
     *
     * @Title: uploadFile
     * @Description: 上传文件
     * @param fullpath  文件全路径
     * @return
     * @return: String  文件id
     */
    public static String uploadFile( String fullpath) {
        File file = new File(fullpath);
        FileInputStream fis = null;
        try {
            NameValuePair[] meta_list = null; // new NameValuePair[0];
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            String fileid = storageClient1.upload_file1(file_buff, getFileExt(fullpath), meta_list);
            return fileid;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }finally{
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }


    /**
     *
     * @Title: uploadSlaveFile
     * @Description: 上传关联文件
     * @param masterFileId  主文件id
     * @param fullpath  从文件全路径
     * @param prefixName   从文件后缀名    例如 主文件为 product_100010.png  从文件为分辨率不同的  product_100010_120x120.png
     * @return
     * @return: String 文件id
     */
    public static String uploadSlaveFile(String masterFileId, String fullpath,String prefixName) {
        File file = new File(fullpath);
        FileInputStream fis = null;
        try {
            NameValuePair[] meta_list = null; // new NameValuePair[0];
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            String fileid =storageClient1.upload_file1(masterFileId,prefixName , fullpath, getFileExt(fullpath), null);

            return fileid;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }finally{
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }



    /**
     *
     * @Title: deleteFile
     * @Description: 根据组名和远程文件名来删除一个文件 如果没有组默认为group1
     * @param groupName
     * @param fileName  例如：/M00/00/00/wKgB-lkdxUmAPb-QAAIbD3CxJDw317.jpg
     * @return
     * @return: int
     */
    public static int deleteFile(String groupName, String fileName) {
        try {
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            int result = storageClient1.delete_file(groupName == null ? "group1" : groupName, fileName);
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    /**
     *
     * @Title: deleteFile
     * @Description: 根据fileId来删除一个文件
     * @param fileId
     * @return
     * @return: int
     */
    public static int deleteFile(String fileId) {
        try {
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            int result = storageClient1.delete_file1(fileId);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @Title: modifyFile
     * @Description: 修改一个已经存在的文件  先上传新的后删除旧的
     * @param oldFileId 旧文件id
     * @param fullPath  新文件全路径
     * @return
     * @return: String
     */
    public static String modifyFile(String oldFileId, String fullPath) {
        String fileid = null;
        try {
            // 先上传
            fileid = uploadFile(fullPath);
            if (fileid == null) {
                return null;
            }
            // 再删除
            int delResult = deleteFile(oldFileId);
            if (delResult != 0) {
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
        return fileid;
    }

    /**
     *
     * @Title: downloadFile
     * @Description: 文件下载
     * @param fileId
     * @return
     * @return: InputStream
     */
    public static InputStream downloadFile(String fileId) {
        try {
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1  storageClient1 = new StorageClient1(trackerServer, storageServer);
            byte[] bytes = storageClient1.download_file1(fileId);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @Title: getFileExt
     * @Description: 获取文件后缀名（不带点）.
     * @param fileName
     * @return
     * @return: String
     */
    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
        }
    }

    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}

