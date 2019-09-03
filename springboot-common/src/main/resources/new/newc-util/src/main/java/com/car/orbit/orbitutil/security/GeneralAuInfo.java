package com.car.orbit.orbitutil.security;

import com.car.orbit.orbitutil.tools.RandomStrGenerator;

/**
 * CreateDate：2019/3/9 <br/>
 * Author：monkjavaer <br/>
 * Description:
 **/
public class GeneralAuInfo {

    /** after zuul processed, the label key will be added */
    public static final String ZUUL_PROCESS_LABEL_KEY = "from_zuul_label";

    /** after zuul processed, the label value will be added */
    public static final String ZUUL_PROCESS_LABEL_VALUE = RandomStrGenerator.generateRandomStr(16);
}