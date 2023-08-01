package com.usb.pss.ipaservice.utils;

import static com.usb.pss.ipaservice.common.constants.SecurityConstants.TOKEN_TYPE;

public class SecurityUtils {

    private SecurityUtils() {

    }

    public static String extractTokenFromHeader(String authHeader) {
        return authHeader.substring(TOKEN_TYPE.length());
    }
}
