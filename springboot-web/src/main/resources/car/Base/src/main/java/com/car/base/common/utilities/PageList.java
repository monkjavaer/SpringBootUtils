package com.car.base.common.utilities;

import java.util.List;

/**
 * 翻页
 * @author monkjavaer
 *
 * @param <Entity>
 */
public class PageList<Entity> implements IPageList<Entity>{
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
     * 后面有页〉
     */
    private boolean hasNext;
    /**
     * 条目List
     */
    private List<Entity> items;
    
    public PageList(int itemCount, int pageIndex, int pageSize, List<Entity> items) {
        this.itemCount = itemCount;
        this.pageSize = pageSize;
        this.pageCount=(itemCount % pageSize == 0) ? itemCount/pageSize :itemCount/pageSize+1;
        this.pageIndex=pageIndex>pageCount?pageCount:pageIndex;
        this.hasPre=pageIndex>1;
        this.hasNext=pageIndex<pageCount;
        
        if (items != null) {
            this.items = items;
        }
    }
    
    @Override
    public int getItemCount() {
        return this.itemCount;
    }
    
    @Override
    public int getPageIndex(){
    	return this.pageIndex;
    }
    
    @Override
    public int getPageSize() {
        return this.pageSize;
    }
    
    @Override
    public int getPageCount() {
    	return this.pageCount;
    }
    
    @Override
    public boolean getHasPre(){
    	return this.hasPre;
    }
    
    @Override
    public boolean getHasNext(){
    	return this.hasNext;
    }
    
    public List<Entity> getItems(){
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

	@Override
    public String resolveUrl(String url, String queryString, Integer pageNo, Integer pageSize){
    	if(queryString==null) {
            queryString=new String();
        } else {
            queryString=queryString.replaceAll("&pageNo=\\d*", "").replaceAll("pageNo=\\d*", "").replaceAll("&pageSize=\\d*", "").replaceAll("pageSize=\\d*", "");
        }
    	//注意pageNo=1&....时
    	if(pageNo!=null) {
            queryString=queryString.isEmpty()?"pageNo="+pageNo.toString():queryString+"&pageNo="+pageNo.toString();
        }
		
		if(pageSize!=null) {
            queryString=queryString.isEmpty()?"pageSize="+pageSize.toString():queryString+"&pageSize="+pageSize.toString();
        }
    	
    	return queryString.isEmpty()?url:url+"?"+queryString;
    }
}
