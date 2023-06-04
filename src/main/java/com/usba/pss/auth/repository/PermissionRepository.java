package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
