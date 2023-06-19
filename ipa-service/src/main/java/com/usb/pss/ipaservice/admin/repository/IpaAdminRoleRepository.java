package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminRoleRepository extends JpaRepository<IpaAdminRole, Long> {
    Optional<IpaAdminRole> findByNameIgnoreCase(String roleName);

//    @Query("select new com.usb.pss.ipaservice.admin.dto.response.RoleResponse(" +
//            "rl.id, " +
//            "rl.name, " +
//            "rl.description) " +
//            "from IpaAdminRole rl")
//    List<RoleResponse> findAllRoleResponse();
}
