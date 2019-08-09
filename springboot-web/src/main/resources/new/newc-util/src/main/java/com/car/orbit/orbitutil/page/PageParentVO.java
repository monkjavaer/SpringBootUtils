package com.car.orbit.orbitutil.page;

import java.io.Serializable;

/**
 * CreateDate: 2018/5/11 <br/>
 * Author: wenliang <br/>
 * Description: 分页公共对象
 */
public class PageParentVO implements Serializable{

    /** serialize number */
    private static final long serialVersionUID = -1487565766433418751L;

    /** start page number, 1 for default */
    private int pageNo = 1;

    /** items count per page, 10 for default */
    private int pageSize = 10;

    /**
     * Gets page no.
     *
     * @return the page no
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * Sets page no.
     *
     * @param pageNo the page no
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
