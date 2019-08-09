package com.car.base.common.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传excel,poi读取excel
 * Created by monkjavaer on 2017/12/6 0006.
 */
public class ReadExcel {


    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    public List getExcelInfo(String fileName, MultipartFile multipartFile){

        //获取本地存储路径
        String path = ReadExcel.class.getResource("/").getPath()+System.currentTimeMillis()+fileName;
        //新建一个文件
        File file = new File(path);
        //将上传的文件写入新建的文件中
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List readList = getExcelData(fileName, path, file);

        //删除临时文件！
        file.deleteOnExit();

        return readList;

    }

    public List<Object> simpleReadListStringV2007(MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        List<Object> data = EasyExcelFactory.read(inputStream, new com.alibaba.excel.metadata.Sheet(1, 0));
        inputStream.close();
        return data;
    }

    public List getExcelData(String fileName,String path,File file) {
        // 流操作
        // InputStream此抽象类是表示字节输入流的所有类的超类。
        // FileInputStream 从文件系统中的某个文件中获得输入字节

        InputStream inputStream = null;
        List readList = new ArrayList();
        Workbook workbook = null;
        // 得到工作薄对象
        try {
            inputStream = new FileInputStream(path);

            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //当excel是2003时
            if(isExcel2003){
                workbook = new HSSFWorkbook(inputStream);
            }
            else{//当excel是2007时
                workbook = new XSSFWorkbook(inputStream);
            }
            //读取Excel里面客户的信息
            readList=readExcelValue(workbook);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return readList;
    }

    /**
     * 读取Excel里面客户的信息
     * @param wb
     * @return
     */
    private List readExcelValue(Workbook wb){
        List readList = new ArrayList<>();
        int totalRows = 0;
        int totalCols = 0;
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);

        //得到Excel的行数
         totalRows = sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
        }


        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++){
            String[] colArr = new String[totalCols];
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            //循环Excel的列
            for(int c = 0; c <totalCols; c++){
                Cell cell = row.getCell(c);

                colArr[c] =getCellValue(cell);
            }
            readList.add(colArr);
        }
        return readList;
    }


    //获取单元格的值
    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            //判断单元格数据的类型，不同类型调用不同的方法
            switch (cell.getCellType()) {
                //数值类型
                case Cell.CELL_TYPE_NUMERIC:
                    //进一步判断 ，单元格格式是日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        //数值
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                case Cell.CELL_TYPE_FORMULA:{
                    try{
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }catch(IllegalStateException e){
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }

                }
                break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }



}
