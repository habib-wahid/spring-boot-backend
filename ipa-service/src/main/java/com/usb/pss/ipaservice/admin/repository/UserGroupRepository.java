package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Groups;
import com.usb.pss.ipaservice.admin.model.entity.UserGroup;
import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Set<UserGroup> findAllByUserAndActive(User user, boolean active);
    UserGroup findByUserAndGroups(User user, Groups groups);
}
