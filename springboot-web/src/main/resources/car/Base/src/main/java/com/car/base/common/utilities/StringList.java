package com.car.base.common.utilities;

import java.util.ArrayList;

/**
 * 该类主要处理以逗号分隔的字符串与ArrayList存储的字符串的操作
 * 以逗号分隔的字符串,例如：E1776450-74EE-47DD-8D9D-B7BA3E4B0D87,61C03C8C-1267-4695-AF74-13D045E4A33C,291F0FC2-3946-42E7-B1DF-294C03AE2CBB 
 * @author monkjavaer
 *
 */
public class StringList {
	/**
	 * String items seperated by comma, an item cannot contain comma 
	 * @param strList
	 * @return
	 */
	public static ArrayList<String> toArrayList(String strList) {
		if(strList != null) {
			String[] ret = strList.split(",");
			if(ret.length > 0) {
				ArrayList<String> retList = new ArrayList(ret.length);
				for(int i = 0; i < ret.length; i ++) {
					retList.add(ret[i]);
				}
				return retList;
			}

		}
		return new ArrayList<String>();
	}

	public static String toString(ArrayList<String> arrList) {
		String ret = "";
		if(arrList != null && arrList.size() != 0) {
			for(String item: arrList) {
				ret += "," + item;
			}
			ret = ret.substring(1);
		}
		return ret;
	}

	public static String add(String strList, ArrayList<String> arrayList) {
		//if(strList == null) return null;
		if(arrayList == null) {
            return strList;
        }
		
		String result = "";
		if(strList != null) {
			result = strList.trim();		
		}

		String more = "";
		for(String str: arrayList) {
			more += "," + str;
		}

		if(!"".equals(result)) {
			result += more;
		} else {
			result = more.substring(1);
		}

		return result;
	}

	public static String delete(String strList, String item) {
		if(strList == null) {
            return null;
        }
		if(item == null) {
            return strList;
        }

		String[] list = strList.split(",");
		String ret = "";
		for(int i = 0; i < list.length; i ++) {
			if(!list[i].trim().equals(item)) {
				ret += "," + list[i];
			}
		}
		if(!"".equals(ret)) {
			ret = ret.substring(1);
		}

		return ret;
	}

	public static void main(String[] args) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("zhang");
		arr.add("wang");
		String tmp = StringList.add("1,2,3", arr);
		System.out.println(tmp);
	}
}
