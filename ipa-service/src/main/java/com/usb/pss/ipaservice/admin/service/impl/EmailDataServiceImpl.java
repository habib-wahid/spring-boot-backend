package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.enums.EmailType;
import com.usb.pss.ipaservice.admin.repository.EmailDataRepository;
import com.usb.pss.ipaservice.admin.service.iservice.EmailDataService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

@Service
@RequiredArgsConstructor
public class EmailDataServiceImpl implements EmailDataService {
    private final EmailDataRepository emailDataRepository;

    @Override
    public EmailData getEmailDataByEmailType(EmailType emailType) {
        return emailDataRepository.findEmailDataByEmailType(emailType).orElseThrow(
                () -> new RuleViolationException(ExceptionConstant.EMAIL_DATA_NOT_FOUND)
        );
    }
}
