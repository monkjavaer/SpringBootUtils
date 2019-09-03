package com.car.orbit.orbitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
//@MapperScan(value = "com.car.orbit.orbitservice.mapper")
//@tk.mybatis.spring.annotation.MapperScan("com.car.orbit.orbitservice.mapper")
public class OrbitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrbitServiceApplication.class, args);
    }

}
