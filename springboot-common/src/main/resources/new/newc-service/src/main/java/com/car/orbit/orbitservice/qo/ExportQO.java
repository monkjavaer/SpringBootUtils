package com.car.orbit.orbitservice.qo;

/**
 * @Title: ExportQO
 * @Package: com.car.orbit.orbitservice.qo
 * @Description: 导出QO(用于以图收图，综合搜车)
 * @Author: monkjavaer
 * @Data: 2019/3/23 16:30
 * @Version: V1.0
 */
public class ExportQO<T> {
    /**
     * 导入开始条数
     */
    private Integer start;
    /**
     * 导出结束条数
     */
    private Integer end;

    /**
     * true带图片，false不带图片
     */
    private Boolean pictureFlg;
    /**
     * 查询QO
     */
    private T queryObject;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public T getQueryObject() {
        return queryObject;
    }

    public void setQueryObject(T queryObject) {
        this.queryObject = queryObject;
    }

    public Boolean getPictureFlg() {
        return pictureFlg;
    }

    public void setPictureFlg(Boolean pictureFlg) {
        this.pictureFlg = pictureFlg;
    }
}
