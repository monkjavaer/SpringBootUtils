package com.car.base.common.excel;

/**
 * @Title: ExcelResult
 * @Package: com.car.base.common.excel
 * @Description: ExcelResult
 * @Author: monkjavaer
 * @Date: 2019/6/4 18:49
 * @Version: V1.0
 */
public class ExcelResult {
    /**
     * 成功条数
     */
    private int success;
    /**
     * 失败条数
     */
    private int fail;
    /**
     * 失败行数
     */
    private String failRow;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String getFailRow() {
        return failRow;
    }

    public void setFailRow(String failRow) {
        this.failRow = failRow;
    }
}
