package com.liushunqiu.strategy;

import com.liushunqiu.annotation.RateLimiter;
import com.liushunqiu.support.RedisConstant;
import com.liushunqiu.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class IpRateLimiterStrategy implements RateLimiterStrategy {
    private static final Logger logger = LoggerFactory.getLogger(IpRateLimiterStrategy.class);
    @Autowired
    RedisUtils client;
    @Override
    public boolean intercepter(HttpServletRequest request, RateLimiter rateLimiter) {
        //设置过期时间
        int expired = changeToSecond(rateLimiter.timeType(), rateLimiter.expired());
        //获取用户IP
        String ip = getIpAdrress(request);
        //获取限制次数
        long limit = rateLimiter.limit();
        try (Jedis jedis = client.jedis()){
            String redisKey = String.format(RedisConstant.LIMIT_IP, ip);
            if (Objects.isNull(jedis.get(redisKey))) {
                if (logger.isDebugEnabled()){
                    logger.debug("当前没有数据,即将添加数据limit={},expired={}", limit, expired);
                }
                jedis.setex(redisKey,expired,"0");
            } else {
                long t = jedis.incr(redisKey);
                if (t > limit) {
                    if (logger.isDebugEnabled()){
                        logger.debug("ip={}已经达到={}过期时间内限流次数={}",ip,expired,limit);
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


    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    private static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

}
