package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.ServiceName;
import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ipa_admin_module")
public class IpaAdminModule extends BaseAuditorEntity {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ServiceName name;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private Set<IpaAdminMenu> menus;
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
