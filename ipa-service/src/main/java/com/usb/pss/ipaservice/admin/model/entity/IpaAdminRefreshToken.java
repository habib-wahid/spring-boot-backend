package com.usb.pss.ipaservice.admin.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ipa_admin_refresh_token")
public class IpaAdminRefreshToken {

    @Id
    private UUID tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private IpaAdminUser user;

    private LocalDateTime expiration;
}
