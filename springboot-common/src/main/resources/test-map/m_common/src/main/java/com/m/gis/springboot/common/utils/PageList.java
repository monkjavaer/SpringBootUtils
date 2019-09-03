package com.m.gis.springboot.common.utils;

import java.util.List;

/**
 * @Title: PageList
 * @Package: springboot.common.utils
 * @Description: implement IPageList
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public class PageList<Entity> implements IPageList<Entity> {
    /**
     * 条目总数
     */
    private int itemCount;
    /**
     * 页码
     */
    private int pageIndex;
    /**
     * 页大小
     */
    private int pageSize;
    /**
     * 页总数
     */
    private int pageCount;
    /**
     * 前面有页
     */
    private boolean hasPre;
    /**
     * 后面有页
     */
    private boolean hasNext;
    /**
     * 条目List
     */
    private List<Entity> items;

    public PageList(int itemCount, int pageIndex, int pageSize, List<Entity> items) {
        this.itemCount = itemCount;
        this.pageSize = pageSize;
        this.pageCount = (itemCount % pageSize == 0) ? itemCount / pageSize : itemCount / pageSize + 1;
        this.pageIndex = pageIndex > pageCount ? pageCount : pageIndex;
        this.hasPre = pageIndex > 1;
        this.hasNext = pageIndex < pageCount;

        if (items != null)
            this.items = items;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public boolean getHasPre() {
        return this.hasPre;
    }

    public boolean getHasNext() {
        return this.hasNext;
    }

    public List<Entity> getItems() {
        return this.items;
    }

    /**
     * @param itemCount the itemCount to set
     */
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * @param pageIndex the pageIndex to set
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @param hasPre the hasPre to set
     */
    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    /**
     * @param hasNext the hasNext to set
     */
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}
