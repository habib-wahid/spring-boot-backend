package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.OtpLog;
import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.repository.OtpLogRepository;
import com.usb.pss.ipaservice.admin.service.iservice.OtpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

@Service
@RequiredArgsConstructor
public class OtpLogServiceImpl implements OtpLogService {
    private final OtpLogRepository otpLogRepository;

    @Override
    public void saveOtpLog(Otp otp, OtpStatus otpStatus) {
        OtpLog otpLog = OtpLog.builder()
                .user(otp.getUser())
                .otpType(otp.getOtpType())
                .otpStatus(otpStatus)
                .otpCode(otp.getOtpCode())
                .otpStatusDate(LocalDateTime.now())
                .build();
        otpLogRepository.save(otpLog);
    }
}
