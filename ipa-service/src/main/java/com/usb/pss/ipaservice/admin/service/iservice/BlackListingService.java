package com.usb.pss.ipaservice.admin.service.iservice;

public interface BlackListingService {
    public String blackListTokenWithExpiryTime(String token, long timeToLive);
    public Object checkBlackListedTokenWithExpiryTime(String token);
    public boolean checkIfBlacklisted(String accessToken);
}
