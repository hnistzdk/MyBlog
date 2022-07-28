package com.zdk.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zdk
 * @Date 2022/7/23 13:47
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public RedisCacheWriter writer() {
        return RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
    }

    @Bean
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(2);
        configurationMap.put("article", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));
        configurationMap.put("category", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));
        configurationMap.put("comments", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));
        configurationMap.put("attach", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));

        return RedisCacheManager.builder(writer())
                .initialCacheNames(configurationMap.keySet())
                .withInitialCacheConfigurations(configurationMap)
                .build();
    }
}
