package com.m.gis.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Title: SpringContextUtil.java
 * @Package com.m.gis.springboot.utils
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月27日 下午3:03:49
 * @version V1.0
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);
	
	private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
        logger.info("------------------------------------------------------------------------------------------------------------------------------------");
        logger.info("ApplicationContext config successfully, call ApplicationContextUtil.getApplicationContext() to access applicationContext object.");
        logger.info("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     *    获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}

