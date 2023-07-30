package com.usb.pss.ipaservice.admin.model.enums;

import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Khan Pathan
 * @date Jul 19, 2023
 */

@Getter
public enum OtpStatus {
    GENERATED(0),
    SENT(1),
    USED(2),
    FAILED(3);

    private final Integer otpStatusCode;
    private static final Map<Integer, OtpStatus> otpStatusMap = new HashMap<>();

    OtpStatus(Integer otpStatusCode) {
        this.otpStatusCode = otpStatusCode;
    }

    static {
        for (OtpStatus status : OtpStatus.values()) {
            otpStatusMap.put(status.getOtpStatusCode(), status);
        }
    }

    public static OtpStatus get(Integer statusCode) {
        if (otpStatusMap.containsKey(statusCode)) {
            return otpStatusMap.get(statusCode);
        }
        throw new RuleViolationException(ExceptionConstant.INVALID_OTP_STATUS_CODE);
    }
}
