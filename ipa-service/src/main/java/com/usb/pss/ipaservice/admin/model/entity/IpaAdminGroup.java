package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ipa_admin_group")
public class IpaAdminGroup extends BaseAuditorEntity {

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ipa_admin_group_role",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<IpaAdminRole> assignedRoles = new HashSet<>();

    public void addRole(IpaAdminRole role) {
        if (Objects.nonNull(role)) {
            this.assignedRoles.add(role);
        }
    }

    public void removeRole(IpaAdminRole role) {
        if (Objects.nonNull(role)) {
            this.assignedRoles.remove(role);
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
