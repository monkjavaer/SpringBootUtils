package com.m.gis.springboot.common.utils;

import java.util.List;

/**
 * @Title: PageListUtil
 * @Package: springboot.common.utils
 * @Description: util used for page
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class PageListUtil {
    /**
     * 默认页号
     */
    public static final int DEFAULT_PAGE_NO = 1;

    /**
     * 默认页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String PAGE_NO_NAME = "pageNo";

    public static final String PAGE_SIZE_NAME = "pageSize";

    /**
     * 获取所需要页的第一条记录的编号，从0开始
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static int getPageListStart(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }

    /**
     * 获取所需要页的第一条记录的编号，并判断参数是否合法；从0开始
     *
     * @param totalCount
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static int getPageListStart(int totalCount, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        if (start >= totalCount)
            start = 0;

        return start;
    }

    /**
     * 获得
     *
     * @param totalCount
     * @param pageIndex
     * @param items
     * @param pageSize
     * @return
     */
    public static <Entity> PageList<Entity> getPageList(int totalCount, int pageIndex, int pageSize, List<Entity> items) {
        return new PageList<Entity>(totalCount, pageIndex, pageSize, items);
    }

}
