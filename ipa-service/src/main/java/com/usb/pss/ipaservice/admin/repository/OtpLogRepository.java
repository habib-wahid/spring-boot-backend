package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.OtpLog;
import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

public interface OtpLogRepository extends JpaRepository<OtpLog, Long> {
    List<OtpLog> findAllByUser(User user);

//    @Query(value = "select * from usba.adm_otp_log where " +
//        "otp_status_date >= now() - interval '1 day' and otp_status_date < now();",
//        nativeQuery = true)
//    Page<OtpLog> findAllOneDayOldOtpLog(Pageable pageable);
}
