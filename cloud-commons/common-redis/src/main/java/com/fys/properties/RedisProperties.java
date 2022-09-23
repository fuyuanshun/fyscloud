package com.fys.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fys
 * @date 2022/9/20
 * @description
 */
@Data
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private Integer maxActive = 10;

    private Integer maxIdle = 3;

    private Integer minIdle = 3;

    private Long maxWaitMillis = 3000L;

    private Boolean testOnBorrow = false;

    private String host = "localhost";

    private String port = "6379";

    private String password;

    private Integer timeout = 3000;
}
