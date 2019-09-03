package com.base.springboot.car.NodeService.src.main.java.com.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 节点服务入口
 * @author monkjavaer
 * @date 2018/8/15 10:28
 */
public class NodeBoot {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(NodeBoot.class);

    /**
     * xml配置文件。
     */
    private static String[] strPublicXml = new String[]
            { "classpath:applicationContext.xml"};

    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args) {
        logger.info("==============start================");
        try {
            AbstractApplicationContext ctx = new FileSystemXmlApplicationContext(strPublicXml);
            StartAppliction startAppliction = (StartAppliction) ctx.getBean("startAppliction");
            startAppliction.getStart();
            // 注册关闭函数钩子，随JVM关闭而关闭
            ctx.registerShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception:"+e.getMessage(),e);
        }
    }

}
