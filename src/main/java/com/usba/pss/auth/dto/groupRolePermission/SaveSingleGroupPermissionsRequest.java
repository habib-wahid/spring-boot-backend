package com.usba.pss.auth.dto.groupRolePermission;

import java.util.List;

public record SaveSingleGroupPermissionsRequest(Long groupId, List<PermissionDto> permissionList) {
}
