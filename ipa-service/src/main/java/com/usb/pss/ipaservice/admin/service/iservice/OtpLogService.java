package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

public interface OtpLogService {
    void saveOtpLog(Otp otp, OtpStatus otpStatus);
}
