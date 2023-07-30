package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.model.enums.OtpType;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

public interface OtpService {
    Otp saveAndSend2faOtp(User user);
    Boolean verify2faOtp(User user, String otpCode);
    Boolean hasPrevious2faOtp(User user, OtpType otpType);
}
