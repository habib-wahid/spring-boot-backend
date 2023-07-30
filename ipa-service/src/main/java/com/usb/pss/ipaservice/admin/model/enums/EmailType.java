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
public enum EmailType {
    OTP_2FA(0),
    PASSWORD_RESET(1);

    private final Integer emailTypeCode;
    private static final Map<Integer, EmailType> emailTypeMap = new HashMap<>();

    EmailType(Integer emailTypeCode) {
        this.emailTypeCode = emailTypeCode;
    }

    static {
        for (EmailType type : EmailType.values()) {
            emailTypeMap.put(type.getEmailTypeCode(), type);
        }
    }

    public static EmailType get(Integer typeCode) {
        if (emailTypeMap.containsKey(typeCode)) {
            return emailTypeMap.get(typeCode);
        }
        throw new RuleViolationException(ExceptionConstant.INVALID_EMAIL_TYPE_CODE);
    }
}
