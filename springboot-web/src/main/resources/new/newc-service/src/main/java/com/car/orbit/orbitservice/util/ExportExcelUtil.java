package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitutil.tools.FastDFSClient;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @Title: ExportExcelUtil
 * @Package: com.car.orbit.orbitservice.util
 * @Description: 导出工具类
 * @Author: monkjavaer
 * @Data: 2019/3/23 15:49
 * @Version: V1.0
 */
public class ExportExcelUtil {

    /**
     * 导出EXCEL(写入fastdfs,返回URL)
     *
     * @param excelName    Excel名字(xxx.xls)
     * @param headers      表头
     * @param exportFields controller传入结果集中datas要导出的数据字段名
     * @param datas        数据列表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String exportExcel(String excelName, String[] headers, String[] exportFields, List<?> datas) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // 创建HSSFWorkbook对象
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建HSSFSheet对象
        XSSFSheet sheet = wb.createSheet("sheet0");
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        XSSFCellStyle style = wb.createCellStyle();
        //设置样式
        setStyle(style);

        // 创建HSSFRow对象(第一行，表头)
        XSSFRow row = sheet.createRow(0);
        //循环写表头
        for (short i = 0; i < headers.length; i++) {
            // 把字体应用到当前的样式
            style.setFont(getBoldFont(wb));
            // 创建HSSFCell对象
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(headers[i]);
        }


        //循环设值
        for (int j = 0; j < datas.size(); j++) {
            //从第二行开始写
            row = sheet.createRow(j + 1);
            //导出偏移量，用于过滤数据
            for (int k = 0; k < exportFields.length; k++) {
                String fieldName = exportFields[k];
                XSSFCell cell = row.createCell(k);
                cell.setCellStyle(style);
                //反射获取get方法和值
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                Method getMethod = datas.get(j).getClass().getMethod(getMethodName, new Class[]{});
                Object value = getMethod.invoke(datas.get(j), new Object[]{});

                // 把字体应用到当前的样式
                style.setFont(getBlackFont(wb));
                cell.setCellStyle(style);
                //类型转化
                convertValue(cell, value);

                //图片处理
                if (value instanceof byte[]) {

                    sheet.addMergedRegion(new CellRangeAddress(j + 1, j + 1, j + 1, j + 1));
                    XSSFDrawing xssfDrawing = sheet.createDrawingPatriarch();
                    XSSFClientAnchor anchor = new XSSFClientAnchor(480, 30, 700, 250, (short) k, j + 1, (short) k + 1, j + 2);
                    xssfDrawing.createPicture(anchor, wb.addPicture((byte[]) value, XSSFWorkbook.PICTURE_TYPE_JPEG));
                    sheet.setColumnWidth((short) 800, (short) 800);
                    row.setHeight((short) 800);
                }
            }
        }
        String filePath = null;
        //写一份到ftp服务器
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] data = os.toByteArray();
            filePath = FastDFSClient.uploadFile(data, excelName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }


    /**
     * 类型转化
     *
     * @param cell  HSSFCell对象
     * @param value 属性值
     * @return HSSFCell对象
     */
    private static void convertValue(XSSFCell cell, Object value) {
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Short) {
            cell.setCellValue((Short) value);
        } else if (value instanceof Byte) {
            cell.setCellValue((Byte) value);
        }
    }

    /**
     * 样式设置
     *
     * @param style
     */
    private static void setStyle(XSSFCellStyle style) {
        // 设置样式
        // 前景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);
    }

    /**
     * 生成黑体字
     *
     * @param wb
     * @return
     */
    private static XSSFFont getBlackFont(XSSFWorkbook wb) {
        // 生成一个字体
        XSSFFont font = wb.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        return font;
    }

    /**
     * 生成加粗黑体字
     *
     * @param wb
     * @return
     */
    private static XSSFFont getBoldFont(XSSFWorkbook wb) {
        // 生成一个字体
        XSSFFont font = wb.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 12);
        return font;
    }

    /**
     * @param wb
     * @param patriarch
     * @param rowIndex
     */
    private static void drawPictureInfoExcel(HSSFWorkbook wb, HSSFPatriarch patriarch, int rowIndex, byte[] data) {
        //rowIndex代表当前行
        //anchor主要用于设置图片的属性
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250, (short) 0, rowIndex, (short) 0, rowIndex);
        //Sets the anchor type （图片在单元格的位置）
        //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
    }
}
