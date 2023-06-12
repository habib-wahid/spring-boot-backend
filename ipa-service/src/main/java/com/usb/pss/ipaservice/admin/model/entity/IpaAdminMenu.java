package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.admin.model.enums.Service;
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
@Table(name = "ipa_admin_menu")
public class IpaAdminMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ipa_admin_menu_sequence",
            sequenceName = "ipa_admin_menu_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String url;

    private String icon;

    @Enumerated(EnumType.STRING)
    private Service service;
}
