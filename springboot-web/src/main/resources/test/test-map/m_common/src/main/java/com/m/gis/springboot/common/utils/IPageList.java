package com.m.gis.springboot.common.utils;

/**
 * @Title: IPageList
 * @Package: springboot.common.utils
 * @Description: define page list interface
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public interface IPageList<Entity> {
    /**
     * 条目总数
     *
     * @return
     */
    public int getItemCount();

    /**
     * 页号
     *
     * @return
     */
    public int getPageIndex();

    /**
     * 页大小
     *
     * @return
     */
    public int getPageSize();

    /**
     * 页总数
     *
     * @return
     */
    public int getPageCount();

    /**
     * 前面是否有页
     *
     * @return
     */
    public boolean getHasPre();

    /**
     * 后面是否有页
     *
     * @return
     */
    public boolean getHasNext();

}
