package com.m.gis.springboot.utils;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.common.exception.GisException;

/**
 * @Title: ResultUtil.java
 * @Package com.m.gis.springboot.utils
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月16日 下午10:44:31
 * @version V1.0
 */
public class ResultUtil {

    /**
     * 返回成功，传入返回体具体出參
     * @param object
     * @return
     */
    public static GisResult success(Object object){
        return new GisResult("0000","success",object);
    }

    /**
     * 提供给部分不需要出參的接口
     * @return
     */
    public static GisResult success(){
        return success(null);
    }

    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static GisResult error(String code, String msg){
        return new GisResult(code,msg,null);
    }

    /**
     * 返回异常信息，在已知的范围内
     * @param exceptionEnum
     * @return
     */
    public static GisResult error(GisException e){
        return new GisResult(e.getCode(),e.getMessage(),null);
    }
}

