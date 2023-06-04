package com.usba.pss.auth.model.RolesPermissions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String title;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

    @OneToMany(mappedBy = "permission")
    private List<GroupPermission> groupPermissions;

    @Column(unique = true)
    private String path;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Permission parentId;

    @Column(name = "sort_order_id")
    private int sortOrderId;

    @Column(name = "active")
    private boolean active;
}
