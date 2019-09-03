package com.car.orbit.orbitservice.bo;


import com.car.orbit.orbitservice.vo.TogetherVehicleInfoVO;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;

import java.util.List;

/**
 * @Title: TogetherVehicleBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 同行车返回分页详细包装类
 * @Author: monkjavaer
 * @Data: 2019/3/28 14:29
 * @Version: V1.0
 */
public class TogetherVehicleBO {

    /**
     * 聚合结果，Redis key
     */
    private String searchKey;

    /**
     * 聚合结果总数
     */
    private Integer count;

    /**
     * 原始车辆轨迹(用于地图显示)
     */
    private List<VehicleTrajectoryVO> originalTrajectoryList;

    /**
     * 同行车轨迹
     */
    private List<VehicleTrajectoryVO> togetherTrajectoryList;

    /**
     * 分页结果
     */
    private List<TogetherVehicleInfoVO> pageList;

    public TogetherVehicleBO(String searchKey, Integer count,
                             List<VehicleTrajectoryVO> originalTrajectoryList,
                             List<VehicleTrajectoryVO> togetherTrajectoryList,
                             List<TogetherVehicleInfoVO> pageList) {
        this.searchKey = searchKey;
        this.count = count;
        this.originalTrajectoryList = originalTrajectoryList;
        this.togetherTrajectoryList = togetherTrajectoryList;
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

    public List<VehicleTrajectoryVO> getOriginalTrajectoryList() {
        return originalTrajectoryList;
    }

    public void setOriginalTrajectoryList(List<VehicleTrajectoryVO> originalTrajectoryList) {
        this.originalTrajectoryList = originalTrajectoryList;
    }

    public List<VehicleTrajectoryVO> getTogetherTrajectoryList() {
        return togetherTrajectoryList;
    }

    public void setTogetherTrajectoryList(List<VehicleTrajectoryVO> togetherTrajectoryList) {
        this.togetherTrajectoryList = togetherTrajectoryList;
    }

    public List<TogetherVehicleInfoVO> getPageList() {
        return pageList;
    }

    public void setPageList(List<TogetherVehicleInfoVO> pageList) {
        this.pageList = pageList;
    }
}
