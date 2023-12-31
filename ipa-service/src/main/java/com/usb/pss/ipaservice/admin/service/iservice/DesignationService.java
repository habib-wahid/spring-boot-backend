package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.CreateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateDesignationRequest;
import com.usb.pss.ipaservice.admin.dto.response.DesignationResponse;
import com.usb.pss.ipaservice.admin.model.entity.Designation;

import java.util.List;

public interface DesignationService {
    void createDesignation(CreateDesignationRequest createDesignationRequest);

    void updateDesignation(UpdateDesignationRequest updateDesignationRequest);

    DesignationResponse getDesignationById(Long id);

    DesignationResponse getDesignationResponse(Designation designation);

    Designation findDesignationById(Long designationId);

    List<DesignationResponse> getAllDesignations();

}
