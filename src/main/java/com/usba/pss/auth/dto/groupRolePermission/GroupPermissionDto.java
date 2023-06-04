package com.usba.pss.auth.dto.groupRolePermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupPermissionDto {
    private int id;
    private Long groupId;
    private Long permissionId;
    private boolean active = true;
}
