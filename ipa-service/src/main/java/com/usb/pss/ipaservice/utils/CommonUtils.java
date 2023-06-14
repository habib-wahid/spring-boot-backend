package com.usb.pss.ipaservice.utils;

public class CommonUtils {

    private CommonUtils() {

    }

    public static String extractTokenFromHeader(String authHeader) {
        return authHeader.substring(7);
    }
}
