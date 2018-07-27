package com.liushunqiu.redisson.autoconfigure;

import com.liushunqiu.redisson.RedissionUtils;
import com.liushunqiu.redisson.properties.RedissionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value={RedissionUtils.class})
@EnableConfigurationProperties()
public class RedissonAutoConfigure {

    @Autowired
    private RedissionProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "common.redisson",value = "enabled",havingValue = "true")
    RedissionUtils redissionUtils() {
        return new RedissionUtils();
    }
}
