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
@Table(name = "groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "group_name", unique = true)
    private String groupName;

    @OneToMany(mappedBy = "groups")
    private List<GroupPermission> groupPermissions;

    @Column(name = "active")
    private boolean active;
}
