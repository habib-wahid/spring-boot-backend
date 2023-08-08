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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_group")
@Audited
public class Group extends BaseAuditorEntity {

    @Column(unique = true)
    private String name;
    private String description;
    private boolean active;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_group_wise_action_mapping",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id"))
    private Set<Action> permittedActions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
