package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

public interface EmailService {
    void send2faOtpMail(User receiver, Otp otp, EmailData emailData);

    void sendPasswordResetEmail(User user, String passwordResetUrl, EmailData emailData);
}
