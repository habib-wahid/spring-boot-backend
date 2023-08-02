package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.CreateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.response.DepartmentResponse;
import com.usb.pss.ipaservice.admin.service.iservice.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.DEPARTMENT_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(DEPARTMENT_ENDPOINT)
@Tag(name = "Department Controller", description = "API Endpoints for department related operations.")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    @Operation(summary = "Get department with it's ID")
    public DepartmentResponse getDepartment(@Validated @PathVariable Long departmentId) {
        return departmentService.findDepartmentById(departmentId);
    }

    @GetMapping
    @Operation(summary = "Get all departments")
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    @Operation(summary = "create a new department")
    public void createDepartment(@Validated @RequestBody CreateDepartmentRequest createDepartmentRequest) {
        departmentService.createDepartment(createDepartmentRequest);
    }

    @PutMapping
    @Operation(summary = "update an existing department")
    public void updateDepartment(@Validated @RequestBody UpdateDepartmentRequest updateDepartmentRequest) {
        departmentService.updateDepartment(updateDepartmentRequest);
    }

}
