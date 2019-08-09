package com.car.trunk.dal.device.bo;

import com.car.base.common.utilities.PageList;

/**
 * Created by monkjavaer on 2017/12/12 0012.
 */
public class DeviceLogDTO {
    private PageList<DeviceLogBO> pageList;
    private Double totalRate;

    public PageList<DeviceLogBO> getPageList() {
        return pageList;
    }

    public void setPageList(PageList<DeviceLogBO> pageList) {
        this.pageList = pageList;
    }

    public Double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Double totalRate) {
        this.totalRate = totalRate;
    }
}
