package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.model.entity.Menu;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface MenuService {
    Menu getMenuById(Long menuId);

    MenuResponse getMenuResponseById(Long menuId);

    List<MenuResponse> getAllMenuResponse();

    void deactivateMenu(Long menuId);

    List<MenuResponseWithIdName> getAllMenuResponseWithIdName();

    void prepareResponse(Menu menu, MenuResponse menuResponse);
}
