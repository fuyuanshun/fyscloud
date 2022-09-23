package com.fys.config;

import com.fys.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author fys
 * @date 2022/9/20
 * @description
 */
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    private RedisProperties redisProperties;

    @Autowired
    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {

        if (redisProperties.getPassword() != null && !"".equals(redisProperties.getPassword())) {
            return new JedisPool(jedisPoolConfig, redisProperties.getHost(),
                    Integer.parseInt(redisProperties.getPort()), redisProperties.getTimeout(), redisProperties.getPassword());
        }
        return new JedisPool(jedisPoolConfig, redisProperties.getHost(), Integer.parseInt(redisProperties.getPort()), redisProperties.getTimeout());
    }
}
