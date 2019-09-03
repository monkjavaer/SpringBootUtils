package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

/**
 * 
 * @author monkjavaer
 *
 * @param <Entity>
 */
public interface IPageList<Entity> {  
    /**
     * 条目总数
     * 
     * @return
     */
    int getItemCount();
    
    /**
     * 页号
     * @return
     */
    int getPageIndex();
    
    /**
     * 页大小
     *  
     * @return
     */
    int getPageSize();
    
    /**
     * 页总数
     * 
     * @return
     */
    int getPageCount();
    
    /**
     * 前面是否有页
     * @return
     */
    boolean getHasPre();
    
    /**
     * 后面是否有页
     * @return
     */
    boolean getHasNext();
    
    String resolveUrl(String url, String queryString, Integer pageNo, Integer pageSize);
}
