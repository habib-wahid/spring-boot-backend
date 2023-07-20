package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDepartmentRequest;
import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.repository.DepartmentRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DepartmentService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DEPARTMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public void createDepartment(CreateDepartmentRequest createdepartmentRequest) {
        Department department = new Department();
        department.setName(createdepartmentRequest.name());
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(UpdateDepartmentRequest updateDepartmentRequest) {
        Department department = getDepartmentById(updateDepartmentRequest.id());
        department.setName(updateDepartmentRequest.name());
        departmentRepository.save(department);

    }

    private Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    @Override
    public Department getDepartment(Long id) {
        return getDepartmentById(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
