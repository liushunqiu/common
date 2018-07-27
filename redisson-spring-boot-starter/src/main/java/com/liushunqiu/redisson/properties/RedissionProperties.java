package com.liushunqiu.redisson.properties;

import com.liushunqiu.redisson.support.ServerType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common.redisson")
public class RedissionProperties {
    private ServerType serverType;
}
