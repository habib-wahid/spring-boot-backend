package com.usb.pss.ipaservice.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupsDto {
    private Long id;
    private String groupName;
    private boolean active;
}
