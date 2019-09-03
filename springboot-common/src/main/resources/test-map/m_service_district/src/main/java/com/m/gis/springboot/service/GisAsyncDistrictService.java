package com.m.gis.springboot.service;

/**
 * @Title: GisAsyncDistrictService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public interface GisAsyncDistrictService {

    /***
     * @Description: 异步加载各级行政区域的缓冲信息
     * @Author: monkjavaer
     * @Data: 14:07 2018/6/27
     * @Param: []
     * @Throws
     * @Return void
     */
    public void initDistrict();
}
