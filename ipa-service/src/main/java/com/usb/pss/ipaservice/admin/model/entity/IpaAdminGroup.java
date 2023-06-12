package com.usb.pss.ipaservice.admin.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ipa_admin_group")
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
    private boolean active;
}
