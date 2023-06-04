package com.usba.pss.auth.model.RolesPermissions;

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
@Table(name = "group_permission")
public class GroupPermission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    private boolean active;
}
