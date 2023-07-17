package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.ServiceName;
import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "adm_module")
public class Module extends BaseAuditorEntity {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ServiceName name;

    private String description;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module parentModule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentModule")
    private Set<Module> subModules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private Set<Menu> menus;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
