package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    @EntityGraph(attributePaths = {"subModules", "subModules.menus", "subModules.menus.actions"})
    List<Module> findAllByParentModuleIsNull();

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
        " left join fetch mn.actions ac left join ac.users u where u.id = :userId and md.parentModule is null")
    List<Module> findAllModuleByUserId(@Param("userId") Long userId);

}
