package com.usb.pss.ipaservice.admin.model.enums;

import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

@Getter
public enum OtpType {
    TWO_FA(0);

    private final Integer otpTypeCode;
    private static final Map<Integer, OtpType> otpTypeMap = new HashMap<>();

    OtpType(Integer otpTypeCode) {
        this.otpTypeCode = otpTypeCode;
    }

    static {
        for (OtpType type : OtpType.values()) {
            otpTypeMap.put(type.getOtpTypeCode(), type);
        }
    }

    public static OtpType get(Integer typeCode) {
        if (otpTypeMap.containsKey(typeCode)) {
            return otpTypeMap.get(typeCode);
        }
        throw new RuleViolationException(ExceptionConstant.INVALID_EMAIL_TYPE_CODE);
    }
}
