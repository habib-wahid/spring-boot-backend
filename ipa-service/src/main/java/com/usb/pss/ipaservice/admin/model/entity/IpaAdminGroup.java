package com.usb.pss.ipaservice.admin.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ipa_admin_group")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IpaAdminGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ipa_admin_group_sequence",
            sequenceName = "ipa_admin_group_sequence",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private boolean active = true;
}
