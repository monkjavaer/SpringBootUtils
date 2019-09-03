package com.car.orbit.orbitzuul.enums;

/**
 * CreateDate：2018/5/17 <br/>
 * Author：NieLixiang <br/>
 * Description: enums for error filter order
 **/
public enum EErrorFilterOrder {

    /** order for ErrorFilter */
    ORDER_ERROR(10, "error filter order for ErrorFilter"),
    /** order for PostErrorFilter */
    ORDER_POST_ERROR(30, "error filter order for PostErrorFilter, must more than order for ErrorFilter");

    /** order value */
    private int orderValue;

    /** order description */
    private String orderDesc;

    /**
     * construction function
     *
     * @param orderValue order value
     * @param orderDesc order description
     */
    private EErrorFilterOrder(int orderValue, String orderDesc) {
        this.orderValue = orderValue;
        this.orderDesc = orderDesc;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public String getOrderDesc() {
        return orderDesc;
    }
}
