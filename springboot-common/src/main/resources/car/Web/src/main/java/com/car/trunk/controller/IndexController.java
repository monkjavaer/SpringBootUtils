package com.base.springboot.car.Web.src.main.java.com.car.trunk.controller;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class IndexController {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostConstruct
    void init() {
        System.out.println("=======启动时加载IndexController========" + this.toString());
        threadPoolTaskExecutor.execute(new Task());
    }

    private class Task implements Runnable {

        @Override
        public void run() {

        }
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }
}
