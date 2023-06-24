package com.usb.pss.ipaservice.admin.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuActionResponse {
    private Long menuId;
    private String menuName;
    private List<ActionResponse> actions;
}
