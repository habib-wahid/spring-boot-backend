package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.JobConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Junaid Khan Pathan
 * @date Aug 07, 2023
 */

public interface JobConfigRepository extends JpaRepository<JobConfig, Long> {
}
