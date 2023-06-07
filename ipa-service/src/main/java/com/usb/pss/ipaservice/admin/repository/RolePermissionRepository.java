package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Permission;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.model.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    Set<RolePermission> findAllByRoleAndActive(Role role, boolean active);

    RolePermission findByRoleAndPermission(Role role, Permission permission);
}
