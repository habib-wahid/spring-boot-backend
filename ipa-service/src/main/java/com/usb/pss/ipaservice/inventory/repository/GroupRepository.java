package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {
}
