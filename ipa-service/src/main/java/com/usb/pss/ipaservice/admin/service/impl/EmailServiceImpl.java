package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.service.iservice.EmailService;
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

    @Async
    @Override
    public void send2faOtpMail(User receiver, Otp otp, EmailData emailData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String emailBody = prepare2faOtpMailBody(emailData, receiver, otp);
            helper.setTo(receiver.getEmail());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send OTP");
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(User user, String passwordResetUrl, EmailData emailData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String emailBody = preparePasswordResetMailBody(emailData, user, passwordResetUrl);
            helper.setTo(user.getEmail());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send password reset link");
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
