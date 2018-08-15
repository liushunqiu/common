package com.liushunqiu.intercepter;

import com.liushunqiu.annotation.RateLimiter;
import com.liushunqiu.strategy.IpRateLimiterStrategy;
import com.liushunqiu.strategy.RateLimiterStrategy;
import com.liushunqiu.strategy.UrlRateLimiterStrategy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class RateLimiterIntercepter extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (Objects.nonNull(rateLimiter)) {
            RateLimiterStrategy strategy = null;
            switch (rateLimiter.type()){
                case IP:
                    strategy = new IpRateLimiterStrategy();
                    break;
                case URL:
                default:
                    strategy = new UrlRateLimiterStrategy();
                    break;
            }
            return new RateHanlder(strategy).rate(request,rateLimiter);
        }
        return true;
    }


}
