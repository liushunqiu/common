package com.liushun.test.config;


import com.liushunqiu.redisson.intercepter.RateLimiterIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    RateLimiterIntercepter rateLimiterIntercepter(){
        return new RateLimiterIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimiterIntercepter());
    }
}
