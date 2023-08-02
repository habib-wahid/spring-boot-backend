package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.admin.model.enums.AccessLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private String userGroup;
    private String email;
    private String pointOfSale;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private Boolean status;
}
