package com.car.orbit.orbitservice.util.redis;

/**
 * 用于转化字典国际化的对象
 */
public class InternationalObject {
    /**
     * 代码
     */
    private String code;

    /**
     * 中文名
     */
    private String zhName;

    /**
     * 英文名
     */
    private String enName;

    /**
     * 西班牙语
     */
    private String esName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }
}
