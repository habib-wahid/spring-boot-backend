package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoResponse {
    private String firstName;
    private String lastName;
    private DepartmentResponse department;
    private DesignationResponse designation;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
}
