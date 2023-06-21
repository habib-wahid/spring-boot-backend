package com.usb.pss.ipaservice.admin.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private Long groupId;
    private String name;
    private String groupName;

}
