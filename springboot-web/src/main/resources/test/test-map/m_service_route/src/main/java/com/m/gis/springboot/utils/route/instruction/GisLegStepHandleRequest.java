package com.m.gis.springboot.utils.route.instruction;

import com.m.gis.springboot.po.GisRouteRoad;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepManeuver;

/**
 * @Title: GisLegStepHandleRequest
 * @Package: com.m.gis.springboot.utils.route.instruction
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/29
 * @Version: V1.0
 */
public class GisLegStepHandleRequest {
    private LegStep legStep;
    private GisRouteRoad previous;
    private GisRouteRoad current;
    private GisRouteRoad next;
    private LegStep previousLegStep;

    public GisLegStepHandleRequest(){
        this.legStep = new LegStep();
        this.legStep.setManeuver(new StepManeuver());
    }

    public GisLegStepHandleRequest(GisRouteRoad previous, GisRouteRoad current, GisRouteRoad next) {
        this.legStep = new LegStep();
        this.legStep.setManeuver(new StepManeuver());
        this.previous = previous;
        this.current = current;
        this.next = next;
    }

    public LegStep getLegStep() {
        return legStep;
    }

    public void setLegStep(LegStep legStep) {
        this.legStep = legStep;
    }

    public GisRouteRoad getPrevious() {
        return previous;
    }

    public void setPrevious(GisRouteRoad previous) {
        this.previous = previous;
    }

    public GisRouteRoad getCurrent() {
        return current;
    }

    public void setCurrent(GisRouteRoad current) {
        this.current = current;
    }

    public GisRouteRoad getNext() {
        return next;
    }

    public void setNext(GisRouteRoad next) {
        this.next = next;
    }

    public LegStep getPreviousLegStep() {
        return previousLegStep;
    }

    public void setPreviousLegStep(LegStep previousLegStep) {
        this.previousLegStep = previousLegStep;
    }
}
