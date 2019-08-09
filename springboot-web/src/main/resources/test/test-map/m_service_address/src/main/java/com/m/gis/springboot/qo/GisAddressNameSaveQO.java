package com.m.gis.springboot.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Title: GisAddressNameSaveQO
 * @Package: com.m.gis.springboot.qo
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/21
 * @Version: V1.0
 */
@ApiModel(value = "GisAddressNameSaveQO", description = "")
public class GisAddressNameSaveQO{

    @ApiModelProperty(value = "待插入的地名列表")
    private List<GisAddressNameElementQO> addressList;

    @ApiModelProperty(value = "地名记录者，系统用户名")
    private String recorder;

    public GisAddressNameSaveQO() {
    }

    public GisAddressNameSaveQO(List<GisAddressNameElementQO> addressList, String recorder) {
        this.addressList = addressList;
        this.recorder = recorder;
    }

    public List<GisAddressNameElementQO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<GisAddressNameElementQO> addressList) {
        this.addressList = addressList;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
}

