package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

public interface OtpService {
    Otp saveAndSend2faOtp(User user);

    Boolean verify2faOtp(User user, OtpVerifyRequest request);

    Otp resend2faOtp(User user, OtpResendRequest request);
}
