package com.base.springboot.utils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description: pagehelper 分页工具类
 * @Author: tangquanbin
 * @Data: 2019/3/7 18:15
 * @Version: V1.0
 */
public class PageUtil<T> {
    private Integer pageIndex;
    private Integer pageSize;
    private long itemCount;
    private Integer pageCount;
    List<T> items;
    public PageUtil() {
        super();
    }

    public PageUtil(List<T> list){
        //PageInfo分页插件类
        PageInfo page = new PageInfo(list);
        pageIndex = page.getPageNum();
        pageSize = page.getPageSize();
        itemCount = page.getTotal();
        pageCount = page.getPages();
        items = list;
    }

    public PageUtil(PageUtil page){
        pageIndex = page.getPageIndex();
        pageSize = page.getPageSize();
        itemCount = page.getItemCount();
        pageCount = page.getPageCount();
        items = page.getItems();
    }

    public PageUtil(Integer pageIndex, Integer pageSize, Integer itemCount,
                    Integer pageCount, List<T> items) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.itemCount = itemCount;
        this.pageCount = pageCount;
        this.items = items;
    }
    public Integer getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public long getItemCount() {
        return itemCount;
    }
    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
    public Integer getPageCount() {
        return pageCount;
    }
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}