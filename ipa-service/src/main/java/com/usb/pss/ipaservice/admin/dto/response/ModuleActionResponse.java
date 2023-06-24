package com.usb.pss.ipaservice.admin.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleActionResponse {
    private Long moduleId;
    private String moduleName;
    private List<MenuActionResponse> moduleMenuList;
}
