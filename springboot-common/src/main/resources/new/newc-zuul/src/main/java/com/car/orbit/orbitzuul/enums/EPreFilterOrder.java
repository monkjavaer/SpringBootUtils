package com.car.orbit.orbitzuul.enums;

/**
 * CreateDate：2019/3/5 <br/>
 * Author：monkjavaer <br/>
 * Description: enums for custom pre filter order in zuul module
 **/
public enum EPreFilterOrder {

    /** order for AccessFilter */
    ORDER_ACCESS(0, "pre filter order for AccessFilter"),
    /** order for LoginPreFilter */
    ORDER_LOGIN_PRE(1, "pre filter order for LoginPreFilter");

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
    EPreFilterOrder(int orderValue, String orderDesc) {
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
