package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.service.iservice.BlackListingService;
import com.usb.pss.ipaservice.config.CacheConfig;
import lombok.RequiredArgsConstructor;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlackListingService implements BlackListingService {
    private final RedisTemplate redisTemplate;

    @Value("${spring.data.redis.hashkey:pss2023Technonext}")
    private String hashKey;

    private final ExpiringMap<String, String> expiringMap = ExpiringMap.builder().variableExpiration().maxSize(1000).build();
    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    @Override
    public void blackListTokenWithExpiryTime(String token, long timeToLive) {
//        To Do: we need to make sure tenant aware caching
        redisTemplate.opsForHash().put(token, hashKey, CacheConfig.BLACKLIST_CACHE_NAME);
        redisTemplate.expire(token, timeToLive, TimeUnit.SECONDS);
    }

    public boolean isTokenBlackListed(String accessToken) {
        if (!useExpiringMapToBlackListAccessToken) {
            Object blackListedToken = checkBlackListedTokenWithExpiryTime(accessToken);
            return blackListedToken != null;
        } else {
            return expiringMap.containsKey(accessToken);
        }
    }

    @Override
    public Object checkBlackListedTokenWithExpiryTime(String token) {
        return redisTemplate.opsForHash().get(token, hashKey);
    }

    public void putAccessTokenInExpiringMap(String accessToken, long timeToLive) {
        expiringMap.put(accessToken, "token", timeToLive, TimeUnit.SECONDS);
    }

}
