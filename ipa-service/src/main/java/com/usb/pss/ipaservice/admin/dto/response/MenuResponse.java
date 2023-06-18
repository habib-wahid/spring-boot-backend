package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.admin.model.enums.Service;
import lombok.*;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private String url;
    private String icon;
    private Service service;
    private boolean active;
}
