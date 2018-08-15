package com.liushunqiu.annotation;

import java.lang.annotation.*;

/**
 * 默认60秒内每个请求只能有100次
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {
    Type type() default Type.URL;

    TimeType timeType() default TimeType.SECOND;

    int limit() default 100;

    int expired() default 60;

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
