package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.ModuleResponseWithSubModuleAndMenu;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.MODULE_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MODULE_ENDPOINT)
@Tag(name = "Module Controller", description = "API Endpoints for module and submodule related operations.")
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping
    @Operation(summary = "Get all modules with subModules in a list.")
    public List<ModuleResponseWithSubModuleAndMenu> getAllModulesWithSubModulesAndMenus() {
        return moduleService.getModuleWithSubModulesAndMenus();
    }
}
