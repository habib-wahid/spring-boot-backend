package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private Long id;
    private String name;
    private String title;
    private String path;
    public Long parentId;
    private int sortOrderId;
    private boolean active;
}
