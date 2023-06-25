package com.usb.pss.ipaservice.admin.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleActionResponse {
    private Long id;
    private String name;
    private List<MenuActionResponse> menuList;
}
