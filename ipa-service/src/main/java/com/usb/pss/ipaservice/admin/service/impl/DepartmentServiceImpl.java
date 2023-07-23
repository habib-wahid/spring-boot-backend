package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDepartmentRequest;
import com.usb.pss.ipaservice.admin.dto.response.DepartmentResponse;
import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.repository.DepartmentRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DepartmentService;
import com.usb.pss.ipaservice.exception.ResourceAlreadyExistsException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DEPARTMENT_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_DEPARTMENT_NAME;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public void createDepartment(CreateDepartmentRequest createdepartmentRequest) {
        if (!existsByName(createdepartmentRequest.name())) {
            Department department = new Department();
            department.setName(createdepartmentRequest.name());
            departmentRepository.save(department);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DEPARTMENT_NAME);
        }
    }

    @Override
    public void updateDepartment(UpdateDepartmentRequest updateDepartmentRequest) {
        if (!existsByName(updateDepartmentRequest.name())) {
            Department department = getDepartmentById(updateDepartmentRequest.id());
            department.setName(updateDepartmentRequest.name());
            departmentRepository.save(department);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DEPARTMENT_NAME);
        }
    }

    @Override
    public DepartmentResponse getDepartment(Long id) {
        return getDepartmentResponseFromDepartment(getDepartmentById(id));
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return getDepartmentResponses(departmentRepository.findAll());
    }

    private Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    private Boolean existsByName(String departmentName) {
        return departmentRepository.existsByName(departmentName);
    }

    private DepartmentResponse getDepartmentResponseFromDepartment(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setDepartmentName(department.getName());
        return departmentResponse;
    }

    private List<DepartmentResponse> getDepartmentResponses(List<Department> departments) {
        return departments.stream()
            .map(this::getDepartmentResponseFromDepartment)
            .toList();
    }
}
