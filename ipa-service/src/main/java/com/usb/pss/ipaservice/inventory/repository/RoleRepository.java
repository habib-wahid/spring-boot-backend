package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
