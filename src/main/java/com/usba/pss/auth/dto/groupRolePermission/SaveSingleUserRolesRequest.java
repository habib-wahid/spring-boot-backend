package com.usba.pss.auth.dto.groupRolePermission;

import java.util.List;

public record SaveSingleUserRolesRequest(Long userId, List<RoleDto> roleList) {
}
