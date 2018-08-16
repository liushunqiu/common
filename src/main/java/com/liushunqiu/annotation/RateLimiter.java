package com.liushunqiu.annotation;

import java.lang.annotation.*;

/**
 * 默认每秒内每个请求只能有1000次
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {
    Type type() default Type.URL;

    TimeType timeType() default TimeType.SECOND;

    int limit() default 1000;

    int expired() default 1;

    enum Type {
        //限制IP
        IP,
        //限制url
        URL
    }

    enum TimeType {
        //秒
        SECOND,
        //分钟
        MINUTE,
        //小时
        HOUR,
        //天
        DAY
    }
}
