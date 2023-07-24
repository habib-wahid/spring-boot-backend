package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.Menu;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface MenuService {
    Menu getMenuById(Long menuId);

    MenuActionResponse getMenuResponseById(Long menuId);

    List<MenuActionResponse> getAllMenuResponse();

    void deactivateMenu(Long menuId);

    List<MenuResponse> getAllMenuResponseWithIdName();

    void prepareResponse(Menu menu, MenuActionResponse menuActionResponse);
}
