package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.model.enums.OtpType;
import com.usb.pss.ipaservice.admin.repository.OtpRepository;
import com.usb.pss.ipaservice.admin.service.iservice.EmailService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpLogService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
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
import static com.usb.pss.ipaservice.common.ExceptionConstant.EXPIRED_OTP;
import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_AUTH_REQUEST;
import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_OTP_RESEND_TIMER;
import static com.usb.pss.ipaservice.common.ExceptionConstant.WRONG_OTP;

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
        sendAsync2faOtpMail(user, savedOtp);
        return savedOtp;
    }

    private void sendAsync2faOtpMail(User user, Otp otp) {
        CompletableFuture.supplyAsync(() -> {
            emailService.send2faOtpMail(user, otp);
            return HttpStatus.OK;
        });
    }

    @Override
    @Transactional
    public Boolean verify2faOtp(User user, OtpVerifyRequest request) {
        Otp otp = otpRepository.findByUserAndOtpType(user, OtpType.TWO_FA)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.OTP_NOT_FOUND));
        boolean isSameOtp = otp.getOtpCode().equals(request.otpCode());
        if (LocalDateTime.now().isBefore(otp.getExpiration())) {
            if (isSameOtp) {
                otpRepository.delete(otp);
                otpLogService.saveOtpLog(otp, OtpStatus.USED);
                return true;
            } else {
                throw new RuleViolationException(WRONG_OTP);
            }
        } else {
            otpRepository.delete(otp);
            throw new RuleViolationException(EXPIRED_OTP);
        }
    }

    @Override
    @Transactional
    public Otp resend2faOtp(User user, OtpResendRequest request) {
        Optional<Otp> existingOtp = otpRepository.findByUserAndOtpType(user, OtpType.TWO_FA);
        if (existingOtp.isPresent()) {
            Otp oldOtp = existingOtp.get();
            if (LocalDateTime.now().isAfter(oldOtp.getResendTimer())) {
                oldOtp.setOtpCode(generateOtpCode());
                oldOtp.setExpiration(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
                oldOtp.setResendTimer(LocalDateTime.now().plusMinutes(OTP_RESEND_TIMER_MINUTES));
                Otp savedOtp = otpRepository.save(oldOtp);
                otpLogService.saveOtpLog(savedOtp, OtpStatus.GENERATED);
                sendAsync2faOtpMail(user, savedOtp);
                return savedOtp;
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
