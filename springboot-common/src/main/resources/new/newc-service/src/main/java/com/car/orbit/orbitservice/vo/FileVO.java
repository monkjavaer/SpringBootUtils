package com.car.orbit.orbitservice.vo;

/**
 * @Title: FileVO
 * @Package: com.car.orbit.orbitservice.vo
 * @Description: 文件上传的结果
 * @Author: monkjavaer
 * @Date: 2019/03/11 17:44
 * @Version: V1.0
 */
public class FileVO {

    /**
     * 文件url
     */
    private String url;

    public FileVO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
