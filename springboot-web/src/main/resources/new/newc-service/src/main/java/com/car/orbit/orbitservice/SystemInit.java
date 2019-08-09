package com.car.orbit.orbitservice;

import com.car.orbit.orbitutil.es.ESUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * CreateDate：2019/3/9 <br/>
 * Author：monkjavaer <br/>
 * Description: 初始化类，用于建立连接、从数据库获取某些可以加载到内存的数据等
 **/
@Component
public class SystemInit {

    /** logger */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** config param environment */
    @Autowired
    private Environment env;

    /**
     * get redis client and autowired to bean
     *
     * @return redis client
     */
    @Bean
    RedisClient redisClient() {
        JedisPoolConfig config = new JedisPoolConfig();
        if (env.containsProperty("spring.redis.pool.max-active")) {
            config.setMaxTotal(Integer.valueOf(env.getProperty("spring.redis.pool.max-active")));
        }
        if (env.containsProperty("spring.redis.pool.max-idle")) {
            config.setMaxIdle(Integer.valueOf(env.getProperty("spring.redis.pool.max-idle")));
        }
        if (env.containsProperty("spring.redis.pool.max-wait")) {
            config.setMaxWaitMillis(Long.valueOf(env.getProperty("spring.redis.pool.max-wait")));
        }
        if (env.containsProperty("spring.redis.pool.testOnBorrow")) {
            config.setTestOnBorrow(Boolean.valueOf(env.getProperty("spring.redis.pool.testOnBorrow")));
        }
        if (env.containsProperty("spring.redis.pool.testOnReturn")) {
            config.setTestOnReturn(Boolean.valueOf(env.getProperty("spring.redis.pool.testOnReturn")));
        }
        String host = env.getProperty("spring.redis.host");
        int port = Integer.valueOf(env.getProperty("spring.redis.port"));
        int timeout = Integer.valueOf(env.getProperty("spring.redis.timeout"));
        String password = env.getProperty("spring.redis.password");
        RedisClient redisClient = new RedisClient(config, host, port, timeout, password, 0);
        //        for (ERedisKey eRedisKey : ERedisKey.values()) {
        //            redisClient.delete(eRedisKey.getKey());
        //        }
        return redisClient;
    }

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

    @Bean
    TransportClient test() {
        //初始化ES连接池
        try {
            ESUtils.initClient();
        } catch (Exception e) {
            logger.error("*****************************************************************************************");
            logger.error("unable to start server because unable to connect es(IP:{}, port:{})", PropertyReaderUtils.getProValue("es.host.ip"), PropertyReaderUtils.getProValue("es.host.port"));
            logger.error("*****************************************************************************************");
        }
        return ESUtils.getClient();
    }

    @Bean
    public ThreadPoolTaskExecutor asyncServiceExecutor(){
        logger.info("Start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(10);
        //配置最大线程数
        executor.setMaxPoolSize(200);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(5);
        //配置队列大小
        executor.setQueueCapacity(500);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}