package com.liushunqiu.autoconfigure;

import com.liushunqiu.properties.CaptchaProperties;
import com.liushunqiu.util.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@ConditionalOnClass(value={CaptchaUtils.class ,Jedis.class})
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfigure {
    @Autowired
    private CaptchaProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "common.captcha",value = "enabled",havingValue = "true")
    CaptchaUtils captchaUtils (){
        return  new CaptchaUtils(properties.getNum(),properties.getWidth(),properties.getHeight());
    }
}
