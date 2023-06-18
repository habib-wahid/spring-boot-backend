package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.MenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface MenuService {
    void createNewMenu(MenuRequest menuRequest);
    IpaAdminMenu getMenuById(Long menuId);
    IpaAdminMenu getMenuByName(String menuName);
    IpaAdminMenu getMenuByUrl(String menuUrl);
    MenuResponse getMenuResponseById(Long menuId);
    List<MenuResponse> getAllActiveMenus();
    List<MenuResponse> getAllInactiveMenus();
    void updateMenu(MenuRequest menuRequest, Long menuId);
    void deactivateMenu(Long menuId);
}
