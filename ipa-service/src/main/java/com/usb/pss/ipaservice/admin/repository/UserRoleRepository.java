package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.model.entity.UserRole;
import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Set<UserRole> findAllByUserAndActive(User user, boolean active);

    List<UserRole> findAllByRole(Long roleId);

    UserRole findByUserAndRole(User user, Role role);
}
