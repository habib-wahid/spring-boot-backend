package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ipa_admin_role")
public class IpaAdminRole extends BaseAuditorEntity {

    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ipa_admin_role_menu",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<IpaAdminMenu> permittedMenu = new HashSet<>();

    public void addMenu(IpaAdminMenu menu) {
        if (Objects.nonNull(menu)) {
            this.permittedMenu.add(menu);
        }
    }

    public void removeMenu(IpaAdminMenu menu) {
        if (Objects.nonNull(menu)) {
            this.permittedMenu.remove(menu);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
