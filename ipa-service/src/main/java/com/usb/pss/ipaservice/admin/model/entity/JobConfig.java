package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.JobType;
import com.usb.pss.ipaservice.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Junaid Khan Pathan
 * @date Aug 06, 2023
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_job_config")
public class JobConfig extends BaseEntity {
    private String jobName;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private String jobDescription;
    private String cronExpression;
    private String jobMessageEmail;
    private Boolean isEnabled;
    private Boolean isTriggered;
    private Boolean isScheduled;
    private Boolean hasJobEndingMessage;
    private Integer fixedRate;
    private Integer fixedDelay;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
