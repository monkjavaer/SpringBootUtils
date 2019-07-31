package com.base.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author monkjavaer
 * @date 2019/7/31 11:42
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @RequestMapping("/welcome")
    public String welcome(){
        return "/welcome";
    }

}
