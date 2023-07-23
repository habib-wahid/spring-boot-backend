package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.CreateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    void createDepartment(CreateDepartmentRequest createdepartmentRequest);

    void updateDepartment(UpdateDepartmentRequest updateDepartmentRequest);

    DepartmentResponse getDepartment(Long id);

    List<DepartmentResponse> getAllDepartments();
}
