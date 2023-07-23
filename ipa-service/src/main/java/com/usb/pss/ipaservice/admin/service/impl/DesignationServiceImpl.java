package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.response.DepartmentResponse;
import com.usb.pss.ipaservice.admin.dto.response.DesignationResponse;
import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.repository.DepartmentRepository;
import com.usb.pss.ipaservice.admin.repository.DesignationRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceAlreadyExistsException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DEPARTMENT_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_DESIGNATION;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public void createDesignation(CreateDesignationRequest createDesignationRequest) {
        Department department = findDepartmentById(createDesignationRequest.departmentId());
        if (!checkDesignationExistsByNameAndDepartment(createDesignationRequest.name(), department)) {
            Designation designation = new Designation();
            designation.setName(createDesignationRequest.name());
            designation.setDepartment(department);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DESIGNATION);
        }
    }


    @Override
    public void updateDesignation(UpdateDesignationRequest updateDesignationRequest) {
        Designation designation = findDesignationWithDepartmentById(updateDesignationRequest.id());
        Department department = findDepartmentById(updateDesignationRequest.departmentId());
        if (!checkDesignationExistsByNameAndDepartment(updateDesignationRequest.name(), department)) {
            designation.setName(updateDesignationRequest.name());
            designation.setDepartment(department);
            designationRepository.save(designation);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DESIGNATION);
        }
    }

    @Override
    public DesignationResponse getDesignationById(Long id) {
        return getDesignationResponseFromDesignation(findDesignationWithDepartmentById(id));
    }

    @Override
    public List<DesignationResponse> getAllDesignations() {
        return getDesignationResponses(designationRepository.findAll());
    }

    @Override
    public List<DesignationResponse> getAllDesignationsByDepartmentId(Long departmentId) {
        Department department = findDepartmentById(departmentId);
        return getDesignationResponses(designationRepository.findAllByDepartment(department));

    }

    private Designation findDesignationWithDepartmentById(Long id) {
        return designationRepository.findDesignationWithDepartmentById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.DESIGNATION_NOT_FOUND));
    }

    private Boolean checkDesignationExistsByNameAndDepartment(String designationName, Department department) {
        return designationRepository.existsByNameAndDepartment(designationName, department);
    }

    private Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
            .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    private DesignationResponse getDesignationResponseFromDesignation(Designation designation) {
        DesignationResponse designationResponse = new DesignationResponse();
        designationResponse.setId(designation.getId());
        designationResponse.setDesignationName(designation.getName());
        designationResponse.setDepartmentResponse(getDepartmentResponse(designation.getDepartment()));
        return designationResponse;
    }

    private DepartmentResponse getDepartmentResponse(Department department) {
        return new DepartmentResponse(department.getId(), department.getName());
    }

    private List<DesignationResponse> getDesignationResponses(List<Designation> designations) {
        return designations.stream()
            .map(this::getDesignationResponseFromDesignation)
            .toList();
    }
}
