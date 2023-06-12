package com.usb.pss.ipaservice.admin.repository;


import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpaAdminUserRepository extends JpaRepository<IpaAdminUser, Long> {

    Optional<IpaAdminUser> findByEmail(String email);

    Optional<IpaAdminUser> findByUsername(String username);

}
