package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Permission;
import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Role;
import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    Set<RolePermission> findAllByRoleAndActive(Role role, boolean active);

    RolePermission findByRoleAndPermission(Role role, Permission permission);
}
