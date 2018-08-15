package com.liushunqiu.strategy;

import com.liushunqiu.annotation.RateLimiter;

import javax.servlet.http.HttpServletRequest;

public interface RateLimiterStrategy {

    boolean intercepter(HttpServletRequest request,RateLimiter rateLimiter);

    default int changeToSecond(RateLimiter.TimeType timeType, int max) {
        int expired = 0;
        switch (timeType) {
            case MINUTE:
                expired = max * 60;
                break;
            case HOUR:
                expired = max * 60 * 60;
                break;
            case DAY:
                expired = max * 60 * 60 * 24;
                break;
            case SECOND:
            default:
                expired = max;
                break;
        }
        return expired;
    }
}
