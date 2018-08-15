package com.liushunqiu.strategy;

import com.liushunqiu.annotation.RateLimiter;
import com.liushunqiu.support.RedisConstant;
import com.liushunqiu.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class UrlRateLimiterStrategy implements RateLimiterStrategy {
    private static final Logger logger = LoggerFactory.getLogger(UrlRateLimiterStrategy.class);

    @Autowired
    RedisUtils client;

    @Override
    public boolean intercepter(HttpServletRequest request, RateLimiter rateLimiter) {
        //设置过期时间
        int expired = changeToSecond(rateLimiter.timeType(), rateLimiter.expired());
        //获取请求的url
        String url = request.getRequestURI();
        //获取限制次数
        long limit = rateLimiter.limit();
        try (Jedis jedis = client.jedis()){
            String redisKey = String.format(RedisConstant.LIMIT_URL, url);
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
        return true;
    }
}
