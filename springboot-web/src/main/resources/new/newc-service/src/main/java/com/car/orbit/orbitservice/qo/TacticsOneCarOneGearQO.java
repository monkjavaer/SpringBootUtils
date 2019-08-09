package com.car.orbit.orbitservice.qo;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-一车一档查询对象
 **/
public class TacticsOneCarOneGearQO {
    /** 车牌号 */
    private String plateNumber;

    /** 车牌颜色 */
    private String plateColor;

    /** redis缓存key */
    private String searchKey;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
