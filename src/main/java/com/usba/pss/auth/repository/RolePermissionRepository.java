package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Permission;
import com.usba.pss.auth.model.RolesPermissions.Role;
import com.usba.pss.auth.model.RolesPermissions.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    public Set<RolePermission> findAllByRoleAndActive(Role role, boolean active);
    public RolePermission findByRoleAndPermission(Role role, Permission permission);
}
