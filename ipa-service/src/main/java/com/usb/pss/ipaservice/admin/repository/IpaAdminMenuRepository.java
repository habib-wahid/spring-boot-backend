package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminMenuRepository extends JpaRepository<IpaAdminMenu, Long> {
    @Query("select mn from IpaAdminMenu mn where mn.id = :menuId and mn.active = true")
    Optional<IpaAdminMenu> findActiveMenuById(Long menuId);

    @Query("select mn from IpaAdminMenu mn where mn.name = :menuName and mn.active = true")
    Optional<IpaAdminMenu> findActiveMenuByName(String menuName);

    @Query("select mn from IpaAdminMenu mn where mn.url = :menuUrl and mn.active = true")
    Optional<IpaAdminMenu> findActiveMenuByUrl(String menuUrl);

    @Query("select mn from IpaAdminMenu mn where mn.active = true order by mn.name asc")
    List<IpaAdminMenu> findAllActiveMenus();

    @Query("select mn from IpaAdminMenu mn where mn.active = false order by mn.name asc")
    List<IpaAdminMenu> findAllInactiveMenus();
}
