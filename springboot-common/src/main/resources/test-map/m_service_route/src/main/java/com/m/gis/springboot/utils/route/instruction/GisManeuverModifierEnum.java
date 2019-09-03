package com.m.gis.springboot.utils.route.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisManeuverTypeEnum
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public enum GisManeuverModifierEnum {
    LEFT("left"),
    RIGHT("right"),
    SHARP_LEFT("sharp left"),
    SHARP_RIGHT("sharp right"),
    SLIGHT_LEFT("slight left"),
    SLIGHT_RIGHT("slight right"),
    STRAIGHT("straight"),
    UTURN("uturn"),
    DEFAULT("default");

    private String name;
    private final static List<Double> thresholds = new ArrayList<>(8);

    //将360度分为8个区间，每个区间对应一种拐弯类型
    static {
        for(int i=0;i<8;i++){
            thresholds.add(transAngle(360.0*i/8-360.0/16));
        }
    }

    GisManeuverModifierEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    /***
     * @Description: 转换为-180到180度
     * @Author: monkjavaer
     * @Data: 18:39 2018/8/28
     * @Param: [angle]
     * @Throws
     * @Return double
     */
    private static double transAngle(double angle){
        double temp = angle-360*(Math.floor(angle/360));
        return temp>=180?(temp-360):temp;
    }

    /***
     * @Description: 根据前后两条路的方向角确定拐弯类型
     * @Author: monkjavaer
     * @Data: 18:06 2018/8/28
     * @Param: [before, after]
     * @Throws
     * @Return com.m.gis.springboot.utils.route.instruction.GisManeuverModifierEnum
     */
    public static GisManeuverModifierEnum getModifier(Double before, Double after){
        if(before==null||after==null)
            return null;

        //转换两者的差为-180到180的区间
        Double diff = transAngle(after-before);

        if(diff>thresholds.get(0)&&diff<=thresholds.get(1))
            return STRAIGHT;
        else if(diff>thresholds.get(1)&&diff<=thresholds.get(2))
            return SLIGHT_RIGHT;
        else if(diff>thresholds.get(2)&&diff<=thresholds.get(3))
            return RIGHT;
        else if(diff>thresholds.get(3)&&diff<=thresholds.get(4))
            return SHARP_RIGHT;
        else if(diff>thresholds.get(5)&&diff<=thresholds.get(6))
            return SHARP_LEFT;
        else if(diff>thresholds.get(6)&&diff<=thresholds.get(7))
            return LEFT;
        else if(diff>thresholds.get(7)&&diff<=thresholds.get(0))
            return SLIGHT_LEFT;
        else
            return UTURN;
    }

    public boolean equals(GisManeuverTypeEnum other){
        return this.name.equalsIgnoreCase(other.getName());
    }

}
