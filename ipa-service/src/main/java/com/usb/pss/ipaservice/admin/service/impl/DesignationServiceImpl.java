package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.response.DesignationResponse;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.repository.DesignationRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceAlreadyExistsException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_DESIGNATION;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Override
    public void createDesignation(CreateDesignationRequest createDesignationRequest) {
        if (!checkDesignationExistsByName(createDesignationRequest.name())) {
            Designation designation = new Designation();
            designation.setName(createDesignationRequest.name());
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DESIGNATION);
        }
    }


    @Override
    public void updateDesignation(UpdateDesignationRequest updateDesignationRequest) {
        Designation designation = findDesignationWithDepartmentById(updateDesignationRequest.id());
        if (!checkDesignationExistsByName(updateDesignationRequest.name())) {
            designation.setName(updateDesignationRequest.name());
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

    private Designation findDesignationWithDepartmentById(Long id) {
        return designationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.DESIGNATION_NOT_FOUND));
    }

    private Boolean checkDesignationExistsByName(String designationName) {
        return designationRepository.existsByName(designationName);
    }


    private DesignationResponse getDesignationResponseFromDesignation(Designation designation) {
        DesignationResponse designationResponse = new DesignationResponse();
        designationResponse.setId(designation.getId());
        designationResponse.setDesignationName(designation.getName());
        return designationResponse;
    }

    private List<DesignationResponse> getDesignationResponses(List<Designation> designations) {
        return designations.stream()
            .map(this::getDesignationResponseFromDesignation)
            .toList();
    }
}
