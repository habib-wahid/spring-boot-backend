package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.Service;
import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ipa_admin_menu")
public class IpaAdminMenu extends BaseAuditorEntity {

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String url;

    private String icon;

    @Enumerated(EnumType.STRING)
    private Service service;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
