package com.car.orbit.orbitutil.response;

/**
 * CreateDate：2018/8/29 <br/>
 * Author：wenliang <br/>
 * Description: store response type information of insight guide project
 **/
public enum ResponseType implements IResponseType {

    /** request success */
    SUCCESS(IResponseType.SUCCESS, "ok"),
    /** illegal parameter */
    ILLEGAL_PARAMETER(IResponseType.ILLEGAL_PARAMETER, "illegal parameter"),
    /** unknown internal server error */
    UNKNOWN(IResponseType.UNKNOWN, "unknown internal server error"),
    /** illegal token */
    ILLEGAL_TOKEN(202, "account status error, please login again"),

    EXPORT_ERROR(210, "export error!"),
    NAME_EXIST(1001, "name is exist");

    /** response code */
    private Integer code;

    /** code description */
    private String desc;

    /**
     * private construction function
     *
     * @param code response code
     * @param desc code description
     */
    ResponseType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}