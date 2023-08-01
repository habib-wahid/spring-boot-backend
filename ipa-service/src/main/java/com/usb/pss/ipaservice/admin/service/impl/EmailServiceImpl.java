package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.model.enums.EmailType;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.service.iservice.EmailDataService;
import com.usb.pss.ipaservice.admin.service.iservice.EmailService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpLogService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final EmailDataService emailDataService;
    private final OtpLogService otpLogService;

    @Async
    @Override
    public void send2faOtpMail(User receiver, Otp otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            EmailData emailData = emailDataService.getEmailDataByEmailType(EmailType.OTP_2FA);
            String emailBody = prepare2faOtpMailBody(emailData, receiver, otp);
            helper.setTo(receiver.getEmail());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailBody, true);
            mailSender.send(message);
            otpLogService.saveOtpLog(otp, OtpStatus.SENT);
        } catch (Exception e) {
            otpLogService.saveOtpLog(otp, OtpStatus.FAILED);
            log.error("Failed to send OTP");
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(User user, String passwordResetUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            EmailData emailData = emailDataService.getEmailDataByEmailType(EmailType.PASSWORD_RESET);
            String emailBody = preparePasswordResetMailBody(emailData, user, passwordResetUrl);
            helper.setTo(user.getEmail());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send OTP");
        }
    }

    private String prepare2faOtpMailBody(EmailData emailData, User receiver, Otp otp) {
        String receiverName = receiver.getPersonalInfo().getFirstName() + " " +
                receiver.getPersonalInfo().getLastName();
        return String.format(emailData.getBody(), receiverName, otp.getOtpCode());
    }

    private String preparePasswordResetMailBody(EmailData emailData, User receiver, String passwordResetUrl) {
        String receiverName = receiver.getPersonalInfo().getFirstName() + " " +
                receiver.getPersonalInfo().getLastName();
        return String.format(emailData.getBody(), receiverName, passwordResetUrl);
    }

}
