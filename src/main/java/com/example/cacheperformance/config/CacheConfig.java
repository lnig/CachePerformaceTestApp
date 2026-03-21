package com.example.cacheperformance.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(500);
    }

    @Bean
    public CacheManager caffeineProductManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("local_product");
        caffeineCacheManager.setCaffeine(caffeineConfig());
        caffeineCacheManager.setAllowNullValues(false);
        return caffeineCacheManager;
    }

    @Bean
    public CacheManager caffeineProductsByCategoryManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("local_products");
        caffeineCacheManager.setCaffeine(caffeineConfig());
        caffeineCacheManager.setAllowNullValues(false);
        return caffeineCacheManager;
    }

    @Bean
    public RedisCacheConfiguration redisConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues();
    }

    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        Map<String, RedisCacheConfiguration> configs = new HashMap<>();

        configs.put("distributed_product", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)));
        configs.put("distributed_products", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)));

        return RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(configs)
                .build();
    }
}
