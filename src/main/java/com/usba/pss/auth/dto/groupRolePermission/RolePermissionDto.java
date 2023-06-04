package com.usba.pss.auth.dto.groupRolePermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {
    private int id;
    private Long roleId;
    private Long permissionId;
    private boolean active = true;
}
