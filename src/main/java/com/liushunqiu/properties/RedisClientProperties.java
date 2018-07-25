package com.liushunqiu.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("common.redis")
public class RedisClientProperties {

    private Map<String,Object> poolConfig;

    private String host;

    private int port;

    private int timeout;

    private String password;

    private int database;

    public Map<String, Object> getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(Map<String, Object> poolConfig) {
        this.poolConfig = poolConfig;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
