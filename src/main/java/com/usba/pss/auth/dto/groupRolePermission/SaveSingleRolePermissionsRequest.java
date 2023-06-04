package com.usba.pss.auth.dto.groupRolePermission;

import java.util.List;

public record SaveSingleRolePermissionsRequest (Long roleId, List<PermissionDto> permissionList) {
}
