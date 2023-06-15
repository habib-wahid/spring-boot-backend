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

    private ExpiringMap<String, String> expiringMap = ExpiringMap.builder()
            .variableExpiration()
            .maxSize(1000)
            .build();
    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    @Override
    public String blackListTokenWithExpiryTime(String token, long timeToLive) {
        redisTemplate.opsForHash().put(token, hashKey, CacheConfig.BLACKLIST_CACHE_NAME);
        boolean isExpire = redisTemplate.expire(token, timeToLive, TimeUnit.SECONDS);
        if (isExpire) {
            return token;
        }
        return null;
    }
    public boolean checkIfBlacklisted(String accessToken) {
        if (!useExpiringMapToBlackListAccessToken) {
            Object blackListedToken = checkBlackListedTokenWithExpiryTime(accessToken);
            if (blackListedToken != null) {
                System.out.println("Tried to access resource with a blacklisted token in redis");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(expiringMap.keySet());
//            String value = expiringMap.get(accessToken);
            if (expiringMap.containsKey(accessToken)) {
                System.out.println("Tried to access resource with a blacklisted token in expiringmap");
                return true;
            } else {
                return false;
            }
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
