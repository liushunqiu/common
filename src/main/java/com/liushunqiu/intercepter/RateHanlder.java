package com.liushunqiu.intercepter;

import com.liushunqiu.annotation.RateLimiter;
import com.liushunqiu.strategy.RateLimiterStrategy;

import javax.servlet.http.HttpServletRequest;

public class RateHanlder {

    private RateLimiterStrategy rateLimiter;

    public RateHanlder(RateLimiterStrategy rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public boolean rate(HttpServletRequest request, RateLimiter rateLimiter){
        return this.rateLimiter.intercepter(request,rateLimiter);
    }
}
