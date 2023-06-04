package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Role;
import com.usba.pss.auth.model.RolesPermissions.UserRole;
import com.usba.pss.auth.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    public Set<UserRole> findAllByUserAndActive(User user, boolean active);
    public List<UserRole> findAllByRole(Long roleId);

    public UserRole findByUserAndRole(User user, Role role);
}
