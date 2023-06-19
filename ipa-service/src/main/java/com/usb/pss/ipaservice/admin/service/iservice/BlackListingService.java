package com.usb.pss.ipaservice.admin.service.iservice;

public interface BlackListingService {
    void blackListTokenWithExpiryTime(String token, long timeToLive);

    Object checkBlackListedTokenWithExpiryTime(String token);

    boolean isTokenBlackListed(String accessToken);
}
