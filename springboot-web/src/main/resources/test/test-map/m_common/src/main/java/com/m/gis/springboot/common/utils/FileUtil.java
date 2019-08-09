package com.m.gis.springboot.common.utils;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FileUtil
 * @Package: com.m.gis.springboot.common.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/9/4
 * @Version: V1.0
 */
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /***
     * @Description: 创建文件
     * @Author: monkjavaer
     * @Data: 18:04 2018/9/4
     * @Param: [filePath, filecontent]
     * @Throws
     * @Return boolean
     */
    public static boolean create(String filePath){
        if(StringUtils.isBlank(filePath))
            throw new GisIllegalParameterCommonException("filePath is null.");

        Boolean bool = false;
        File file = new File(filePath);

        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                logger.info("success create file "+filePath);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return bool;
    }


    /***
     * @Description: 向文件中写入内容
     * @Author: monkjavaer
     * @Data: 18:04 2018/9/4
     * @Param: [filepath, newstr]
     * @Throws
     * @Return boolean
     */
    public static boolean write(String filepath,String newstr,boolean append){
        Boolean bool = false;
        File file = new File(filepath);//文件路径(包括文件名称)

        if(file==null)
            throw new GisIllegalParameterCommonException(String.format("File %s is not exist.",filepath));

        try(FileWriter writer = new FileWriter(filepath, append)){
            writer.write(newstr);
            bool = true;
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return bool;
    }

    /***
     * @Description: 从文件中读取内容
     * @Author: monkjavaer
     * @Data: 18:23 2018/9/4
     * @Param: [filepath]
     * @Throws
     * @Return boolean
     */
    public static String read(String filepath){
        File file = new File(filepath);//文件路径(包括文件名称)

        if(file==null)
            throw new GisIllegalParameterCommonException(String.format("File %s is not exist.",filepath));

        StringBuffer stringBuffer = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String tempString = null;

            while ((tempString = br.readLine()) != null) {
                stringBuffer.append(tempString);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return stringBuffer.toString();
    }

    /***
     * @Description: 从文件中读取每一行，并返回list
     * @Author: monkjavaer
     * @Data: 11:33 2018/9/5
     * @Param: [filepath]
     * @Throws
     * @Return java.util.List<java.lang.String>
     */
    public static List<String> readLines(String filepath){
        File file = new File(filepath);//文件路径(包括文件名称)

        if(file==null)
            throw new GisIllegalParameterCommonException(String.format("File %s is not exist.",filepath));

        List<String> strings = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String tempString = null;

            while ((tempString = br.readLine()) != null) {
                strings.add(tempString);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return strings;
    }



    /**
     * 删除文件
     * @param fileName 文件名称
     * @return
     */
    public static boolean delete(String fileName){
        Boolean bool = false;
        File file  = new File(fileName);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return bool;
    }


}
