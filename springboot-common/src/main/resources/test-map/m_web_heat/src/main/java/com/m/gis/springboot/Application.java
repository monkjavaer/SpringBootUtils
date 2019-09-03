package com.m.gis.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScan;

/**
 * @Title: Application
 * @Package: com.m.gis.springboot
 * @Description: 启动类
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
@SpringBootApplication
@MapperScan("com.m.gis.springboot.mapper")
@EnableCaching
@EnableAsync
public class Application {
    
    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }
}
