package com.car.orbit.orbitutil.response;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitutil.exception.OrbitException;

/**
 * @Title: ResultUtil
 * @Package: com.car.orbit.orbitutil.tools.response
 * @Description: 返回前端工具类
 * @Author: monkjavaer
 * @Data: 2019/3/7 20:14
 * @Version: V1.0
 */
public class ResultUtil {

    /**
     * 返回成功，传入返回体具体出參
     *
     * @param object 返回对象
     * @return 出参
     */
    public static OrbitResult success(Object object) {
        return new OrbitResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getDesc(), true, object);
    }

    /**
     * 返回成功，提供给部分不需要出參的接口
     *
     * @return
     */
    public static OrbitResult success() {
        return success(null);
    }

    /**
     * 返回成功，供只有一个字段的返回值的方法使用
     *
     * @param object 返回值
     * @param key 返回值名称
     * @return 操作结果
     */
    public static OrbitResult success(Object object, String key) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, object);
        return new OrbitResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getDesc(), true, jsonObject);
    }

    /**
     * 自定义错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static OrbitResult error(Integer code, String msg) {
        return new OrbitResult(code, msg, false, null);
    }

    /**
     * 返回异常信息，在已知的范围内
     *
     * @param e
     * @return
     */
    public static OrbitResult error(OrbitException e) {
        return new OrbitResult(e.getCode(), e.getMessage(), false, null);
    }
}
