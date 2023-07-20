package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Group;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    @EntityGraph(attributePaths = {"subModules", "subModules.menus", "subModules.menus.actions"})
    List<Module> findAllByParentModuleIsNull();

    @EntityGraph(attributePaths = {"subModules", "subModules.menus"})
    List<Module> findAllModulesWithSubModulesAndMenusByParentModuleIsNull();

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
        " left join fetch mn.actions ac left join ac.groups gr where ac in :actions or gr = :group and" +
        " md.parentModule is null")
    List<Module> findModuleWiseUserActions(@Param("group") Group group, @Param("actions") Collection<Action> actions);

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
        " left join fetch mn.actions ac left join ac.groups gr where gr.id = :groupId and md.parentModule is null")
    List<Module> getAllModulesForGroup(@Param("groupId") Long groupId);
}
