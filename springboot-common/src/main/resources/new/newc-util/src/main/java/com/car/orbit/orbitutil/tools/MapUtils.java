package com.car.orbit.orbitutil.tools;

import java.util.*;

/**
 * CreateDate: 2019-3-21 <br/>
 * Author: monkjavaer <br/>
 * Description: 封装常用的Map转换操作，包括计算最大值、最小值等
 * Version: 1.0
 **/
public class MapUtils {

    private static List<Map.Entry<Object,Integer>> sortMap(Map<? extends Object, Integer> map){
        List<Map.Entry<Object,Integer>> list = new ArrayList(map.entrySet());
        Collections.sort(list, Comparator.comparingInt(Map.Entry::getValue));
        return  list;
    }

     /**
     * 获取Map对象中值最小的Value对应的Key
     *
     * @param map
     */
    public static Object getMinValueKey(Map<? extends Object, Integer> map) {
        if (map == null) return null;
        List<Map.Entry<Object,Integer>> list = sortMap(map);
        return list.get(0).getKey();
    }

    /**
     * 获取Map对象中值最大的Value对应的Key
     *
     * @param map
     */
    public static Object getMaxValueKey(Map<? extends Object, Integer> map) {
        if (map == null) return null;
        List<Map.Entry<Object,Integer>> list = sortMap(map);
        return list.get(list.size()-1).getKey();
    }

}
