package com.liushunqiu.intercepter;

import com.liushunqiu.annotation.RateLimiter;
import com.liushunqiu.support.RedisConstant;
import com.liushunqiu.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class RateLimiterIntercepter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterIntercepter.class);

    @Autowired
    RedisUtils client;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (Objects.nonNull(rateLimiter)) {
            //设置过期时间
            int expired = changeToSecond(rateLimiter.timeType(), rateLimiter.max());
            //获取请求的url
            String url = request.getRequestURI();
            //获取限制次数
            long limit = rateLimiter.limit();
            try (Jedis jedis = client.jedis()){
                String redisKey = String.format(RedisConstant.LIMIT_PATH, url);
                if (Objects.isNull(jedis.get(redisKey))) {
                    if (logger.isDebugEnabled()){
                        logger.debug("当前没有数据,即将添加数据limit={},expired={}", limit, expired);
                    }
                    jedis.setex(redisKey,expired,"0");
                } else {
                    long t = jedis.incr(redisKey);
                    if (t > limit) {
                        if (logger.isDebugEnabled()){
                            logger.debug("接口={}已经达到={}过期时间内限流次数={}",url,expired,limit);
                        }
                        return false;
                    }
                    if (jedis.ttl(redisKey) == -1L){
                        jedis.expire(redisKey,expired);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 转化成秒
     *
     * @param timeType
     * @param max
     * @return
     */
    private int changeToSecond(RateLimiter.TimeType timeType, int max) {
        int init = 0;
        switch (timeType) {
            case MINUTE:
                init = max * 60;
                break;
            case HOUR:
                init = max * 60 * 60;
                break;
            case DAY:
                init = max * 60 * 60 * 24;
                break;
            case SECOND:
            default:
                init = max;
                break;
        }
        return init;
    }
}
