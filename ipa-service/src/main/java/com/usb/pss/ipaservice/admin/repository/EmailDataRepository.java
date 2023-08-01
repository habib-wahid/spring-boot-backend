package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.EmailData;
import com.usb.pss.ipaservice.admin.model.enums.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findEmailDataByEmailType(EmailType emailType);
}
