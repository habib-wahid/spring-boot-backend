package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.MenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.MENU_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MENU_ENDPOINT)
@Tag(name = "Menu Controller", description = "API Endpoints for menu menu related operations.")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "Get all active menus in a list.")
    public GlobalApiResponse<List<MenuResponse>> getAllMenus() {
        List<MenuResponse> allActiveMenus = menuService.getAllActiveMenus();
        return new GlobalApiResponse<>(allActiveMenus);
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "Get a single menu with it's ID.")
    public GlobalApiResponse<MenuResponse> getMenuById(@PathVariable Long menuId) {
        MenuResponse menu = menuService.getMenuResponseById(menuId);
        return new GlobalApiResponse<>(menu);
    }

    @PostMapping
    @Operation(summary = "Create a new menu with valid menu data.")
    public GlobalApiResponse<Void> createNewMenu(@Validated @RequestBody MenuRequest menuRequest) {
        menuService.createNewMenu(menuRequest);
        return new GlobalApiResponse<>(HttpStatus.CREATED, "Menu created successfully.", null);
    }

    @PutMapping("/{menuId}")
    @Operation(summary = "update an existing menu with valid menu data and existing menu's ID.")
    public GlobalApiResponse<Void> updateMenu(@Validated @RequestBody MenuRequest menuRequest,
                                              @PathVariable Long menuId) {
        menuService.updateMenu(menuRequest, menuId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Menu updated successfully.", null);
    }

    @DeleteMapping("/{menuId}")
    @Operation(summary = "Deactivate an active menu with it's ID.")
    public GlobalApiResponse<Void> deactivateMenu(@PathVariable Long menuId) {
        menuService.deactivateMenu(menuId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Menu deactivated successfully.", null);
    }
}
