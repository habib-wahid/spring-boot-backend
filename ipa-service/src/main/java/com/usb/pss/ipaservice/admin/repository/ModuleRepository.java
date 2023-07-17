package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    @EntityGraph(attributePaths = {"subModules", "subModules.menus", "subModules.menus.actions"})
    List<Module> findAllByParentModuleIsNull();

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
        " left join fetch mn.actions ac left join ac.roles rl where ac in :actions or rl = :role and" +
        " md.parentModule is null")
    List<Module> findModuleWiseUserActions(@Param("role") Role role, @Param("actions") Collection<Action> actions);

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
        " left join fetch mn.actions ac left join ac.roles rl where rl.id = :roleId and md.parentModule is null")
    List<Module> getAllModulesForRole(@Param("roleId") Long roleId);
}
