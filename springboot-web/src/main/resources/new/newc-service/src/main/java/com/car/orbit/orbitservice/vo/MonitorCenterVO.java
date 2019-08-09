package com.car.orbit.orbitservice.vo;

/**
 * @Title: MonitorCenterVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 部分机构信息，用于下拉选择
 * @Author: monkjavaer
 * @Data: 2019/3/7 19:28
 * @Version: V1.0
 */
public class MonitorCenterVO {
    /**
     * 机构id
     */
    private String id;
    /**
     * 机构名
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
