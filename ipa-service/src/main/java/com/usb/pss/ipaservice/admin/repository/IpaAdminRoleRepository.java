package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminRoleRepository extends JpaRepository<IpaAdminRole, Long> {
    @Query("select rl from IpaAdminRole rl where rl.id = :roleId and rl.active = true")
    Optional<IpaAdminRole> findActiveRoleById(Long roleId);

    @Query("select rl from IpaAdminRole rl where rl.name = :roleName and rl.active = true")
    Optional<IpaAdminRole> findActiveRoleByName(String roleName);

    @Query("select rl from IpaAdminRole rl where rl.active = true order by rl.name asc")
    List<IpaAdminRole> findAllActiveRoles();

    @Query("select rl from IpaAdminRole rl where rl.active = false order by rl.name asc")
    List<IpaAdminRole> findAllInactiveRoles();
}
