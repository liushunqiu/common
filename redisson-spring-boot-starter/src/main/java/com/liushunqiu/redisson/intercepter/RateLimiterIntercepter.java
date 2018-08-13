package com.liushunqiu.redisson.intercepter;

import com.liushunqiu.redisson.annotation.RateLimiter;
import com.liushunqiu.redisson.support.RedisConstant;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RateLimiterIntercepter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterIntercepter.class);

    @Autowired
    RedissonClient client;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (Objects.nonNull(rateLimiter)){
            //设置过期时间
            long expired =  changeToSecond(rateLimiter.timeType(),rateLimiter.max());
            //获取请求的url
            String url = request.getRequestURI();
            //获取限制次数
            long limit = rateLimiter.limit();
            RBucket<Long> limitBucket = client.getBucket(String.format(RedisConstant.LIMIT_PATH,url));
            RLock lock = client.getLock(String.format(RedisConstant.LOCK_LIMIT_PATH,url));
            lock.lock();
            try {
                if (Objects.isNull(limitBucket.get())){
                    logger.info("当前没有数据,即将添加数据limit={},expired={}",limit,expired);
                    limitBucket.set(limit,expired,TimeUnit.SECONDS);
                }else{
                    long t;
                    if ((t = limitBucket.get()) <= 1){
                        logger.info("已经达到最大请求");
                        return false;
                    }
                    logger.info("请求成功t={}",t);
                    limitBucket.set(--t);
                }
            }finally {
                lock.unlock();
            }
        }
        return true;
    }

    /**
     * 转化成秒
     * @param timeType
     * @param max
     * @return
     */
    private long changeToSecond(RateLimiter.TimeType timeType,long max){
        long init = 0;
        switch (timeType){
            case MINUTE:
                init = max * 60;
                break;
            case HOUR:
                init = max * 60 * 60;
                break;
            case DAY:
                init  = max * 60 * 60 * 24;
                break;
            case SECOND:
            default:
                init = max;
                break;
        }
        return init;
    }
}
