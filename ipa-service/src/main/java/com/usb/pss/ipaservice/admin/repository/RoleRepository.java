package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameIgnoreCase(String roleName);

    List<Role> findAllByOrderByCreatedDateDesc();

    @EntityGraph(attributePaths = {"permittedMenus", "permittedActions"})
    Optional<Role> findRoleAndFetchMenuAndActionsById(Long roleId);


    @EntityGraph(attributePaths = {"permittedActions", "permittedActions.menu", "permittedMenus"})
    List<Role> findAllRoleAndMenuAndActionByIdIn(Set<Long> roles);

}
