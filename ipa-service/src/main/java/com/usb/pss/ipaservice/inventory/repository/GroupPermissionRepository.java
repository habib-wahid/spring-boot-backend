package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.GroupPermission;
import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Groups;
import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {
    Set<GroupPermission> findAllByGroupsAndActive(Groups groups, boolean active);

    GroupPermission findByGroupsAndPermission(Groups groups, Permission permission);
}
