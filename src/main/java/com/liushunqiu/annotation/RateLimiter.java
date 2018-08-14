package com.liushunqiu.annotation;

import java.lang.annotation.*;

/**
 * 基于url的限流,默认60秒内每个请求只能有100次
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {

    TimeType timeType() default TimeType.SECOND;

    int limit() default 100;

    int max() default 60;

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
