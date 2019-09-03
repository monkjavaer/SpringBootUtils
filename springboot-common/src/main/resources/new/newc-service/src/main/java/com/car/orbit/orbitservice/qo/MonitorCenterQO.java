package com.car.orbit.orbitservice.qo;

import com.car.orbit.orbitutil.page.PageParentVO;

import java.math.BigInteger;

/**
 * @Title: MonitorCenterQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 机构分页查询对象
 * @Author: monkjavaer
 * @Data: 2019/3/7 18:22
 * @Version: V1.0
 */
public class MonitorCenterQO extends PageParentVO {
    /**
     * 机构名
     */
    private String name;
    /**
     * 机构地址
     */
    private String address;
    /**
     * 机构地址
     */
    private BigInteger id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
