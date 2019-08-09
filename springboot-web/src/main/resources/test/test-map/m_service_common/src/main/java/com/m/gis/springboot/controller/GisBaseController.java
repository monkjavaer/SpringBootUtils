package com.m.gis.springboot.controller;

import com.m.gis.springboot.service.GisDictService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: GisHelloController
 * @Package: com.m.gis.springboot.controller
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
public class GisBaseController {
    @Autowired
    protected GisDictService gisDictService;
}
