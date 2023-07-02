package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameIgnoreCase(String roleName);

    List<Role> findAllByOrderByCreatedDateDesc();

//    @Query("select new com.usb.pss.ipaservice.admin.dto.response.RoleResponse(" +
//            "rl.id, " +
//            "rl.name, " +
//            "rl.description) " +
//            "from IpaAdminRole rl")
//    List<RoleResponse> findAllRoleResponse();
}
