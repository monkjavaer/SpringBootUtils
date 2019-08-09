package com.car.trunk.util;


public class StringFormatUtil {
    public static String formatRetrieveString(String str) {
        str = str.replaceAll("\\[", "[[]");
        str = str.replaceAll("\\_", "[_]");
        str = str.replaceAll("\\%", "[%]");
        str = str.replaceAll("\\{", "[]");
        str = str.replaceAll("'", "");
        return str;
    }

    public static void main(String[] args) {
        System.out.println(formatRetrieveString("_%%_"));
    }
}
