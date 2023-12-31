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

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DEPARTMENT_NOT_FOUND;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_DEPARTMENT_NAME;

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
            Department department = findDepartmentById(updateDepartmentRequest.id());
            department.setName(updateDepartmentRequest.name());
            departmentRepository.save(department);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DEPARTMENT_NAME);
        }
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return getDepartmentResponse(findDepartmentById(id));
    }

    @Override
    public DepartmentResponse getDepartmentResponse(Department department) {
        return new DepartmentResponse(department.getId(), department.getName());
    }

    @Override
    public Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
            .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return getDepartmentResponses(departmentRepository.findAll());
    }


    private Boolean existsByName(String departmentName) {
        return departmentRepository.existsByName(departmentName);
    }


    private List<DepartmentResponse> getDepartmentResponses(List<Department> departments) {
        return departments.stream()
            .map(this::getDepartmentResponse)
            .toList();
    }
}
