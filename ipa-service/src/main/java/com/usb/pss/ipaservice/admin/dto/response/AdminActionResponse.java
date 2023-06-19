package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminActionResponse {
    private Long id;
    private String actionName;
}
