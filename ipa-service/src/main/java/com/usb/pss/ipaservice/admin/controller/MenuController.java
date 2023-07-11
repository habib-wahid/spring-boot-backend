package com.usb.pss.ipaservice.admin.controller;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.MENU_ENDPOINT;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(MENU_ENDPOINT)
@Tag(name = "Menu Controller", description = "API Endpoints for menu related operations.")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "Get all active menus in a list.")
    public List<MenuResponse> getAllMenus() {
        return menuService.getAllMenuResponse();
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "Get a single menu with it's ID.")
    public MenuResponse getMenuById(@PathVariable Long menuId) {
        return menuService.getMenuResponseById(menuId);
    }

    @PatchMapping("/{menuId}")
    @Operation(summary = "Deactivate an active menu with it's ID.")
    public void deactivateMenu(@PathVariable Long menuId) {
        menuService.deactivateMenu(menuId);
    }

    @GetMapping("/allWithIdName")
    @Operation(summary = "Get all active menus with id and name in a list.")
    public List<MenuResponseWithIdName> getAllMenusWithIdName() {
        return menuService.getAllMenuResponseWithIdName();
    }
}
