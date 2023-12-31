package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.model.enums.EmailType;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.model.enums.OtpType;
import com.usb.pss.ipaservice.admin.repository.OtpRepository;
import com.usb.pss.ipaservice.admin.service.iservice.EmailDataService;
import com.usb.pss.ipaservice.admin.service.iservice.EmailService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpLogService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static com.usb.pss.ipaservice.common.ApplicationConstants.OTP_EXPIRATION_MINUTES;
import static com.usb.pss.ipaservice.common.ApplicationConstants.OTP_RESEND_TIMER_MINUTES;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.EXPIRED_OTP;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.INVALID_AUTH_REQUEST;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.INVALID_OTP_RESEND_TIMER;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.OTP_NOT_FOUND;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.WRONG_OTP;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

@Component
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;
    private final OtpLogService otpLogService;
    private final EmailService emailService;
    private final EmailDataService emailDataService;

    @Override
    @Transactional
    public Otp saveAndSend2faOtp(User user) {
        otpRepository.deleteAllByUserAndOtpType(user, OtpType.TWO_FA);
        Otp otp = Otp.builder()
            .user(user)
            .otpCode(generateOtpCode())
            .otpType(OtpType.TWO_FA)
            .expiration(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES))
            .resendTimer(LocalDateTime.now().plusMinutes(OTP_RESEND_TIMER_MINUTES))
            .build();
        Otp savedOtp = otpRepository.save(otp);
        otpLogService.saveOtpLog(savedOtp, OtpStatus.GENERATED);
        EmailData emailData = emailDataService.getEmailDataByEmailType(EmailType.OTP_2FA);
        sendAsync2faOtpMail(user, savedOtp, emailData);
        return savedOtp;
    }

    private void sendAsync2faOtpMail(User user, Otp otp, EmailData emailData) {
        CompletableFuture.supplyAsync(() -> {
            emailService.send2faOtpMail(user, otp, emailData);
            return HttpStatus.OK;
        });
        // TODO save otp log data that was previously done in email service
    }

    @Override
    @Transactional
    public Boolean verify2faOtp(User user, OtpVerifyRequest request) {
        Otp otp = otpRepository.findByUserAndOtpType(user, OtpType.TWO_FA)
            .orElseThrow(() -> new ResourceNotFoundException(OTP_NOT_FOUND));
        boolean isSameOtp = otp.getOtpCode().equals(request.otpCode());
        if (LocalDateTime.now().isBefore(otp.getExpiration())) {
            if (isSameOtp) {
                otpRepository.delete(otp);
                otpLogService.saveOtpLog(otp, OtpStatus.USED);
                return true;
            }
            throw new RuleViolationException(WRONG_OTP);
        } else {
            otpRepository.delete(otp);
            throw new RuleViolationException(EXPIRED_OTP);
        }
    }

    @Override
    @Transactional
    public void resend2faOtp(User user, OtpResendRequest request) {
        Optional<Otp> existingOtp = otpRepository.findByUserAndOtpType(user, OtpType.TWO_FA);
        if (existingOtp.isPresent()) {
            Otp oldOtp = existingOtp.get();
            if (LocalDateTime.now().isAfter(oldOtp.getResendTimer())) {
                oldOtp.setOtpCode(generateOtpCode());
                oldOtp.setExpiration(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
                oldOtp.setResendTimer(LocalDateTime.now().plusMinutes(OTP_RESEND_TIMER_MINUTES));
                Otp savedOtp = otpRepository.save(oldOtp);
                otpLogService.saveOtpLog(savedOtp, OtpStatus.GENERATED);
                EmailData emailData = emailDataService.getEmailDataByEmailType(EmailType.OTP_2FA);
                sendAsync2faOtpMail(user, savedOtp, emailData);
                return;
            }
            throw new RuleViolationException(INVALID_OTP_RESEND_TIMER);
        }
        throw new RuleViolationException(INVALID_AUTH_REQUEST);
    }

    private String generateOtpCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return String.valueOf(random.nextInt(max - min + 1) + min);
    }
}
