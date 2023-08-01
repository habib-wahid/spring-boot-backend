package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.OtpStatus;
import com.usb.pss.ipaservice.admin.model.enums.OtpType;
import com.usb.pss.ipaservice.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Junaid Khan Pathan
 * @date Jul 27, 2023
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_otp_log")
public class OtpLog extends BaseEntity {
    @Column(length = 6)
    private String otpCode;
    private OtpStatus otpStatus;
    private OtpType otpType;
    private LocalDateTime otpStatusDate;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
