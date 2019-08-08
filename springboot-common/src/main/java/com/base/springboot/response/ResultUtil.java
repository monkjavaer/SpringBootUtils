package com.base.springboot.response;


import com.base.springboot.exception.BaseException;

/**
 * @Description: 返回结果工具
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
    public static BaseResult success(Object object){
        return new BaseResult("0000","success",object);
    }

    /**
     * 提供给部分不需要出參的接口
     * @return
     */
    public static BaseResult success(){
        return success(null);
    }

    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static BaseResult error(String code, String msg){
        return new BaseResult(code,msg,null);
    }

    /**
     * 返回异常信息，在已知的范围内
     * @param e
     * @return
     */
    public static BaseResult error(BaseException e){
        return new BaseResult(e.getCode(),e.getMessage(),null);
    }
}

