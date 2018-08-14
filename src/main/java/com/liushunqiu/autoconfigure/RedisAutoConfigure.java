package com.liushunqiu.autoconfigure;

import com.liushunqiu.properties.RedisProperties;
import com.liushunqiu.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;


@Configuration
@ConditionalOnClass(Jedis.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfigure {

    @Autowired
    private RedisProperties redisProperties;


    @Bean(destroyMethod = "destroy")
    @ConditionalOnMissingBean
    public RedisUtils redisUtils() {
        return new RedisUtils(redisProperties);
    }
}
