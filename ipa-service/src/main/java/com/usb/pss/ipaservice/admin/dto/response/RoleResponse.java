package com.usb.pss.ipaservice.admin.dto.response;

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
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private boolean active;
}
