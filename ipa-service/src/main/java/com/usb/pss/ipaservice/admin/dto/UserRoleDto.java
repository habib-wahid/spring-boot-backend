package com.usb.pss.ipaservice.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {
    private int id;
    private Long roleId;
    private Long userId;
    private boolean active = true;

}
