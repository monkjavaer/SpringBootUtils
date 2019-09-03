package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Title: ExportColorUtil
 * @Package: com.car.trunk.center.vehiclecontrol.service
 * @Description: 检查 ExportColorUtil
 * @Author: monkjavaer
 * @Date: 2019/6/4 19:25
 * @Version: V1.0
 */
public class ExportColorUtil {

    /**车牌颜色(白色-0, 黄色-1, 蓝色-2, 黑色-3, 其他-4, 绿色-5, 红色-6)*/
    public static int white = 0;
    public static int yellow = 1;
    public static int blue = 2;
    public static int black = 3;
    public static int other = 4;
    public static int green = 5;
    public static int red = 6;

    /**车身颜色（白色-A,灰色-B,黄色-C,粉色-D,红色-E,紫色-F,绿色-G,蓝色-H）*/
    public static String whitePlate = "A";
    public static String grayPlate = "B";
    public static String yellowPlate = "C";
    public static String pinkPlate = "D";
    public static String redPlate = "E";
    public static String purplePlate = "F";
    public static String greenPlate = "G";
    public static String bluePlate = "H";

    /**车辆类型(1-小型车,2-中型车,3-大型车,4-其他)*/
    public static int small = 1;
    public static int mid = 2;
    public static int big = 3;
    public static int otherCar = 4;


    /**
     * 检查导入车牌颜色
     * @param color color
     * @return boolean
     */
    public static boolean checkPlateColor(String color) throws Exception {
        List<Integer> list = Arrays.asList(white,yellow,blue,black,other,green,red);
        return list.contains(Integer.valueOf(color));
    }

    /**
     * 检查导入车身颜色
     * @param color
     * @return
     */
    public static boolean checkVehicleColor(String color) throws Exception {
        List<String> list = Arrays.asList(whitePlate,grayPlate,yellowPlate,pinkPlate,redPlate,purplePlate,greenPlate,bluePlate);
        return list.contains(color);
    }

    /**
     * 检查导入车型
     * @param type
     * @return
     */
    public static boolean checkCarType(String type) throws Exception{
        List<Integer> list = Arrays.asList(small,mid,big,otherCar);
        return list.contains(Integer.valueOf(type));
    }

}
