package com.liushunqiu.redisson.autoconfigure;

import com.liushunqiu.redisson.properties.RedissonProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Configuration
@ConditionalOnClass(Redisson.class)
@ConditionalOnProperty(prefix = "spring.redisson",value = "enabled",havingValue = "true")
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfigure {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedissonProperties redissonProperties;

    public Config configJson() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getJson());
        return Config.fromJSON(file);
    }

    public Config configYaml() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getYaml());
        return Config.fromYAML(file);
    }

    @Bean
    @ConditionalOnMissingBean
    public Config config() throws IOException {
        if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getJson())) {
            return configJson();
        } else if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getYaml())) {
            return configYaml();
        } else {
            throw new RuntimeException("请提供redisson的json/yaml格式的配置文件");
        }
    }

    @Bean(destroyMethod="shutdown")
    @ConditionalOnClass(Config.class)
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(Config config) throws IOException {
        logger.info("创建RedissonClient,配置为 = {}", config.toJSON());
        return Redisson.create(config);
    }
}
