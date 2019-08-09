package com.car.orbit.orbitservice.vo;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description: 套牌车辆简单对象
 * Version: 1.0
 **/
public class DeckVehiclesVO extends TacticsVehicleBaseInfo{

    /**
     * 异常状态
     */
    private String exception;

    /**
     * 套牌车辆异常过车信息次数
     */
    private int deckRecord;

    /**
     * 最近抓拍时间
     * */
    private String captureTime;

    /**
     * 处理状态,1未处理，2已排除
     * */
    private String status;

    public int getDeckRecord() {
        return deckRecord;
    }

    public void setDeckRecord(int deckRecord) {
        this.deckRecord = deckRecord;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
