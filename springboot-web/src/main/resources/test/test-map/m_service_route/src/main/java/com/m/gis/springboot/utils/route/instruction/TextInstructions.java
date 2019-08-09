package com.m.gis.springboot.utils.route.instruction;

import com.alibaba.fastjson.JSONObject;
import com.m.gis.springboot.common.utils.JsonUtils;
import com.m.gis.springboot.common.utils.PropertiesUtil;
import com.m.gis.springboot.exception.GisRouteTextInstructionsException;
import com.mapbox.services.api.directions.v5.models.IntersectionLanes;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.commons.utils.TextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class TextInstructions {

  private static final Logger logger = LoggerFactory.getLogger(TextInstructions.class);

  private TokenizedInstructionHook tokenizedInstructionHook;

  private JSONObject rootObject;
  private JSONObject versionObject;

  public TextInstructions(String language, String version) {
    // Load the resource
    String localPath = String.format("translations/%s.json", language);
    // Parse the JSON content
    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(localPath);
    if (ins == null) {
      throw new GisRouteTextInstructionsException("Translation not found for language: " + language);
    }

    rootObject = JsonUtils.toJSONObject(PropertiesUtil.getStringFromInputStream(ins));

    versionObject = rootObject.getJSONObject(version);
    if (versionObject == null) {
      throw new GisRouteTextInstructionsException("Version not found for value: " + version);
    }
  }

  public TokenizedInstructionHook getTokenizedInstructionHook() {
    return tokenizedInstructionHook;
  }

  public void setTokenizedInstructionHook(TokenizedInstructionHook tokenizedInstructionHook) {
    this.tokenizedInstructionHook = tokenizedInstructionHook;
  }

  public JSONObject getRootObject() {
    return rootObject;
  }

  public JSONObject getVersionObject() {
    return versionObject;
  }

  public static String capitalizeFirstLetter(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  /***
   * @Description: 获取序号
   * @Author: monkjavaer
   * @Data: 20:09 2018/8/1
   * @Param: [number]
   * @Throws 
   * @Return java.lang.String
   */ 
  public String ordinalize(Integer number) {
    try {
      String ordinalize = getVersionObject().getJSONObject("constants").getJSONObject("ordinalize").getString(String.valueOf(number));
      return ordinalize == null ? "":ordinalize;
    } catch (Exception exception) {
      return "";
    }
  }

  /***
   * @Description: 获取方向
   * @Author: monkjavaer
   * @Data: 20:11 2018/8/1
   * @Param: [degree]
   * @Throws 
   * @Return java.lang.String
   */ 
  public String directionFromDegree(Double degree) {
    if(degree==null){
      // step had no bearing_after degree, ignoring
      return "";
    }
    //转换为0-360
    degree = degree-360*(Math.floor(degree/360));

    if (degree >= 0 && degree <= 20) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("north");
    } else if (degree > 20 && degree < 70) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("northeast");
    } else if (degree >= 70 && degree <= 110) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("east");
    } else if (degree > 110 && degree < 160) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("southeast");
    } else if (degree >= 160 && degree <= 200) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("south");
    } else if (degree > 200 && degree < 250) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("southwest");
    } else if (degree >= 250 && degree <= 290) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("west");
    } else if (degree > 290 && degree < 340) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("northwest");
    } else if (degree >= 340 && degree <= 360) {
      return getVersionObject().getJSONObject("constants").getJSONObject("direction")
        .getString("north");
    } else {
      throw new GisRouteTextInstructionsException("Degree is invalid: " + degree);
    }
  }

  /***
   * @Description: 确定路的行驶
   * @Author: monkjavaer
   * @Data: 20:19 2018/8/1
   * @Param: [step]
   * @Throws 
   * @Return java.lang.String
   */ 
  public String laneConfig(LegStep step) {
    if (step.getIntersections() == null
      || step.getIntersections().size() == 0
      || step.getIntersections().get(0).getLanes() == null
      || step.getIntersections().get(0).getLanes().length == 0) {
      throw new GisRouteTextInstructionsException("No lanes object");
    }

    StringBuilder config = new StringBuilder();
    Boolean currentLaneValidity = null;
    for (IntersectionLanes lane : step.getIntersections().get(0).getLanes()) {
      if (currentLaneValidity == null || currentLaneValidity != lane.getValid()) {
        if (lane.getValid()) {
          config.append("o");
        } else {
          config.append("x");
        }
        currentLaneValidity = lane.getValid();
      }
    }

    return config.toString();
  }


  /***
   * @Description: 根据定义规则生成路径描述，只有一个目的地
   * @Author: monkjavaer
   * @Data: 16:37 2018/9/4
   * @Param: [step]
   * @Throws
   * @Return java.lang.String
   */
  public String compile(LegStep step){
    return compile(step,-1);
  }


  /***
   * @Description: 根据定义规则生成路径描述，nthWaypoint表示第几个目的地
   * @Author: monkjavaer
   * @Data: 16:37 2018/9/4
   * @Param: [step, nthWaypoint]
   * @Throws
   * @Return java.lang.String
   */
  public String compile(LegStep step,Integer nthWaypoint) {
    if (step.getManeuver() == null) {
      throw new GisRouteTextInstructionsException("No step maneuver provided.");
    }

    String type = step.getManeuver().getType();
    String modifier = step.getManeuver().getModifier();
    String mode = step.getMode();

    if (TextUtils.isEmpty(type)) {
      throw new GisRouteTextInstructionsException("Missing step maneuver type.");
    }

    if (!type.equals("depart") && !type.equals("arrive") && TextUtils.isEmpty(modifier)) {
      throw new GisRouteTextInstructionsException("Missing step maneuver modifier.");
    }

    if (getVersionObject().getJSONObject(type) == null) {
      // Log for debugging
      logger.error("Encountered unknown instruction type: " + type);

      // OSRM specification assumes turn types can be added without
      // major version changes. Unknown types are to be treated as
      // type `turn` by clients
      type = "turn";
    }

    // Use special instructions if available, otherwise `defaultinstruction`
    JSONObject instructionObject;
    JSONObject modeValue = getVersionObject().getJSONObject("modes").getJSONObject(mode);
    if (modeValue != null) {
      instructionObject = modeValue;
    } else {
      JSONObject modifierValue = getVersionObject().getJSONObject(type).getJSONObject(modifier);
      instructionObject = modifierValue == null
        ? getVersionObject().getJSONObject(type).getJSONObject("default")
        : modifierValue;
    }

    // Special case handling
    String laneInstruction = null;
    switch (type) {
      case "use lane":
        laneInstruction = getVersionObject().getJSONObject("constants")
          .getJSONObject("lanes").getString(laneConfig(step));
        if (laneInstruction == null) {
          // If the lane combination is not found, default to continue straight
          instructionObject = getVersionObject().getJSONObject("use lane")
            .getJSONObject("no_lanes");
        }
        break;
      case "rotary":
      case "roundabout":
        if (!TextUtils.isEmpty(step.getRotaryName())
          && step.getManeuver().getExit() != null
          && instructionObject.getJSONObject("name_exit") != null) {
          instructionObject = instructionObject.getJSONObject("name_exit");
        } else if (step.getRotaryName() != null && instructionObject.getJSONObject("name") != null) {
          instructionObject = instructionObject.getJSONObject("name");
        } else if (step.getManeuver().getExit() != null && instructionObject.getJSONObject("exit") != null) {
          instructionObject = instructionObject.getJSONObject("exit");
        } else {
          instructionObject = instructionObject.getJSONObject("default");
        }
        break;
      default:
        // NOOP, since no special logic for that type
    }

    // Decide way_name with special handling for name and ref
    String wayName;
    String name = TextUtils.isEmpty(step.getName()) ? "" : step.getName();
    String ref = TextUtils.isEmpty(step.getRef()) ? "" : step.getRef().split(";")[0];

    // Remove hacks from Mapbox Directions mixing ref into name
    if (name.equals(step.getRef())) {
      // if both are the same we assume that there used to be an empty name, with the ref being filled in for it
      // we only need to retain the ref then
      name = "";
    }
    name = name.replace(" (" + step.getRef() + ")", "");

    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(ref) && !name.equals(ref)) {
      wayName = name + " (" + ref + ")";
    } else if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(ref)) {
      wayName = ref;
    } else {
      wayName = name;
    }

    // Decide which instruction string to use
    // Destination takes precedence over name
    String instruction;
    if(type.equals("arrive")&&StringUtils.isBlank(ordinalize(nthWaypoint))){
      instruction = instructionObject.getString("no ordinalize");
      if(StringUtils.isBlank(instruction))
        instruction = instructionObject.getString("default");
    }
    else{
      if (!TextUtils.isEmpty(step.getDestinations())
              && instructionObject.getString("destination") != null) {
        instruction = instructionObject.getString("destination");
      } else if (!TextUtils.isEmpty(wayName)
              && instructionObject.getString("name") != null) {
        instruction = instructionObject.getString("name");
      } else {
        instruction = instructionObject.getString("default");
      }

      if (getTokenizedInstructionHook() != null) {
        instruction = getTokenizedInstructionHook().change(instruction);
      }
    }

    // Replace tokens
    // NOOP if they don't exist
    String nthWaypointDes = ordinalize(nthWaypoint);
    String modifierValue =
      getVersionObject().getJSONObject("constants").getJSONObject("modifier").getString(modifier);
    instruction = instruction
      .replace("{way_name}", wayName)
      .replace("{destination}", TextUtils.isEmpty(step.getDestinations()) ? "" : step.getDestinations().split(",")[0])
      .replace("{exit_number}",
        step.getManeuver().getExit() == null ? ordinalize(1) : ordinalize(step.getManeuver().getExit()))
      .replace("{rotary_name}", TextUtils.isEmpty(step.getRotaryName()) ? "" : step.getRotaryName())
      .replace("{lane_instruction}", laneInstruction == null ? "" : laneInstruction)
      .replace("{modifier}", modifierValue == null ? "" : modifierValue)
      .replace("{direction}", directionFromDegree(step.getManeuver().getBearingAfter()))
      .replace("{nth}", nthWaypointDes)
      .replaceAll("\\s+", " "); // remove excess spaces

    if (getRootObject().getJSONObject("meta").getBoolean("capitalizeFirstLetter")) {
      instruction = capitalizeFirstLetter(instruction);
    }

    return instruction;
  }



}
