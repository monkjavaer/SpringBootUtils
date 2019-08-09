package com.car.base.common.utilities;

import com.car.base.common.exception.StringListFormatException;

import java.util.ArrayList;

/**
 * Handle complex string list, items are also seperated by comma.
 * But an item are double-quoted, and can contain comma. e.g.,
 *  "oh,this cann't work now" ,,"",,"damaged!", "abc"
 * @author monkjavaer
 *
 */

public class ComplexStringList {
	public static void main(String []args) {
/*		String strList = ",,\"oh,this cann't work now\" , \" \", \"3\",  , \" damaged!\" , \"abc ,\" ";
		ArrayList<String> list = null;
		try{
			list = ComplexStringList.toArrayList(strList);
		} catch(StringListFormatException formatEx) {
			formatEx.printStackTrace();
		}
		int i = 0;
		for(String item: list) {
			System.out.println((i++) + ":" + item);
		}*/
		
		ArrayList<String> strList = new ArrayList<String>();
		strList.add(null);
		strList.add("222");
		strList.add(null);
		strList.add("444");
		strList.add("");
		strList.add(null);
		System.out.println(ComplexStringList.toString(strList));
	}
	
	public static ArrayList<String> toArrayList(String strList) throws StringListFormatException{
		boolean waitQuote = false;
		ArrayList<String> itemList = new ArrayList<String>();
		if(strList == null || strList.isEmpty())
		{return  itemList;}

		byte[] arr = strList.trim().getBytes();
		int cur = 0, itemStart = 0, itemEnd;

		while(cur < arr.length) {
			if(!waitQuote) {
				if(arr[cur] == '"') {
					waitQuote = true;
					itemStart = cur + 1;
				} else if(arr[cur] == ',') {
					itemList.add("");
				} else if(arr[cur] == ' '){
					// do nothing
				} else {
					throw new StringListFormatException();
				}
			} else {
				if(arr[cur] == '"') {
					itemEnd = cur - 1;
					if(itemEnd < itemStart) {
						itemList.add("");
					} else {
						itemList.add(new String(arr, itemStart, itemEnd - itemStart + 1));
					}
					waitQuote = false;
					
					while(++cur < arr.length && arr[cur] == ' ') {
						;
					}
					if(cur == arr.length)
					{break;}

					if(arr[cur] != ',') {
						throw new StringListFormatException();
					}
				}
			}
			cur++;

		}
		if(arr[cur - 1] == ',') {
			itemList.add("");
		}

		return itemList;
	}
	
	public static String toString(ArrayList<String> arrList) {
		if(arrList == null || arrList.size() == 0) {
			return "";
		}
		
		String result = "";
		boolean first = true;
		for(String item: arrList) {
			if(!first) {
				result += ",";
			}
   
			first = false;
 		
			if(item != null && !item.isEmpty()) {
				result += "\"" + item + "\"";
			}
		}
		
		return result;
	}

}
