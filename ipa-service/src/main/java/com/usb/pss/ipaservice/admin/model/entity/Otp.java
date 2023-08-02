package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.OtpType;
import com.usb.pss.ipaservice.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_otp")
public class Otp extends BaseEntity {
    @Column(length = 6)
    private String otpCode;
    @Enumerated(EnumType.STRING)
    private OtpType otpType;
    private LocalDateTime expiration;
    private LocalDateTime resendTimer;

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
