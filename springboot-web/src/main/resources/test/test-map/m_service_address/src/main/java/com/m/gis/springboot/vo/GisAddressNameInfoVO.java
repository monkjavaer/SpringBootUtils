package com.m.gis.springboot.vo;

/**
 * @Title: GisAddressNameInfoVO
 * @Package: com.m.gis.springboot.vo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/7/4
 * @Version: V1.0
 */
public class GisAddressNameInfoVO {
    private String geom;
    private String envelope;

    public GisAddressNameInfoVO() {
    }

    public GisAddressNameInfoVO(String geom,String envelope) {
        this.geom = geom;
        this.envelope = envelope;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(String envelope) {
        this.envelope = envelope;
    }
}

