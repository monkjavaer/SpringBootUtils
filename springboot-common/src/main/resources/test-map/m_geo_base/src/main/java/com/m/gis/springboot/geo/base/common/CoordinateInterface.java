package com.m.gis.springboot.geo.base.common;

/**
 * @Title: CoordinateInterface
 * @Package: springboot.geo.base.common
 * @Description: coordinate interface
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public interface CoordinateInterface {

    /**
     * 返回经度，格式为xx.xxxxx
     *
     * @return
     */
    public double getLongitude();

    /**
     * 返回经度，格式为xx.xxxxx
     *
     * @return
     */
    public double getLatitude();

}
