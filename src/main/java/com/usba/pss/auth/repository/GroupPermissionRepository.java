package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.GroupPermission;
import com.usba.pss.auth.model.RolesPermissions.Groups;
import com.usba.pss.auth.model.RolesPermissions.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {
    public Set<GroupPermission> findAllByGroupsAndActive(Groups groups, boolean active);
    public GroupPermission findByGroupsAndPermission(Groups groups, Permission permission);
}
