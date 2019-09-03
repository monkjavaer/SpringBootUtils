package com.car.orbit.orbitservice.vo;

/**
 * @Title: AlarmOneFileVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 报警列表开启一车一档
 * @Author: monkjavaer
 * @Data: 2019/4/2 14:13
 * @Version: V1.0
 */
public class AlarmOneFileVO {

    /**
     * 车牌
     */
    private String plateNumber;
    /**
     * 图片路径
     */
    private String url;

    /**
     * 每个车牌总数
     */
    private Integer total;


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
