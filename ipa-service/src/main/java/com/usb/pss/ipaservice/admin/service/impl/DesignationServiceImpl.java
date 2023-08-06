package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.response.DesignationResponse;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.repository.DesignationRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceAlreadyExistsException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_DESIGNATION;

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
        Designation designation = findDesignationById(updateDesignationRequest.id());
        if (!checkDesignationExistsByName(updateDesignationRequest.name())) {
            designation.setName(updateDesignationRequest.name());
            designationRepository.save(designation);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_DESIGNATION);
        }
    }

    @Override
    public DesignationResponse getDesignationById(Long id) {
        return getDesignationResponse(findDesignationById(id));
    }

    @Override
    public Designation findDesignationById(Long designationId) {
        return designationRepository.findById(designationId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.DESIGNATION_NOT_FOUND));
    }

    @Override
    public List<DesignationResponse> getAllDesignations() {
        return getDesignationResponses(designationRepository.findAll());
    }

    private Boolean checkDesignationExistsByName(String designationName) {
        return designationRepository.existsByName(designationName);
    }


    @Override
    public DesignationResponse getDesignationResponse(Designation designation) {
        return new DesignationResponse(designation.getId(), designation.getName());
    }

    private List<DesignationResponse> getDesignationResponses(List<Designation> designations) {
        return designations.stream().map(this::getDesignationResponse).toList();
    }
}
