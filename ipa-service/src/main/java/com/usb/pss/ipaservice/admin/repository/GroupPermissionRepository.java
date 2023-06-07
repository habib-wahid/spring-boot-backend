package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.GroupPermission;
import com.usb.pss.ipaservice.admin.model.entity.Groups;
import com.usb.pss.ipaservice.admin.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {
    Set<GroupPermission> findAllByGroupsAndActive(Groups groups, boolean active);

    GroupPermission findByGroupsAndPermission(Groups groups, Permission permission);
}
