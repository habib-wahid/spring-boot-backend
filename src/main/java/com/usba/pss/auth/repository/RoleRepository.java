package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
