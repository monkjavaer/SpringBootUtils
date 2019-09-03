package com.car.orbit.orbitutil.response;

/**
 * CreateDate：2018/4/26 <br/>
 * Author：wenliang <br/>
 * Description: common return code for each project
 **/
public interface IResponseType {

    /** request success */
    Integer SUCCESS = 200;

    /** error in gateway */
    Integer ZUUL_ERROR = 204;


    /** illegal request param */
    Integer ILLEGAL_PARAMETER = 203;

    /** unknown internal server error */
    Integer UNKNOWN = 99999;

    /**
     * get return code
     *
     * @return return code
     */
    public Integer getCode();

    /**
     * get code description
     *
     * @return return code description
     */
    public String getDesc();
}
