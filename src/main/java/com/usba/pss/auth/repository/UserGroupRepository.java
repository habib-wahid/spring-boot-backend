package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Groups;
import com.usba.pss.auth.model.RolesPermissions.UserGroup;
import com.usba.pss.auth.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    public Set<UserGroup> findAllByUserAndActive(User user, boolean active);
    public UserGroup findByUserAndGroups(User user, Groups groups);
}
