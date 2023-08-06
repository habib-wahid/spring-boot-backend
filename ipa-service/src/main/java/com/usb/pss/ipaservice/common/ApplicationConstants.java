package com.usb.pss.ipaservice.common;

import org.springframework.data.domain.Sort;

/**
 * @author Junaid Khan Pathan
 * @date Jul 19, 2023
 */

public class ApplicationConstants {
    // TODO need to implement global parameter config table
    public static final Integer OTP_EXPIRATION_MINUTES = 5;
    public static final Integer OTP_RESEND_TIMER_MINUTES = 1;
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "15";
    public static final String DEFAULT_SORT_BY = "id";
    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;
}
