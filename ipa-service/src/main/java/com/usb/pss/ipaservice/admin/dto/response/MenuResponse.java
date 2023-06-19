package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.admin.model.enums.ServiceName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long serviceId;
    private ServiceName serviceName;
}
