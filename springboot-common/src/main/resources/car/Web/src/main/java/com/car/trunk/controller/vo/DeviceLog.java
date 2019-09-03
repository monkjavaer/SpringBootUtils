package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller.vo;

import com.car.trunk.dal.device.bo.DeviceLogBO;

import java.util.List;

/**
 * Description:设备状态分页列表返回参数构造
 * Created by monkjavaer on 2017/12/13 0013.
 */
public class DeviceLog {
    private List<DeviceLogBO> items;
    private int itemCount;
    private Double totalRate;

    public List<DeviceLogBO> getItems() {
        return items;
    }

    public void setItems(List<DeviceLogBO> items) {
        this.items = items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Double totalRate) {
        this.totalRate = totalRate;
    }
}
