package com.usb.pss.ipaservice.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.usb.pss.ipaservice.config.CacheConfig.BLACKLIST_CACHE_NAME;

@Service
@RequiredArgsConstructor
public class TokenBlackListingService {

    private final RedisTemplate redisTemplate;

    @Value("${spring.data.redis.hashkey:pss2023Technonext}")
    private String hashKey;

    @Cacheable(value = BLACKLIST_CACHE_NAME, key = "#jwt")
    @CacheEvict()
//    @Caching(put = {@CachePut(value = CacheConfig.BLACKLIST_CACHE_NAME,key = "#jwt")})
    public String blackListJwt(String jwt) {
        return jwt;
    }

    @Cacheable(value = BLACKLIST_CACHE_NAME, key = "#jwt", unless = "#result == null")
    public String getJwtBlackList(String jwt) {
        return null;
    }

    public String blackListJwtWithExpiryTime(String jwt, long timeLeft) {
        redisTemplate.opsForHash().put(jwt, hashKey, BLACKLIST_CACHE_NAME);
        redisTemplate.expire(jwt, timeLeft, TimeUnit.SECONDS);
        return jwt;
    }

    public Object checkBlackListJwtWithExpiryTime(String jwt) {
        return redisTemplate.opsForHash().get(jwt, hashKey);
    }

}