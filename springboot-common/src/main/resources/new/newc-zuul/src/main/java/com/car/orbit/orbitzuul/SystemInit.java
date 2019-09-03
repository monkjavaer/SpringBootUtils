package com.car.orbit.orbitzuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * CreateDate：2019/3/22 <br/>
 * Author：monkjavaer <br/>
 * Description: 系统初始化类
 **/
@Component
@Configuration
public class SystemInit {

    @Autowired
    private Environment env;

    /**
     * MultiPartFile在接收时，需要有一个临时的路径，如果使用默认的，这个路径会被删除，此处指定一个绝对路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String multiPath = "";
        String OSName = System.getProperty("os.name");
        if (OSName.toLowerCase().contains("windows")) {
            multiPath = env.getProperty("tmp.multipart.path.windows");
        } else if (OSName.toLowerCase().contains("linux")) {
            multiPath = env.getProperty("tmp.multipart.path.linux");
        }
        File file = new File(multiPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        factory.setLocation(multiPath);
        return factory.createMultipartConfig();
    }
}