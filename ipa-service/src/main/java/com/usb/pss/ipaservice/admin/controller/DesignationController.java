package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.DESIGNATION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(DESIGNATION_ENDPOINT)
@Tag(name = "Designation Controller", description = "API Endpoints for designation related operations.")
public class DesignationController {
    private final DesignationService designationService;

    @GetMapping("/{designationId}")
    @Operation(summary = "Get designation with it's ID")
    public Designation getDesignationByID(@PathVariable Long designationId) {
        return designationService.getDesignationById(designationId);
    }

    @GetMapping
    @Operation(summary = "Get all designations")
    public List<Designation> getAllDesignations() {
        return designationService.getAllDesignations();
    }

    @PostMapping
    @Operation(summary = "Create a new designation")
    public void createDesignation(CreateDesignationRequest createDesignationRequest) {
        designationService.createDesignation(createDesignationRequest);
    }

    @PutMapping
    @Operation(summary = "update an existing designation")
    public void updateDesignation(UpdateDesignationRequest updateDesignationRequest) {
        designationService.updateDesignation(updateDesignationRequest);
    }
}
