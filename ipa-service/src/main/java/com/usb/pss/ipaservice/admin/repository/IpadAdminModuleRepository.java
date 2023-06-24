package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IpadAdminModuleRepository extends JpaRepository<IpaAdminModule,Long> {
    @Query(value = "select ipa_action.id as action_id, ipa_action.name as action_name,ipa_menu.id as menu_id, ipa_menu.name as menu_name, " +
                   "ipa_module.id as module_id,ipa_module.name as module_name from ipa_admin_action as ipa_action " +
                   "inner join ipa_admin_menu as ipa_menu on ipa_action.menu_id = ipa_menu.id " +
                   "inner join ipa_admin_module as ipa_module on ipa_menu.module_id = ipa_module.id",nativeQuery = true)
    List<Map<String,Object>> getActionsByMenuAndModule();
}
