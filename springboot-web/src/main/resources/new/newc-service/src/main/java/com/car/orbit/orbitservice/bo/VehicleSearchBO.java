package com.car.orbit.orbitservice.bo;

import java.util.List;

/**
 * @Title: VehicleSearchBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 聚合分页返回
 * @Author: monkjavaer
 * @Data: 2019/3/19 19:23
 * @Version: V1.0
 */
public class VehicleSearchBO<T> {

    /**
     * 聚合结果，Redis key
     */
    private String searchKey;

    /**
     * 聚合结果总数
     */
    private Integer count;

    /**
     * 聚合分页结果
     */
    private List<T> pageList;

    /**
     * 是否被重定向到第一页
     */
    private Boolean redirect;

    public VehicleSearchBO(String searchKey, Integer count, List<T> pageList) {
        this.searchKey = searchKey;
        this.count = count;
        this.pageList = pageList;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public Boolean getRedirect() {
        return redirect;
    }

    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }
}
