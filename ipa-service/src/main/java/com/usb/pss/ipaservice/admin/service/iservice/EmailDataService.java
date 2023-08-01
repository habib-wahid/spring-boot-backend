package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.enums.EmailType;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

public interface EmailDataService {
    EmailData getEmailDataByEmailType(EmailType emailType);
}
