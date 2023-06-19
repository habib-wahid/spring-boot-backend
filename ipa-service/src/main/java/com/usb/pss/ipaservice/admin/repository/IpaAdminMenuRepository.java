package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminMenuRepository extends JpaRepository<IpaAdminMenu, Long> {
    Optional<IpaAdminMenu> findByNameIgnoreCase(String menuName);

    Optional<IpaAdminMenu> findByUrlIgnoreCase(String menuUrl);

    @Query("select new com.usb.pss.ipaservice.admin.dto.response.MenuResponse(" +
            "mn.id, " +
            "mn.name, " +
            "mn.url, " +
            "mn.icon, " +
            "mn.service) " +
            "from IpaAdminMenu mn")
    List<MenuResponse> findAllMenuResponse();
}
