package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.repository.DesignationRepository;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Override
    public void createDesignation(CreateDesignationRequest createDesignationRequest) {
        Designation designation = new Designation();
        designation.setName(createDesignationRequest.name());
        designationRepository.save(designation);
    }

    @Override
    public void updateDesignation(UpdateDesignationRequest updateDesignationRequest) {
        Designation designation = findDesignationById(updateDesignationRequest.id());
        designation.setName(updateDesignationRequest.name());
        designationRepository.save(designation);
    }

    @Override
    public Designation getDesignationById(Long id) {
        return findDesignationById(id);
    }

    @Override
    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();
    }

    private Designation findDesignationById(Long id) {
        return designationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.DESIGNATION_NOT_FOUND));
    }
}
