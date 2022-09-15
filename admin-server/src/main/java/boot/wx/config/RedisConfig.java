package boot.wx.config;

import boot.wx.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author fys
 * @date 2022/9/16
 * @description
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    @Resource
    private RedisProperties redisProperties;


    @Bean
    public JedisPoolConfig initPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数，默认值为8.如果赋值为-1，则表示不限制；
//        poolConfig.setMaxTotal(redisProperties.getMaxTotal());
//        // 最大空闲连接数
//        poolConfig.setMaxIdle(redisProperties.getMaxIdle());
//        // 最小空闲连接数
//        poolConfig.setMinIdle(redisProperties.getMinIdle());
//        // 获取Jedis连接的最大等待时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
//        poolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
//        // 每次释放连接的最大数目
//        poolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
//        // 释放连接的扫描间隔（毫秒）,如果为负数,则不运行逐出线程, 默认-1
//        poolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
//        // 连接最小空闲时间
//        poolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMillis());
//        // 连接空闲多久后释放, 当空闲时间&gt;该值 且 空闲连接&gt;最大空闲连接数 时直接释放
//        poolConfig.setSoftMinEvictableIdleTimeMillis(redisProperties.getSoftMinEvictableIdleTimeMillis());
//        // 在获取Jedis连接时，自动检验连接是否可用
//        poolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
//        // 在将连接放回池中前，自动检验连接是否有效
//        poolConfig.setTestOnReturn(redisProperties.isTestOnReturn());
//        // 自动测试池中的空闲连接是否都是可用连接
//        poolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
//        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
//        poolConfig.setBlockWhenExhausted(redisProperties.isBlockWhenExhausted());
//        // 是否启用pool的jmx管理功能, 默认true
//        poolConfig.setJmxEnabled(redisProperties.isJmxEnabled());
//        // 是否启用后进先出, 默认true
//        poolConfig.setLifo(redisProperties.isLifo());
//        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
//        poolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        return poolConfig;
    }

    @Bean
    public JedisPool getRedisPool() {
        return new JedisPool(initPoolConfig(), redisProperties.getHost(), redisProperties.getPort());
    }
}
