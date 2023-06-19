package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.MENU_ENDPOINT;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(MENU_ENDPOINT)
@Tag(name = "Menu Controller", description = "API Endpoints for menu menu related operations.")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "Get all active menus in a list.")
    public GlobalApiResponse<List<MenuResponse>> getAllMenus() {
        List<MenuResponse> allActiveMenus = menuService.getAllMenuResponse();
        return new GlobalApiResponse<>(allActiveMenus);
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "Get a single menu with it's ID.")
    public GlobalApiResponse<MenuResponse> getMenuById(@PathVariable Long menuId) {
        MenuResponse menu = menuService.getMenuResponseById(menuId);
        return new GlobalApiResponse<>(menu);
    }

    @PatchMapping("/{menuId}")
    @Operation(summary = "Deactivate an active menu with it's ID.")
    public GlobalApiResponse<Void> deactivateMenu(@PathVariable Long menuId) {
        menuService.deactivateMenu(menuId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Menu deactivated successfully.", null);
    }
}
