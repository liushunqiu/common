package com.liushunqiu.redisson.support;

public enum  ServerType {
    /**
     * 部署方式
     */
    single("单机"),
    master_slave("主从"),
    sentinel("哨兵"),
    cluster("集群");

    private String name;

    ServerType(String name) {
        this.name = name;
    }
}
