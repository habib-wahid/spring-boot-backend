package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.model.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    @Value("${application.host-mail}")
    private String hostMail;

    private final JavaMailSender mailSender;

    public void sendEmail(User user, String link)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(hostMail);
        helper.setTo(user.getEmail());
        String subject = "Here's the link to reset your password";
        String content = "<p>Dear, " + user.getUsername() + "</p>"
                + "<p>We have received a request to reset your password for your account. To proceed with the following link : </p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<p>If you did not initiate this request or no longer wish to reset your password, please disregard this email. Your current password will remain unchanged.</p>"
                + "<p>Please note that the password reset link is valid for 5 min. After this time, you will need to request a new link if you still wish to reset your password.</p>"
                + "<p>If you encounter any difficulties or require further assistance, please contact our support team at support@email.com . We are here to help you.</p>"
                + "<p>Thank you for using USBA PSS.</p>"
                + "<p>Best regards,</p>"
                + "<p>System Administrator.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
