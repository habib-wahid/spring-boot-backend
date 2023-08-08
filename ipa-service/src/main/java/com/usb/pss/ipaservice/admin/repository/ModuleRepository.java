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
    List<Module> findAllByOrderBySortOrder();

    @EntityGraph(attributePaths = {"subModules", "subModules.menus"})
    List<Module> findAllModulesWithSubModulesAndMenusByIdIsNotNull();

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
            " left join fetch mn.actions ac left join ac.groups gr where ac in :actions or gr = :group"
    )
    List<Module> findModuleWiseUserActions(@Param("group") Group group, @Param("actions") Collection<Action> actions);


    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn " +
            "left join fetch mn.actions ac where md.id = :moduleId and ac not in :groupActions"
    )
    List<Module> findActionsByModuleNotInGroup(
            @Param("groupActions") Collection<Action> groupActions,
            @Param("moduleId") Long moduleId
    );

    @Query("select md from Module md left join fetch md.subModules smd left join fetch smd.menus mn" +
            " left join mn.actions ac left join ac.groups gr where ac in :actions or gr = :group"
    )
    List<Module> findModuleWiseMenuForGroupAndAdditionalAction(
            @Param("group") Group group, @Param("actions") Collection<Action> actions
    );

}
