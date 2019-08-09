package com.car.base.common.excel;

import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * A handler used to create, read, modified an excel
 * @author monkjavaer
 *
 */
public class ExcelHandler {
	private String mFilePath;
	private WritableWorkbook mWorkbook;
	private WritableSheet mSheet;
	
	public ExcelHandler(String excelFilePath){
		mFilePath=excelFilePath;
		mWorkbook=null;
		mSheet=null;
	}
	
	public boolean writeOpen(){
		if(mFilePath==null||mFilePath.isEmpty()) {
            return false;
        }
						
        try {
    		File excelFile=new File(mFilePath);
    		excelFile.createNewFile(); 
	        OutputStream outputStrean=new FileOutputStream(excelFile);

			mWorkbook = Workbook.createWorkbook(outputStrean);
	        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	      
		return true;
	}
	
	
	public boolean writeClose(){
		if(mWorkbook==null) {
            return false;
        }
		
		try{
			mWorkbook.write();
			mWorkbook.close();
			return true;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	
	public boolean setSheet(String sheetName){
		if(mWorkbook==null){
			return false;
		}
		try{
			mSheet=mWorkbook.getSheet(sheetName);
			if(mSheet==null){
				int number=mWorkbook.getNumberOfSheets();
				mSheet = mWorkbook.createSheet(sheetName,number);
			}
	        return true;			
	    }catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * sheetName:excel sheet name
	 * writeList:write writeList as a row
	 * */
	public void write(String sheetName,List<String> writeList){	
		String[] writeArray = writeList.toArray(new String[writeList.size()]);
		write(sheetName,writeArray);
	}
	
	public void write(String sheetName,String[] writeArray){		
		if(!setSheet(sheetName)||writeArray==null||writeArray.length<=0) {
            return;
        }
				
		int rowCount=mSheet.getRows();
		for(int i=0;i<writeArray.length;i++){
			try {
				mSheet.addCell(new Label(i,rowCount,writeArray[i]));
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        			
		}
	}
	
	/*
	 * sheetName:excel sheet name
	 * headerList:set headerList as the head of sheet named sheetName
	 * */
	public void setHeaders(String sheetName,List<String> headerList){		
		String[] headerArray = headerList.toArray(new String[headerList.size()]);
		setHeaders(sheetName,headerArray);
	}
	
	
	public void setHeaders(String sheetName,String[] headerArray){		
		if(!setSheet(sheetName)||headerArray==null||headerArray.length<=0) {
            return;
        }
		
		try{
			WritableFont headFont = new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD); 
			WritableCellFormat headFormat = new  WritableCellFormat(headFont);
			mSheet.insertRow(0);

			for(int i=0;i<headerArray.length;i++){		
				Label label = new Label(i,0,headerArray[i],headFormat);
				mSheet.addCell(label);
			}		
		}catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
