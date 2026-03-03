package com.scnu.common.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class CaffeineCacheConfig {

    @Bean
    public LoadingCache<String, Object> loadingCache() {
        return Caffeine.newBuilder()
                //设置缓存中保存数据的最大量
                .maximumSize(Integer.MAX_VALUE)
                //初始化缓存空间大小
                .initialCapacity(100)
                //设置过期时间
                .expireAfterAccess(60*5, TimeUnit.SECONDS)
                //构建Cache实例
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return null;
                    }
                });
    }

}
