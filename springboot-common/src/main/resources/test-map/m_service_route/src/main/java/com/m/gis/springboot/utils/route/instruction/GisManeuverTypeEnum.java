package com.m.gis.springboot.utils.route.instruction;

/**
 * @Title: GisManeuverTypeEnum
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/28
 * @Version: V1.0
 */
public enum GisManeuverTypeEnum {
    ARRIVE("arrive"),
    CONTINUE("continue"),
    DEPART("depart"),
    END_OF_ROAD("end of road"),
    FORK("fork"),
    MERGE("merge"),
    NEW_NAME("new name"),
    NOTIFICATION("notification"),
    OFF_RAMP("off ramp"),
    ON_RAMP("on ramp"),
    ROTARY("rotary"),
    ROUNDABOUT("roundabout"),
    ROUNDABOUT_TURN("roundabout turn"),
    TURN("turn"),
    USE_LANE("use lane");

    private String name;
    GisManeuverTypeEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
