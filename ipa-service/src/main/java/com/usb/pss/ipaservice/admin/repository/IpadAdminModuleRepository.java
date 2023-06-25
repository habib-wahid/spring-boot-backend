package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminModule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IpadAdminModuleRepository extends JpaRepository<IpaAdminModule, Long> {
    @EntityGraph(attributePaths = {"menus", "menus.actions"})
    List<IpaAdminModule> findAll();
}
