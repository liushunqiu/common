package com.liushunqiu.captcha.condition;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CheckRedisCondition implements Condition {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //如果没有加入redis配置的就返回false
        String property = context.getEnvironment().getProperty("spring.redis.host");
        if (StringUtils.isEmpty(property)){
            logger.error("图形验证需要依赖spring-boot-starter-data-redis!");
            return false ;
        }else {
            return true;
        }
    }
}
