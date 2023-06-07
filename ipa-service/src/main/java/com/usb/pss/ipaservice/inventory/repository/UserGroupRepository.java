package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.Groups;
import com.usb.pss.ipaservice.inventory.model.entity.RolesPermissions.UserGroup;
import com.usb.pss.ipaservice.inventory.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Set<UserGroup> findAllByUserAndActive(User user, boolean active);
    UserGroup findByUserAndGroups(User user, Groups groups);
}
