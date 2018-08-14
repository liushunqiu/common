package com.liushunqiu.util;

import com.alibaba.fastjson.JSON;
import com.liushunqiu.properties.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;
import java.util.Set;

public class RedisUtils {

    private final static Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private JedisPool pool;

    public RedisUtils(RedisProperties properties) {
        JedisPoolConfig config = new JedisPoolConfig();
        if (log.isDebugEnabled()){
            log.debug("读取配置信息={}",properties);
        }
        //复制配置过去
        BeanUtils.copyProperties(properties.getPool(),config);
        this.pool = new JedisPool(config,
                properties.getHost(),
                properties.getPort(),
                properties.getTimeout(),
                properties.getPassword(),
                properties.getDatabase());
    }

    public void destroy() {
        if (pool != null && !pool.isClosed()){
            pool.destroy();
        }
    }

    public Jedis jedis() {
        return pool.getResource();
    }

    public void set(String key, String value) {
        set(key, value, null);
    }

    /**
     * @param expire:单位秒
     */
    public void set(String key, String value, Integer expire) {
        this.setex(key, value, expire);
    }


    public void setex(String key, String value, Integer seconds) {
        try (Jedis jedis = pool.getResource()) {
            if (Objects.nonNull(seconds)) {
                jedis.setex(key, seconds, value);
            } else{
                jedis.set(key, value);
            }
        }
    }

    public void del(String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    public String get(String key, String defualtValue) {
        try (Jedis jedis = pool.getResource()) {
            String value = jedis.get(key);
            if (value == null) {
                value = defualtValue;
            }
            return value;
        }
    }

    public String get(String key) {
        return get(key, null, String.class);
    }

    public Long ttl(String key) {
        try (Jedis jedis = pool.getResource()) {
            Long ttl = jedis.ttl(key);
            return ttl;
        }
    }

    public <T> T get(String key, String defualtValue, Class<T> clazz) {
        String value = get(key, defualtValue);
        if (null == value) {
            return null;
        }
        if (clazz == String.class) {
            return (T) value;
        }
        return JSON.parseObject(value, clazz);// 支持基本类型,支持null转换

    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, null, clazz);
    }

    /**
     * 自增
     **/
    public Long incr(String key) {
        try (Jedis jedis = pool.getResource()) {
            Long value = jedis.incr(key);
            return value;// 默认从1开始,由于redis-server是单线程的,所以不会有脏数据问题
        }
    }

    /**
     * 自减
     **/
    public Long decr(String key) {
        try (Jedis jedis = pool.getResource()) {
            Long value = jedis.decr(key);
            return value;// 默认从1开始,由于redis-server是单线程的,所以不会有脏数据问题
        }
    }

    /**
     * set列表增加值
     */
    public void sadd(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    /**
     * set列表增加值
     */
    public void sadd(String key, String... value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    /**
     * set列表删除值
     */
    public void srem(String key, String... value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(key, value);
        }
    }

    /**
     * set列表随机获取一个值
     */
    public String spop(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.spop(key);
        }
    }

    /**
     * set列表随机获取一个值
     */
    public <T> T spop(String key, Class<T> clazz) {
        String value = spop(key);
        if (clazz == String.class) {
            return (T) value;
        }
        return JSON.parseObject(value, clazz);
    }

    /**
     * set列表的数据个数
     */
    public Long scard(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.scard(key);
        }
    }

    /**
     * set
     */
    public String hget(String key, String field) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    /**
     * set
     */
    public <T> T hget(String key, String field, Class<T> clazz) {
        String value = hget(key, field);
        if (null == value) {
            return null;
        }
        if (clazz == String.class) {
            return (T) value;
        }
        return JSON.parseObject(value, clazz);// 支持基本类型,支持null转换
    }

    /**
     * set列表的数据个数
     */
    public Long hset(String key, String field, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hset(key, field, value);
        }
    }

    /**
     * hdel 删除
     */
    public Long hdel(String key, String... fields) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hdel(key, fields);
        }
    }

    /**
     * zadd
     */
    public Long zadd(String key, String member, double score) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    /**
     * zrange
     */
    public Set<String> zrange(String key, Long start, Long end) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    /**
     * zrevrange
     */
    public Set<String> zrevrange(String key, Long start, Long end) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    /**
     * zrevrange
     */
    public Double zincrby(String key, String member, double score) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    /**
     * zrem
     */
    public Long zrem(String key, String... member) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrem(key, member);
        }
    }


    public Long expires(String key, Integer expires_in) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.expire(key, expires_in);
        }
    }
}
