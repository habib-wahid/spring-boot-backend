package com.usb.pss.ipaservice.admin.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "navigation_item")
public class NavigationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String label;
    private String key;

    @Column(unique = true)
    private String url;

    private String icon;
    private Long parentId;
    private Long permissionId;
}
