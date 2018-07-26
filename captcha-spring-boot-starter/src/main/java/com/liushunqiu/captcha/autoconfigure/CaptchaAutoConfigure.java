package com.liushunqiu.captcha.autoconfigure;

import com.liushunqiu.captcha.CaptchaUtils;
import com.liushunqiu.captcha.condition.CheckRedisCondition;
import com.liushunqiu.captcha.properties.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(CheckRedisCondition.class)
@ConditionalOnClass(value={CaptchaUtils.class})
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfigure {

    @Autowired
    private CaptchaProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "common.captcha",value = "enabled",havingValue = "true")
    CaptchaUtils captchaUtils (){
        return  new CaptchaUtils(properties.getWidth(),properties.getHeight(),properties.getNum(),properties.isHasLineSize());
    }
}
